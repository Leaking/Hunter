package com.quinn.hunter.plugin.timing.bytecode;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

public final class TimingMethodAdapter extends LocalVariablesSorter implements Opcodes {

    private int startVarIndex;

    private String methodName;

    public TimingMethodAdapter(String name, int access, String desc, MethodVisitor mv) {
        super(Opcodes.ASM7, access, desc, mv);
        this.methodName = name.replace("/", ".");
    }

    @Override
    public void visitCode() {
        super.visitCode();
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        startVarIndex = newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(Opcodes.LSTORE, startVarIndex);
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(LLOAD, startVarIndex);
            mv.visitInsn(LSUB);
            int index = newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(LSTORE, index);
            mv.visitLdcInsn(methodName);
            mv.visitVarInsn(LLOAD, index);
            mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/timing/BlockManager", "timingMethod", "(Ljava/lang/String;J)V", false);
        }
        super.visitInsn(opcode);
    }

}
