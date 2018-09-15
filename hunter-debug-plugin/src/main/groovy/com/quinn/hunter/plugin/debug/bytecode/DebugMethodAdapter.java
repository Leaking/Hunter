package com.quinn.hunter.plugin.debug.bytecode;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

public final class DebugMethodAdapter extends LocalVariablesSorter implements Opcodes {

    private int startVarIndex;

    private String methodName;

    public DebugMethodAdapter(String name, int access, String desc, MethodVisitor mv) {
        super(Opcodes.ASM5, access, desc, mv);
        this.methodName = name.replace("/", ".");
    }

    @Override
    public void visitCode() {
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
        }
        super.visitInsn(opcode);
    }

}
