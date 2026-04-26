package com.quinn.hunter.plugin.debug.bytecode;

import com.android.build.api.instrumentation.ClassContext;

import com.quinn.hunter.transform.HunterAsmClassVisitorFactory;
import com.quinn.hunter.transform.HunterParameters;
import com.quinn.hunter.transform.asm.IWeaver;

public abstract class DebugClassVisitorFactory extends HunterAsmClassVisitorFactory<HunterParameters> {

    @Override
    protected IWeaver createWeaver(ClassContext classContext) {
        return new DebugWeaver();
    }
}
