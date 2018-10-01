package com.hunter.library.debug;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class PrintUtils {

    private static final String HUNTER_DEBUG_TAG = "Hunter-Debug";

    private StringBuilder result = new StringBuilder();

    private int paramIndex = 0;

    private String printFormat = "%ss=\"%s\"";

    private String divider = ", ";

    public PrintUtils(String tag){
        result.append(tag).append("[");
    }

    public PrintUtils append(String name, int val) {
        if(paramIndex++ != 0) result.append(divider);
        result.append(String.format(printFormat, name, val));
        return this;
    }

    public PrintUtils append(String name, boolean val) {
        if(paramIndex++ != 0) result.append(divider);
        result.append(String.format(printFormat, name, val));
        return this;
    }

    public PrintUtils append(String name, short val) {
        if(paramIndex++ != 0) result.append(divider);
        result.append(String.format(printFormat, name, val));
        return this;
    }

    public PrintUtils append(String name, byte val) {
        if(paramIndex++ != 0) result.append(divider);
        result.append(String.format(printFormat, name, val));
        return this;
    }

    public PrintUtils append(String name, char val) {
        if(paramIndex++ != 0) result.append(divider);
        result.append(String.format(printFormat, name, val));
        return this;
    }

    public PrintUtils append(String name, long val) {
        if(paramIndex++ != 0) result.append(divider);
        result.append(String.format(printFormat, name, val));
        return this;
    }

    public PrintUtils append(String name, double val) {
        if(paramIndex++ != 0) result.append(divider);
        result.append(String.format(printFormat, name, val));
        return this;
    }

    public PrintUtils append(String name, float val) {
        if(paramIndex++ != 0) result.append(divider);
        result.append(String.format(printFormat, name, val));
        return this;
    }

    public PrintUtils append(String name, Object val) {
        if(paramIndex++ != 0) result.append(divider);
        if(val != null && val.getClass().isArray()){
            result.append(String.format(printFormat, name, arrayToString(val)));
        } else {
            result.append(String.format(printFormat, name, val));
        }
        return this;
    }

    private String arrayToString(Object val) {
        if (!(val instanceof Object[])) {
            if (val instanceof int[]) {
                return Arrays.toString((int[])val);
            } else if (val instanceof char[]) {
                return Arrays.toString((char[])val);
            } else if (val instanceof boolean[]) {
                return Arrays.toString((boolean[])val);
            } else if (val instanceof byte[]) {
                return Arrays.toString((byte[])val);
            } else if (val instanceof long[]) {
                return Arrays.toString((long[])val);
            } else if (val instanceof double[]) {
                return Arrays.toString((double[])val);
            } else if (val instanceof float[]) {
                return Arrays.toString((float[])val);
            } else if (val instanceof short[]) {
                return Arrays.toString((short[])val);
            } else {
                return "Unknown type array";
            }
        } else {
            return Arrays.deepToString((Object[])val);
        }
    }

    public void print(){
        result.append("]");
        Log.i(HUNTER_DEBUG_TAG, result.toString());
    }
}



