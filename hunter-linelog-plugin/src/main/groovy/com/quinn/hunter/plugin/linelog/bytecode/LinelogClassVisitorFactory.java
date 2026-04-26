package com.quinn.hunter.plugin.linelog.bytecode;

import com.android.build.api.instrumentation.ClassContext;

import com.quinn.hunter.transform.HunterAsmClassVisitorFactory;
import com.quinn.hunter.transform.HunterParameters;
import com.quinn.hunter.transform.asm.IWeaver;

public abstract class LinelogClassVisitorFactory extends HunterAsmClassVisitorFactory<HunterParameters> {

    @Override
    protected IWeaver createWeaver(ClassContext classContext) {
        return new LinelogWeaver();
    }
}
