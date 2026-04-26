package com.quinn.hunter.plugin.timing.bytecode;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

public final class TimingMethodAdapter extends LocalVariablesSorter implements Opcodes {

    private final String methodName;
    private final boolean isConstructor;

    private int startVarIndex = -1;
    // For constructors, defer probe injection until after the mandatory
    // super(...)/this(...) call. Tracks new/dup pairs so we only count the
    // *outermost* INVOKESPECIAL <init>.
    private int newDepth = 0;
    private boolean superCallSeen = false;

    public TimingMethodAdapter(String name, int access, String desc, MethodVisitor mv, boolean isConstructor) {
        super(Opcodes.ASM9, access, desc, mv);
        this.methodName = name.replace("/", ".");
        this.isConstructor = isConstructor;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        if (!isConstructor) {
            emitProbeStart();
        }
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        if (isConstructor && !superCallSeen && opcode == NEW) {
            newDepth++;
        }
        super.visitTypeInsn(opcode, type);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        if (isConstructor && !superCallSeen
                && opcode == INVOKESPECIAL && "<init>".equals(name)) {
            if (newDepth > 0) {
                newDepth--;
            } else {
                // This is the mandatory super(...)/this(...) call; safe to probe now.
                superCallSeen = true;
                emitProbeStart();
            }
        }
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            // If we never managed to install the start probe (e.g. exotic
            // bytecode), skip the end probe to avoid VerifyError.
            if (startVarIndex >= 0) {
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                mv.visitVarInsn(LLOAD, startVarIndex);
                mv.visitInsn(LSUB);
                int index = newLocal(Type.LONG_TYPE);
                mv.visitVarInsn(LSTORE, index);
                mv.visitLdcInsn(methodName);
                mv.visitVarInsn(LLOAD, index);
                mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/timing/BlockManager",
                        "timingMethod", "(Ljava/lang/String;J)V", false);
            }
        }
        super.visitInsn(opcode);
    }

    private void emitProbeStart() {
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        startVarIndex = newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(LSTORE, startVarIndex);
    }
}
