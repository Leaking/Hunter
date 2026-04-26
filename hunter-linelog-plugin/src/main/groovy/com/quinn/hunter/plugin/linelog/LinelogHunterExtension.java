package com.quinn.hunter.plugin.linelog;

import com.quinn.hunter.transform.RunVariant;

public class LinelogHunterExtension {

    public RunVariant runVariant = RunVariant.ALWAYS;

    @Override
    public String toString() {
        return "LinelogHunterExtension{runVariant=" + runVariant + '}';
    }
}
