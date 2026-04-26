package com.quinn.hunter.plugin.linelog.bytecode;

import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;

public final class LinelogWeaver extends BaseWeaver {

    private static final String PLUGIN_LIBRARY_PREFIX = "com.hunter.library.linelog";

    @Override
    public boolean isWeavableClass(String fullyQualifiedClassName) {
        return super.isWeavableClass(fullyQualifiedClassName)
                && !fullyQualifiedClassName.startsWith(PLUGIN_LIBRARY_PREFIX);
    }

    @Override
    public ClassVisitor wrap(ClassVisitor next) {
        return new LinelogClassAdapter(next);
    }
}
