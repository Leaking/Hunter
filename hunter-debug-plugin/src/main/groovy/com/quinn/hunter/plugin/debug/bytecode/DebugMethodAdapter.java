package com.quinn.hunter.plugin.debug.bytecode;

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
    private static final String LCOM_HUNTER_LIBRARY_DEBUG_HUNTER_DEBUG_NO_PARAMETER = "Lcom/hunter/library/debug/HunterDebugSimple;";
    private static final String LCOM_HUNTER_LIBRARY_DEBUG_HUNTER_DEBUG_IMPL = "Lcom/hunter/library/debug/HunterDebugImpl;";
    private static final String LCOM_HUNTER_LIBRARY_DEBUG_HUNTER_DEBUG = "Lcom/hunter/library/debug/HunterDebug;";
    private List<Parameter> parameters;
    private String className;
    private String methodName;
    private boolean debugMethod = false;
    private boolean debugMethodWithCustomLogger = false;
    private int timingStartVarIndex;
    private String methodDesc;
    private boolean printParameter = true;
 

    public DebugMethodAdapter(String className, List<Parameter> parameters, String name, int access, String desc,
            MethodVisitor mv) {
        super(Opcodes.ASM5, access, desc, mv);
        if (!className.endsWith("/")) {
            this.className = className.substring(className.lastIndexOf("/") + 1);
        } else {
            this.className = className;
        }
        this.parameters = parameters;
        this.methodName = name;
        this.methodDesc = desc;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor defaultAv = super.visitAnnotation(desc, visible);
        if (LCOM_HUNTER_LIBRARY_DEBUG_HUNTER_DEBUG.equals(desc)) {
            debugMethod = true;
            printParameter=true;
        } else if (LCOM_HUNTER_LIBRARY_DEBUG_HUNTER_DEBUG_IMPL.equals(desc)) {
            debugMethodWithCustomLogger = true;
            printParameter=true;
        } else if (LCOM_HUNTER_LIBRARY_DEBUG_HUNTER_DEBUG_NO_PARAMETER.equals(desc)) {
            debugMethod = true;
            printParameter = false;
        }
        return defaultAv;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        if (!debugMethod && !debugMethodWithCustomLogger)
            return;
        int printUtilsVarIndex = newLocal(Type.getObjectType("com/hunter/library/debug/ParameterPrinter"));
        // new ParameterPrinter 
        mv.visitTypeInsn(NEW, "com/hunter/library/debug/ParameterPrinter");
        mv.visitInsn(DUP);
        // set Constructor parameter1 
        mv.visitLdcInsn(className);
        // set Constructor parameter1 
        mv.visitLdcInsn(methodName);
        // Constructor <init>,(Ljava/lang/String;Ljava/lang/String;)V parameter
        mv.visitMethodInsn(INVOKESPECIAL, "com/hunter/library/debug/ParameterPrinter", "<init>",
                "(Ljava/lang/String;Ljava/lang/String;)V", false);

        if(printParameter){      
            mv.visitVarInsn(ASTORE, printUtilsVarIndex);
            for (int i = 0; i < parameters.size(); i++) {
                Parameter parameter = parameters.get(i);
                String name = parameter.name;
                String desc = parameter.desc;
                int index = parameter.index;
                int opcode = Utils.getLoadOpcodeFromDesc(desc);
                String fullyDesc = String.format("(Ljava/lang/String;%s)Lcom/hunter/library/debug/ParameterPrinter;", desc);
                visitPrint(printUtilsVarIndex, index, opcode, name, fullyDesc);
            }
            mv.visitVarInsn(ALOAD, printUtilsVarIndex);
        }else{
            // don't print parameter.
        }

        if (debugMethod) {
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/hunter/library/debug/ParameterPrinter", "print", "()V", false);
        } else if (debugMethodWithCustomLogger) {
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/hunter/library/debug/ParameterPrinter", "printWithCustomLogger",
                    "()V", false);
        }
        // Timing,record enter time.
        timingStartVarIndex = newLocal(Type.LONG_TYPE);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        // store value to timingStartVarIndex register
        mv.visitVarInsn(Opcodes.LSTORE, timingStartVarIndex);
    }

    private void visitPrint(int varIndex, int localIndex, int opcode, String name, String desc) {
        mv.visitVarInsn(ALOAD, varIndex);
        mv.visitLdcInsn(name);
        mv.visitVarInsn(opcode, localIndex);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/hunter/library/debug/ParameterPrinter", "append", desc, false);
        mv.visitInsn(POP);
    }

    @Override
    public void visitInsn(int opcode) {
        if ((debugMethod || debugMethodWithCustomLogger)
                && ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW)) {
            Type returnType = Type.getReturnType(methodDesc);
            String returnDesc = methodDesc.substring(methodDesc.indexOf(")") + 1);
            if (returnDesc.startsWith("[") || returnDesc.startsWith("L")) {
                returnDesc = "Ljava/lang/Object;"; // regard object extended from Object or array as object
            }
        
            int resultTempValIndex = -1;
            if(printParameter){
                // store origin return value
                if (returnType != Type.VOID_TYPE || opcode == ATHROW) {
                    resultTempValIndex = newLocal(returnType);
                    int storeOpcocde = Utils.getStoreOpcodeFromType(returnType);
                    if (opcode == ATHROW)
                        storeOpcocde = ASTORE;
                    mv.visitVarInsn(storeOpcocde, resultTempValIndex);
                }
            }
            // get exit time
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LLOAD, timingStartVarIndex);// load enter time from timingStartVarIndex reg.
            mv.visitInsn(LSUB);// sub ,exit-enter.
            int index = newLocal(Type.LONG_TYPE);// store (exit-enter) value
            mv.visitVarInsn(LSTORE, index);
            mv.visitLdcInsn(className); // parameter 1 string, print TAG
            mv.visitLdcInsn(methodName); // parameter 2 string,print methodName
            mv.visitVarInsn(LLOAD, index); // parameter 3 long,print time

            if(printParameter){
            // parameter 4, check the return value.
            if ( returnType != Type.VOID_TYPE || opcode == ATHROW) {
                System.out.println("hello world returnType ="+returnType);
                int loadOpcode = Utils.getLoadOpcodeFromType(returnType);
                if (opcode == ATHROW) {
                    loadOpcode = ALOAD;
                    returnDesc = "Ljava/lang/Object;";
                }
                // load return val reg index.
                mv.visitVarInsn(loadOpcode, resultTempValIndex);
                String formatDesc = String.format("(Ljava/lang/String;Ljava/lang/String;J%s)V", returnDesc);
                // print(tag,methodname,time,returnvalue)
                if (debugMethod) {
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/debug/ResultPrinter", "print", formatDesc,
                            false);
                } else {
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/debug/ResultPrinter", "printWithCustomLogger",
                            formatDesc, false);
                }
                // re-load return value,restore.
                mv.visitVarInsn(loadOpcode, resultTempValIndex);
            } else {
                mv.visitLdcInsn("void");
                if (debugMethod) {
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/debug/ResultPrinter", "print",
                            "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/Object;)V", false);
                } else {
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/debug/ResultPrinter", "printWithCustomLogger",
                            "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/Object;)V", false);
                }
            }
        }else{
            // only print returnType descriptor
            String[] returnStrings = returnType.getClassName().split("\\.");
            if(returnStrings.length >= 1){
                mv.visitLdcInsn(returnStrings[returnStrings.length-1]);
            }else{
                mv.visitLdcInsn(returnStrings[0]);
            }
            
            if (debugMethod) {
                mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/debug/ResultPrinter", "print",
                        "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/Object;)V", false);
            } else {
                mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/debug/ResultPrinter", "printWithCustomLogger",
                        "(Ljava/lang/String;Ljava/lang/String;JLjava/lang/Object;)V", false);
            }
        }
        }
        
        super.visitInsn(opcode);
    }

}
