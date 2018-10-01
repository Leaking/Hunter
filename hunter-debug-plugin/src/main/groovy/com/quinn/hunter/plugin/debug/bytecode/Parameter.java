package com.quinn.hunter.plugin.debug.bytecode;

public class Parameter {

    public final String name;
    public final String desc;
    public final int index;

    public Parameter(String name, String desc, int index){
        this.name = name;
        this.desc = desc;
        this.index = index;
    }
}
