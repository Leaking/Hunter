package com.quinn.hunter.plugin.debug.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Quinn on 19/09/2018.
 */
public class DebugPreGoMethodAdapter extends MethodVisitor implements Opcodes {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugPreGoMethodAdapter.class);
    private Map<String, List<String>> methodParametersMap;
    private List<String> parameters = new ArrayList<>();
    private String methodKey;
    private boolean needParameter = false;
    private List<Label> labelList = new ArrayList<>();

    public DebugPreGoMethodAdapter(String methodKey, Map<String, List<String>> methodParametersMap, MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
        this.methodKey = methodKey;
        this.methodParametersMap = methodParametersMap;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor defaultAv = super.visitAnnotation(desc, visible);
        if("Lcom/hunter/library/debug/HunterDebug;".equals(desc)) {
            needParameter = true;
        }
        return defaultAv;
    }

    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        if(!"this".equals(name) && start == labelList.get(0) && needParameter) {
            parameters.add(name + "--" + desc);
        }
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    @Override
    public void visitEnd() {
        methodParametersMap.put(methodKey, parameters);
        super.visitEnd();
    }

    @Override
    public void visitLabel(Label label) {
        labelList.add(label);
        super.visitLabel(label);
    }

    public boolean getNeedParameter() {
        return needParameter;
    }

}
