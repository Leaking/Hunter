package com.quinn.hunter.plugin.okhttp.bytecode;

import com.android.build.api.instrumentation.ClassContext;

import com.quinn.hunter.plugin.okhttp.OkHttpHunterParameters;
import com.quinn.hunter.transform.HunterAsmClassVisitorFactory;
import com.quinn.hunter.transform.asm.IWeaver;

public abstract class OkHttpClassVisitorFactory extends HunterAsmClassVisitorFactory<OkHttpHunterParameters> {

    @Override
    protected IWeaver createWeaver(ClassContext classContext) {
        boolean weaveEventListener = getParameters().get().getWeaveEventListener().getOrElse(Boolean.TRUE);
        return new OkHttpWeaver(weaveEventListener);
    }
}
