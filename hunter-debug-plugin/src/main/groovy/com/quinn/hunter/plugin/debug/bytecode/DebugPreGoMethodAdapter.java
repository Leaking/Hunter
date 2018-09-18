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

    /**
     * not called except you compile with -parameters option
     */
    @Override
    public void visitParameter(String name, int access) {
        logger.info("parameter name" + name);
        super.visitParameter(name, access);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        logger.info("visit annotation " + desc);
        AnnotationVisitor defaultAv = super.visitAnnotation(desc, visible);
        if("Lcom/hunter/library/debug/HunterDebug;".equals(desc)) {
            needParameter = true;
            return new AnnotationVisitor(Opcodes.ASM5, defaultAv) {
                @Override
                public void visit(String name, Object value) {
                    if("stepByStep".equals(name) && (Boolean)value) {
//                        stepByStep = true;
                    }
                    super.visit(name, value);
                }
            };
        } else {
            return defaultAv;
        }
    }

    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        if(!"this".equals(name) && start == labelList.get(0)) {
            logger.info("local val " + name);
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
