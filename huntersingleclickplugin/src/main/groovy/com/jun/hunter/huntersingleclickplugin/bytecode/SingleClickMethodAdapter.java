package com.jun.hunter.huntersingleclickplugin.bytecode;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by Quinn on 15/09/2018.
 */
public final class SingleClickMethodAdapter extends MethodVisitor implements Opcodes {

    private boolean noSingleClick = false;


    public SingleClickMethodAdapter(MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
    }


    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor defaultAv = super.visitAnnotation(desc, visible);
        if ("Lcom/jun/hunter/huntersingleclicklibrary/NoSingleClick;".equals(desc)) {
            noSingleClick = true;
        }
        return defaultAv;
    }


    @Override
    public void visitCode() {
        if (noSingleClick) {
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKESTATIC, "com/jun/hunter/huntersingleclicklibrary/ClickUtils", "isFastDoubleClick", "(Landroid/view/View;)Z", false);
            mv.visitVarInsn(ISTORE, 2);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitVarInsn(ILOAD, 2);
            Label l2 = new Label();
            mv.visitJumpInsn(IFEQ, l2);
            mv.visitInsn(RETURN);
            mv.visitLabel(l2);
        }
        super.visitCode();
    }

}
