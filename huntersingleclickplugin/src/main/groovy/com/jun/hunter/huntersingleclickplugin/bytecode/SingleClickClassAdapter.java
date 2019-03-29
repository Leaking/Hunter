package com.jun.hunter.huntersingleclickplugin.bytecode;

import com.jun.hunter.huntersingleclickplugin.utils.FilterUtil;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public final class SingleClickClassAdapter extends ClassVisitor {

    //是否命中相关类
    private boolean isHintClass = false;


    SingleClickClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }


    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        isHintClass = FilterUtil.isMatchingClass(interfaces);
        super.visit(version, access, name, signature, superName, interfaces);
    }


    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        //命中相关类（实现OnClickListener的类）并且命中相关方法（onclick方法）
        if (isHintClass && FilterUtil.isMatchingMethod(name, desc)) {
            return mv == null ? null : new SingleClickMethodAdapter(mv);
        } else
            return mv;
    }

}