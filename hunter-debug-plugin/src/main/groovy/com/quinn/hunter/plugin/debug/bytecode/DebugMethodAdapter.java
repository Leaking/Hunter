package com.quinn.hunter.plugin.debug.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

/**
 * Created by Quinn on 16/09/2018.
 */
public final class DebugMethodAdapter extends LocalVariablesSorter implements Opcodes {


    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugMethodAdapter.class);
    private List<Parameter> parameters;
    private String methodName;
    private boolean debugMethod = false;
    private boolean stepByStep = false;
    private int printUtilsVarIndex;


    public DebugMethodAdapter(List<Parameter> parameters, String name, int access, String desc, MethodVisitor mv) {
        super(Opcodes.ASM5, access, desc, mv);
        this.parameters = parameters;
        this.methodName = name;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
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
        super.visitCode();
        if(!debugMethod || parameters.size() == 0) return;
        printUtilsVarIndex = newLocal(Type.getObjectType("com/hunter/library/debug/PrintUtils"));
        logger.info(debugMethod + " new parameters " + printUtilsVarIndex);
        mv.visitTypeInsn(NEW, "com/hunter/library/debug/PrintUtils");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(methodName);
        mv.visitMethodInsn(INVOKESPECIAL, "com/hunter/library/debug/PrintUtils", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitVarInsn(ASTORE, printUtilsVarIndex);
        for(int i = 0; i < parameters.size(); i++) {
            Parameter parameter = parameters.get(i);
            String name = parameter.name;
            String desc = parameter.desc;
            int index = parameter.index;
            int opcode = ILOAD;
            if("F".equals(desc)) {
                opcode = FLOAD;
            } else if("J".equals(desc)) {
                opcode = LLOAD;
            } else if("D".equals(desc)) {
                opcode = DLOAD;
            } else if(desc.startsWith("L")) {  //object
                opcode = ALOAD;
            } else if(desc.startsWith("[")) {  //array
                opcode = ALOAD;
            }
            String fullyDesc = String.format("(Ljava/lang/String;%s)Lcom/hunter/library/debug/PrintUtils;", desc);
            logger.info(name + " > " + desc + " > "  + fullyDesc + " > " + index);
            visitPrint(printUtilsVarIndex, index, opcode, name, fullyDesc);
        }
        mv.visitVarInsn(ALOAD, printUtilsVarIndex);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/hunter/library/debug/PrintUtils", "print", "()V", false);
    }

    private void visitPrint(int varIndex, int localIndex, int opcode, String name, String desc){
        mv.visitVarInsn(ALOAD, varIndex);
        mv.visitLdcInsn(name);
        mv.visitVarInsn(opcode, localIndex);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "com/hunter/library/debug/PrintUtils",
                "append",
                desc, false);
        mv.visitInsn(POP);
    }

}
