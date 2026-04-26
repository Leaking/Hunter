package com.quinn.hunter.plugin.timing;

import com.quinn.hunter.transform.RunVariant;

import java.util.ArrayList;
import java.util.List;

/**
 * whitelist takes precedence over blacklist.
 */
public class TimingHunterExtension {

    public RunVariant runVariant = RunVariant.ALWAYS;
    public List<String> whitelist = new ArrayList<>();
    public List<String> blacklist = new ArrayList<>();

    @Override
    public String toString() {
        return "TimingHunterExtension{" +
                "runVariant=" + runVariant +
                ", whitelist=" + whitelist +
                ", blacklist=" + blacklist +
                '}';
    }
}
