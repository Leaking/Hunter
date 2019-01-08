package com.hunter.library.debug;

import android.util.Log;

import java.util.Arrays;

public class ResultPrinter {

    public static void print(String className, String methodName, long costedMilles, byte returnVal) {
        Log.i(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String className, String methodName, long costedMilles, char returnVal) {
        Log.i(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String className, String methodName, long costedMilles, short returnVal) {
        Log.i(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String className, String methodName, long costedMilles, int returnVal) {
        Log.i(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String className, String methodName, long costedMilles, boolean returnVal) {
        Log.i(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String className, String methodName, long costedMilles, long returnVal) {
        Log.i(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String className, String methodName, long costedMilles, float returnVal) {
        Log.i(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String className, String methodName, long costedMilles, double returnVal) {
        Log.i(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void print(String className, String methodName, long costedMilles, Object returnVal) {
        if(returnVal != null && returnVal.getClass().isArray()){
            Log.i(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", arrayToString(returnVal)));
        } else {
            Log.i(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal));
        }
    }

    public static void printWithCustomLogger(String className, String methodName, long costedMilles, byte returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL.log(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void printWithCustomLogger(String className, String methodName, long costedMilles, char returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL.log(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void printWithCustomLogger(String className, String methodName, long costedMilles, short returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL.log(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void printWithCustomLogger(String className, String methodName, long costedMilles, int returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL.log(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void printWithCustomLogger(String className, String methodName, long costedMilles, boolean returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL.log(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void printWithCustomLogger(String className, String methodName, long costedMilles, long returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL.log(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void printWithCustomLogger(String className, String methodName, long costedMilles, float returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL.log(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void printWithCustomLogger(String className, String methodName, long costedMilles, double returnVal) {
        HunterLoggerHandler.CUSTOM_IMPL.log(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal + ""));
    }

    public static void printWithCustomLogger(String className, String methodName, long costedMilles, Object returnVal) {
        if(returnVal != null && returnVal.getClass().isArray()){
            HunterLoggerHandler.CUSTOM_IMPL.log(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", arrayToString(returnVal)));
        } else {
            HunterLoggerHandler.CUSTOM_IMPL.log(className, String.format(Constants.RETURN_PRINT_FORMAT, methodName, costedMilles + "", returnVal));
        }
    }

    private static String arrayToString(Object val) {
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

}
