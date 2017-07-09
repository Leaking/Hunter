package com.lolita.plugin.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.MethodVisitor;

class MethodAdapter extends MethodVisitor implements Opcodes {

    public MethodAdapter(final MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        /* do call */
        mv.visitMethodInsn(opcode, owner, name, desc, itf);
    }


    @Override
    public void visitCode() {
//        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "err", "Ljava/io/PrintStream;");
//        mv.visitLdcInsn("visitCode start");
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitVarInsn(LSTORE, 11);

        super.visitCode();
    }


    @Override
    public void visitInsn(int opcode) {

        switch(opcode) {
            case Opcodes.IRETURN:
            case Opcodes.FRETURN:
            case Opcodes.ARETURN:
            case Opcodes.LRETURN:
            case Opcodes.DRETURN:
            case Opcodes.RETURN:
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "err", "Ljava/io/PrintStream;");
                mv.visitLdcInsn("visitCode end");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

//                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
//                mv.visitVarInsn(LSTORE, 12);
//
//
//                mv.visitVarInsn(LLOAD, 12);
//                mv.visitVarInsn(LLOAD, 11);
//                mv.visitInsn(LSUB);
//                mv.visitVarInsn(LSTORE, 13);
//
//                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//                mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
//                mv.visitInsn(DUP);
//                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
//                mv.visitLdcInsn("costed = ");
//                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//                mv.visitVarInsn(LLOAD, 13);
//                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
//                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
//                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

                break;
        }
        super.visitInsn(opcode);

    }
}
