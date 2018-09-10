package com.quinn.hunter.plugin.okhttp.bytecode;


import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Created by Quinn on 09/09/2018.
 */
public final class OkHttpWeaver extends BaseWeaver {

    private static final String PLUGIN_LIBRARY = "com/hunter/library";

    @Override
    public boolean isWeavableJarClass(String entryName) {
        return isWeavableClass(entryName) && !entryName.startsWith(PLUGIN_LIBRARY);
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new OkHttpClassAdapter(classWriter);
    }

}
