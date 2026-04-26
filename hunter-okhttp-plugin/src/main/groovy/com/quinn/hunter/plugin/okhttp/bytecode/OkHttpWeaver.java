package com.quinn.hunter.plugin.okhttp.bytecode;

import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;

public final class OkHttpWeaver extends BaseWeaver {

    private final boolean weaveEventListener;

    public OkHttpWeaver(boolean weaveEventListener) {
        this.weaveEventListener = weaveEventListener;
    }

    @Override
    public boolean isWeavableClass(String fullyQualifiedClassName) {
        if (!super.isWeavableClass(fullyQualifiedClassName)) return false;
        // Only the OkHttpClient.Builder class actually needs visiting; AGP still
        // calls isInstrumentable for every class so this short-circuits the
        // expensive ClassVisitor chain on the rest of the project.
        return "okhttp3.OkHttpClient$Builder".equals(fullyQualifiedClassName);
    }

    @Override
    public ClassVisitor wrap(ClassVisitor next) {
        return new OkHttpClassAdapter(next, weaveEventListener);
    }
}
