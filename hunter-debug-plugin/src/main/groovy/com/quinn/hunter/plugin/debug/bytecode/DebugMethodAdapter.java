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
 * Created by Quinn on 16/09/2018.
 */
public final class DebugMethodAdapter extends MethodVisitor implements Opcodes {


    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugClassAdapter.class);
    private Map<String, List<String>> methodParametersMap = new HashMap<>();
    private List<String> parameters = new ArrayList<>();
    private String methodKey;
    private boolean lackOfParameterDetail = false;
    private boolean parameterDebug = true;

    public DebugMethodAdapter(String methodKey, MethodVisitor mv, Map<String, List<String>> methodParametersMap) {
        super(Opcodes.ASM5, mv);
        this.methodKey = methodKey;
        this.methodParametersMap = methodParametersMap;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        /**
         *  ....
         */
        this.parameterDebug = true;
        if(methodParametersMap.get(methodKey).isEmpty()){
            lackOfParameterDetail = true;
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitCode() {
        if(lackOfParameterDetail) {
            super.visitCode();
        } else {
            /**
             *
             */
            super.visitCode();
        }
    }

    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        parameters.add(name);
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    @Override
    public void visitEnd() {
        methodParametersMap.put(methodKey, parameters);
        super.visitEnd();
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    public boolean isLackOfParameterDetail() {
        return lackOfParameterDetail;
    }

    public String getMethodKey() {
        return methodKey;
    }
}
