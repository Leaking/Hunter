package com.quinn.hunter.plugin.linelog.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Quinn on 15/09/2018.
 */
public final class LinelogMethodAdapter extends MethodVisitor implements Opcodes {

    private int lineNunber;
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(LinelogMethodAdapter.class);

    public LinelogMethodAdapter(MethodVisitor mv) {
        super(Opcodes.ASM7, mv);
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        this.lineNunber = line;
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if("android/util/Log".equals(owner)) {
            String linenumberConst = lineNunber + "";
            if("i".equals(name)) {
                if("(Ljava/lang/String;Ljava/lang/String;)I".equals(desc)) {
                    mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "i", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I", false);
                } else if("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I".equals(desc)) {
                    mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "i", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)I", false);
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            } else if("d".equals(name)) {
                if("(Ljava/lang/String;Ljava/lang/String;)I".equals(desc)) {
                    mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "d", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I", false);
                } else if("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I".equals(desc)) {
                    mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "d", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)I", false);
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            } else if("v".equals(name)) {
                if("(Ljava/lang/String;Ljava/lang/String;)I".equals(desc)) {
                    mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "v", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I", false);
                } else if("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I".equals(desc)) {
                    mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "v", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)I", false);
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            } else if("e".equals(name)) {
                if("(Ljava/lang/String;Ljava/lang/String;)I".equals(desc)) {
                    mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "e", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I", false);
                } else if("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I".equals(desc)) {
                    mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "e", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)I", false);
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            } else if("w".equals(name)) {
                if("(Ljava/lang/String;Ljava/lang/String;)I".equals(desc)) {
                    mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "w", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I", false);
                } else if("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I".equals(desc)) {
                    mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "w", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)I", false);
                } else if("(Ljava/lang/String;Ljava/lang/Throwable;)I".equals(desc)) {
                    mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "w", "(Ljava/lang/String;Ljava/lang/Throwable;Ljava/lang/String;)I", false);
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            } else if("println".equals(name)) {
                if("(ILjava/lang/String;Ljava/lang/String;)I".equals(desc)) {
                    mv.visitLdcInsn(linenumberConst);
                    mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/linelog/LineNumberLog", "println", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)I", false);
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            } else {
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        } else {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }

}
