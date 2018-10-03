package com.quinn.hunter.plugin.debug.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

import java.util.List;

/**
 * Created by Quinn on 16/09/2018.
 */
public final class DebugMethodAdapter extends LocalVariablesSorter implements Opcodes {


    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugMethodAdapter.class);
    private List<Parameter> parameters;
    private String methodName;
    private boolean debugMethod = false;
    private boolean stepByStep = false;
    private int timingVarIndex;
    private String methodDesc;


    public DebugMethodAdapter(List<Parameter> parameters, String name, int access, String desc, MethodVisitor mv) {
        super(Opcodes.ASM5, access, desc, mv);
        this.parameters = parameters;
        this.methodName = name;
        this.methodDesc = desc;
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
        if(!debugMethod) return;
        int printUtilsVarIndex = newLocal(Type.getObjectType("com/hunter/library/debug/ParameterPrinter"));
        logger.info(debugMethod + " new parameters " + printUtilsVarIndex);
        mv.visitTypeInsn(NEW, "com/hunter/library/debug/ParameterPrinter");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(methodName);
        mv.visitMethodInsn(INVOKESPECIAL, "com/hunter/library/debug/ParameterPrinter", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitVarInsn(ASTORE, printUtilsVarIndex);
        for(int i = 0; i < parameters.size(); i++) {
            Parameter parameter = parameters.get(i);
            String name = parameter.name;
            String desc = parameter.desc;
            int index = parameter.index;
            int opcode = Utils.getLoadOpcodeFromDesc(desc);
            String fullyDesc = String.format("(Ljava/lang/String;%s)Lcom/hunter/library/debug/ParameterPrinter;", desc);
            logger.info(name + " > " + desc + " > "  + fullyDesc + " > " + index);
            visitPrint(printUtilsVarIndex, index, opcode, name, fullyDesc);
        }
        mv.visitVarInsn(ALOAD, printUtilsVarIndex);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/hunter/library/debug/ParameterPrinter", "print", "()V", false);
        //Timing
        timingVarIndex = newLocal(Type.LONG_TYPE);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitVarInsn(Opcodes.LSTORE, timingVarIndex);
    }

    private void visitPrint(int varIndex, int localIndex, int opcode, String name, String desc){
        mv.visitVarInsn(ALOAD, varIndex);
        mv.visitLdcInsn(name);
        mv.visitVarInsn(opcode, localIndex);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "com/hunter/library/debug/ParameterPrinter",
                "append",
                desc, false);
        mv.visitInsn(POP);
    }

    @Override
    public void visitInsn(int opcode) {
        if (debugMethod && ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW)) {
            Type returnType = Type.getReturnType(methodDesc);
            String returnDesc = methodDesc.substring(methodDesc.indexOf(")") + 1);
            logger.info("returnDesc > " + returnDesc);
            if(returnDesc.startsWith("[") || returnDesc.startsWith("L")) {
                returnDesc = "Ljava/lang/Object;"; //regard object extended from Object or array as object
            }
            logger.info("returnDesc > " + returnDesc);
            //store origin return value
            int resultTempValIndex = -1;
            if(returnType != Type.VOID_TYPE) {
                resultTempValIndex = newLocal(returnType);
                int storeOpcocde = Utils.getStoreOpcodeFromType(returnType);
                mv.visitVarInsn(storeOpcocde, resultTempValIndex);
            }
            //parameter1 parameter2
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LLOAD, timingVarIndex);
            mv.visitInsn(LSUB);
            int index = newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(LSTORE, index);
            mv.visitLdcInsn(methodName);   //parameter 1 string
            mv.visitVarInsn(LLOAD, index); //parameter 2 long
            //parameter3
            if(returnType != Type.VOID_TYPE) {
                int loadOpcode = Utils.getLoadOpcodeFromType(returnType);
                mv.visitVarInsn(loadOpcode, resultTempValIndex);
                String formatDesc = String.format("(Ljava/lang/String;J%s)V", returnDesc);
                logger.info("returnDesc > " + formatDesc);
                mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/debug/ResultPrinter", "print", formatDesc, false);
                mv.visitVarInsn(loadOpcode, resultTempValIndex);
            } else {
                mv.visitLdcInsn("VOID");
                mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/debug/ResultPrinter", "print", "(Ljava/lang/String;JLjava/lang/Object;)V", false);
            }
        }
        super.visitInsn(opcode);
    }
}
