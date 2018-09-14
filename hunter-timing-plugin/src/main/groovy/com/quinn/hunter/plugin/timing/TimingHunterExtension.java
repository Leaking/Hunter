package com.quinn.hunter.plugin.timing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by quinn on 27/06/2017.
 */

public class TimingHunterExtension {

    public String on = "true";
    public String global = "true";
    public List<String> blacklist = new ArrayList<>();

    @Override
    public String toString() {
        return "TimingHunterExtension{" +
                "on='" + on + '\'' +
                ", global='" + global + '\'' +
                ", blacklist=" + Arrays.toString(blacklist.toArray()) +
                '}';
    }
}
