package com.quinn.hunter.plugin.linelog.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public final class LinelogMethodAdapter extends MethodVisitor implements Opcodes {

    private String className;
    private int lineNunber;
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(LinelogMethodAdapter.class);

    public LinelogMethodAdapter(String className, MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
        this.className = className;
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        this.lineNunber = line;
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if("android/util/Log".equals(owner) && "i".equals(name)) {
            logger.info("visitMethodInsn " + className);
            mv.visitLdcInsn(lineNunber + "");
            mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "log", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I", false);
        } else {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }

}
