package com.quinn.hunter.plugin.debug.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

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
    private boolean debugMethod = false;
    private boolean stepByStep = false;

    public DebugMethodAdapter(String methodKey, Map<String, List<String>> methodParametersMap, MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
        this.methodKey = methodKey;
        this.methodParametersMap = methodParametersMap;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        logger.info("visit annotation " + desc);
        AnnotationVisitor defaultAv = super.visitAnnotation(desc, visible);
        if("Lcom/hunter/library/debug/HunterDebug;".equals(desc)) {
            debugMethod = true;
            return new AnnotationVisitor(Opcodes.ASM5, defaultAv) {
                @Override
                public void visit(String name, Object value) {
                    if("stepByStep".equals(name) && (Boolean)value) {
                        stepByStep = true;
                    }
                    super.visit(name, value);
                }
            };
        } else {
            return defaultAv;
        }
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
