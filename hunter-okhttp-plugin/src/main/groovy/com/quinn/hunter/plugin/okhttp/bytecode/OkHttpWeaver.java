package com.quinn.hunter.plugin.okhttp.bytecode;

import com.quinn.hunter.plugin.okhttp.OkHttpHunterExtension;
import com.quinn.hunter.transform.asm.BaseWeaver;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Created by Quinn on 09/09/2018.
 */
public final class OkHttpWeaver extends BaseWeaver {

    private OkHttpHunterExtension okHttpHunterExtension;

    @Override
    public void setExtension(Object extension) {
        if(extension == null) return;
        this.okHttpHunterExtension = (OkHttpHunterExtension) extension;
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new OkHttpClassAdapter(classWriter, this.okHttpHunterExtension.weaveEventListener);
    }

}
