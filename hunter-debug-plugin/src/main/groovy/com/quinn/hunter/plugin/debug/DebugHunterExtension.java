package com.quinn.hunter.plugin.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by quinn on 27/06/2017.
 * whitelist is prior to to blacklist
 */
public class DebugHunterExtension {

    public List<String> whitelist = new ArrayList<>();
    public List<String> blacklist = new ArrayList<>();

    @Override
    public String toString() {
        return "DebugHunterExtension{" +
                "whitelist=" + Arrays.toString(whitelist.toArray()) +
                ", blacklist=" + Arrays.toString(blacklist.toArray()) +
                '}';
    }
}
