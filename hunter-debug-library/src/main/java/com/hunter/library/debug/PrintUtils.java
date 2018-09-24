package com.hunter.library.debug;

import android.util.Log;
import java.util.List;

public class PrintUtils {
    public static void printParameters(String tag, List<String> paramNames, Object...paramValues) {
        if(paramNames.size() == 0 || paramNames.size() != paramValues.length) {
            return;
        }
        for(int index = 0; index < paramNames.size(); index++) {
            Log.i(tag, paramNames.get(index) + " > " + paramValues[index]);
        }
    }

    private String tag;
    private StringBuilder result = new StringBuilder();

    public PrintUtils(String tag){
        this.tag = tag;
    }

    public PrintUtils append(String name, int val) {
        result.append(name + " > " + val);
        return this;
    }

    public PrintUtils append(String name, boolean val) {
        result.append(name + " > " + val);
        return this;
    }

    public PrintUtils append(String name, short val) {
        result.append(name + " > " + val);
        return this;
    }

    public PrintUtils append(String name, byte val) {
        result.append(name + " > " + val);
        return this;
    }

    public PrintUtils append(String name, char val) {
        result.append(name + " > " + val);
        return this;
    }

    public PrintUtils append(String name, long val) {
        result.append(name + " > " + val);
        return this;
    }

    public PrintUtils append(String name, double val) {
        result.append(name + " > " + val);
        return this;
    }

    public PrintUtils append(String name, float val) {
        result.append(name + " > " + val);
        return this;
    }

    public PrintUtils append(String name, Object val) {
        result.append(name + " > " + val);
        return this;
    }

    public void print(){
        Log.i(tag, result.toString());
    }
}



