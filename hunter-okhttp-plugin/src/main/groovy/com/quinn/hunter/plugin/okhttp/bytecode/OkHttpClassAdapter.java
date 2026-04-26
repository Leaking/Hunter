package com.quinn.hunter.plugin.okhttp.bytecode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public final class OkHttpClassAdapter extends ClassVisitor {

    private String className;
    private final boolean weaveEventListener;

    public OkHttpClassAdapter(final ClassVisitor cv, boolean weaveEventListener) {
        super(Opcodes.ASM9, cv);
        this.weaveEventListener = weaveEventListener;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (mv != null && "okhttp3/OkHttpClient$Builder".equals(className) && "<init>".equals(name)) {
            return new OkHttpMethodAdapter(access, desc, mv, weaveEventListener);
        }
        return mv;
    }
}
