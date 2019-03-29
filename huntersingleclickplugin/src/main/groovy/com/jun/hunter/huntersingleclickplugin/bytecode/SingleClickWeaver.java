package com.jun.hunter.huntersingleclickplugin.bytecode;

import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;


public final class SingleClickWeaver extends BaseWeaver {

    private static final String PLUGIN_LIBRARY = "com.jun.hunter.huntersingleclicklibrary";
    private static final String ANDROID_LIBRARY = "android";

    @Override
    public void setExtension(Object extension) {
    }

    @Override
    public boolean isWeavableClass(String fullQualifiedClassName) {
        boolean superResult = super.isWeavableClass(fullQualifiedClassName);
        boolean isByteCodePlugin = fullQualifiedClassName.startsWith(PLUGIN_LIBRARY) || fullQualifiedClassName.startsWith(ANDROID_LIBRARY);

        return superResult && !isByteCodePlugin;
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new SingleClickClassAdapter(classWriter);
    }

}
