package com.quinn.hunter.plugin.timing.bytecode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.util.Arrays;

public final class TimingClassAdapter extends ClassVisitor {

    private String className;
    private boolean isHeritedFromBlockHandler = false;

    public TimingClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM9, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.isHeritedFromBlockHandler = Arrays.toString(interfaces).contains("com/hunter/library/timing/IBlockHandler");
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (mv == null || isHeritedFromBlockHandler) {
            return mv;
        }
        // Issue #48: constructors require deferring the timing-start probe until
        // after the mandatory super()/this() call; abstract / native methods have
        // no body so we leave them untouched.
        if ((access & (Opcodes.ACC_ABSTRACT | Opcodes.ACC_NATIVE)) != 0) {
            return mv;
        }
        boolean isConstructor = "<init>".equals(name);
        return new TimingMethodAdapter(className + File.separator + name, access, desc, mv, isConstructor);
    }
}
