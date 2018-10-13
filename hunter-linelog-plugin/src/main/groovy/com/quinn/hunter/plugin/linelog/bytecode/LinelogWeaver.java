package com.quinn.hunter.plugin.linelog.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.transform.asm.BaseWeaver;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Created by Quinn on 09/07/2017.
 */
public final class LinelogWeaver extends BaseWeaver {

    private static final String PLUGIN_LIBRARY = "com.hunter.library.linelog";
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(LinelogWeaver.class);

    @Override
    public void setExtension(Object extension) {
    }

    @Override
    public boolean isWeavableClass(String fullQualifiedClassName) {
        boolean superResult = super.isWeavableClass(fullQualifiedClassName);
        boolean isByteCodePlugin = fullQualifiedClassName.startsWith(PLUGIN_LIBRARY);
        return superResult && !isByteCodePlugin;
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new LinelogClassAdapter(classWriter);
    }

}
