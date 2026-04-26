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
    // Defer start probe until the first real instruction so that empty methods
    // (e.g. empty overrides with just a RETURN) are not instrumented.
    private boolean startProbeDeferred = false;

    public TimingMethodAdapter(String name, int access, String desc, MethodVisitor mv, boolean isConstructor) {
        super(Opcodes.ASM9, access, desc, mv);
        this.methodName = name.replace("/", ".");
        this.isConstructor = isConstructor;
    }

    @Override
    public void visitCode() {
        super.visitCode();
        if (!isConstructor) {
            startProbeDeferred = true;
        }
    }

    private void emitDeferredStartProbe() {
        if (startProbeDeferred) {
            startProbeDeferred = false;
            emitProbeStart();
        }
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        if (isConstructor && !superCallSeen && opcode == NEW) {
            newDepth++;
        } else {
            emitDeferredStartProbe();
        }
        super.visitTypeInsn(opcode, type);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (isConstructor && !superCallSeen
                && opcode == INVOKESPECIAL && "<init>".equals(name)) {
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            if (newDepth > 0) {
                newDepth--;
            } else {
                // This is the mandatory super(...)/this(...) call; defer probe
                // so that empty constructors (just super + return) are skipped.
                superCallSeen = true;
                startProbeDeferred = true;
            }
            return;
        }
        emitDeferredStartProbe();
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            // If we never managed to install the start probe (e.g. empty method
            // or exotic bytecode), skip the end probe to avoid VerifyError.
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
        } else {
            emitDeferredStartProbe();
        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        emitDeferredStartProbe();
        super.visitIntInsn(opcode, operand);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        emitDeferredStartProbe();
        super.visitVarInsn(opcode, var);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        emitDeferredStartProbe();
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    public void visitJumpInsn(int opcode, org.objectweb.asm.Label label) {
        emitDeferredStartProbe();
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLdcInsn(Object value) {
        emitDeferredStartProbe();
        super.visitLdcInsn(value);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        emitDeferredStartProbe();
        super.visitIincInsn(var, increment);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, org.objectweb.asm.Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        emitDeferredStartProbe();
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, org.objectweb.asm.Label dflt, org.objectweb.asm.Label... labels) {
        emitDeferredStartProbe();
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override
    public void visitLookupSwitchInsn(org.objectweb.asm.Label dflt, int[] keys, org.objectweb.asm.Label[] labels) {
        emitDeferredStartProbe();
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }

    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        emitDeferredStartProbe();
        super.visitMultiANewArrayInsn(descriptor, numDimensions);
    }

    private void emitProbeStart() {
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        startVarIndex = newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(LSTORE, startVarIndex);
    }
}
