package com.quinn.hunter.plugin.timing.bytecode;

import com.android.build.api.instrumentation.ClassContext;

import com.quinn.hunter.plugin.timing.TimingHunterParameters;
import com.quinn.hunter.transform.HunterAsmClassVisitorFactory;
import com.quinn.hunter.transform.asm.IWeaver;

public abstract class TimingClassVisitorFactory
        extends HunterAsmClassVisitorFactory<TimingHunterParameters> {

    @Override
    protected IWeaver createWeaver(ClassContext classContext) {
        TimingHunterParameters params = getParameters().get();
        return new TimingWeaver(
                params.getWhitelist().getOrElse(java.util.Collections.emptyList()),
                params.getBlacklist().getOrElse(java.util.Collections.emptyList()));
    }
}
