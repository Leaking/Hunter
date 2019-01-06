package com.quinn.hunter.plugin.debug;

import com.quinn.hunter.transform.RunVariant;

/**
 * Created by Quinn on 07/10/2018.
 */
public class DebugHunterExtension {

    public RunVariant runVariant = RunVariant.ALWAYS;
    public boolean duplcatedClassSafeMode = false;

    @Override
    public String toString() {
        return "DebugHunterExtension{" +
                "runVariant=" + runVariant +
                ", duplcatedClassSafeMode=" + duplcatedClassSafeMode +
                '}';
    }
}
