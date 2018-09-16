package com.quinn.hunter.plugin.debug.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;
import com.android.ddmlib.Log;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

public final class DebugMethodAdapter extends MethodVisitor implements Opcodes {

    private int startVarIndex;

    private int lineNunber;
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugMethodAdapter.class);


    public DebugMethodAdapter(String name, MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
        logger.info("weave method " + name);
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        this.lineNunber = line;
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if("android/util/Log".equals(owner) && "i".equals(name)) {
            mv.visitLdcInsn(lineNunber + "");
            mv.visitMethodInsn(INVOKESTATIC, "com/hunter/library/debug/LineNumberLog", "log", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I", false);
        } else {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }

    @Override
    public void visitInsn(int opcode) {
        logger.info("visitInsn " + opcode);
        super.visitInsn(opcode);
    }
}
