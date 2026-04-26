package com.quinn.hunter.plugin.linelog.bytecode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Quinn on 15/09/2018.
 */
public final class LinelogClassAdapter extends ClassVisitor{

    public LinelogClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM9, cv);
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        return mv == null ? null : new LinelogMethodAdapter(mv);
    }

}