package com.quinn.hunter.plugin.okhttp.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.okhttp.OkHttpHunterTransform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;

public class OkHttpClassAdapter extends ClassVisitor{

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(OkHttpClassAdapter.class);

    private String className;

    public OkHttpClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
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
        if(className.equals("okhttp3/OkHttpClient$Builder")) {
            return mv == null ? null : new OkHttpMethodAdapter(className + File.separator + name, access, desc, mv);
        } else {
            return mv;
        }
    }



}