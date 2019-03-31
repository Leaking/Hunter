package com.jun.hunter.huntersingleclickplugin;

import com.quinn.hunter.transform.RunVariant;


public class SingleClickHunterExtension {

    public RunVariant runVariant = RunVariant.ALWAYS;
    public boolean duplcatedClassSafeMode = false;
    @Override
    public String toString() {
        return "SingleClickHunterExtension{" +
                "runVariant=" + runVariant +
                ", duplcatedClassSafeMode=" + duplcatedClassSafeMode +
                '}';
    }
}
