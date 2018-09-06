package com.quinn.hunter.plugin.log;

/**
 * Created by quinn on 31/08/2018
 */
public class Logging {

    private final String tag;

    public Logging(String tag){
        this.tag = tag;
    }

    public final static Logging getLogger(String tag) {
        return new Logging(tag);
    }

    public void log(String log) {
        println([tag] + ":" + log)
    }

}
