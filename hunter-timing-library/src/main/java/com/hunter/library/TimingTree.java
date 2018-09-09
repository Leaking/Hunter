package com.hunter.library;

import java.util.ArrayList;
import java.util.List;

public class TimingTree {

    public String name;
    //if "methodFinish" is false, there is not costedTime until "methodFinish " is true
    public boolean methodFinish;
    public long costedTime;
    public List<TimingTree> children = new ArrayList<>();
    public TimingTree parent;

}
