package com.quinn.hunter.plugin.okhttp.bytecode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Quinn on 09/09/2018.
 */
public final class OkHttpClassAdapter extends ClassVisitor {

    private String className;

    private boolean weaveEventListener;

    OkHttpClassAdapter(final ClassVisitor cv, boolean weaveEventListener) {
        super(Opcodes.ASM7, cv);
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
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (className.equals("okhttp3/OkHttpClient$Builder") && name.equals("<init>")) {
            return mv == null ? null : new OkHttpMethodAdapter(access, desc, mv);
        } else if (className.equals("okhttp3/OkHttpClient") && name.equals("<init>") && desc.equals("(Lokhttp3/OkHttpClient$Builder;)V")) {
            return mv == null ? null : new OkHttpClientMethodAdapter(mv, weaveEventListener);
        } else {
            return mv;
        }
    }

}