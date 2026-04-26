package com.quinn.hunter.plugin.debug;

import com.quinn.hunter.transform.RunVariant;

public class DebugHunterExtension {

    public RunVariant runVariant = RunVariant.ALWAYS;

    @Override
    public String toString() {
        return "DebugHunterExtension{runVariant=" + runVariant + '}';
    }
}
