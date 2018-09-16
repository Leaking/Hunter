package com.hunter.library.linelog;

import android.util.Log;

public class LineNumberLog {

    public static int i(String tag, String msg, String linenumber) {
        return Log.i(tag + "[" + linenumber + "]", msg);
    }

    public static int i(String tag, String msg, Throwable e, String linenumber) {
        return Log.i(tag + "[" + linenumber + "]", msg, e);
    }

    public static int d(String tag, String msg, String linenumber) {
        return Log.d(tag + "[" + linenumber + "]", msg);
    }

    public static int d(String tag, String msg, Throwable e, String linenumber) {
        return Log.d(tag + "[" + linenumber + "]", msg, e);
    }

    public static int v(String tag, String msg, String linenumber) {
        return Log.v(tag + "[" + linenumber + "]", msg);
    }

    public static int v(String tag, String msg, Throwable e, String linenumber) {
        return Log.v(tag + "[" + linenumber + "]", msg, e);
    }

    public static int e(String tag, String msg, String linenumber) {
        return Log.e(tag + "[" + linenumber + "]", msg);
    }

    public static int e(String tag, String msg, Throwable e, String linenumber) {
        return Log.e(tag + "[" + linenumber + "]", msg, e);
    }

    public static int w(String tag, String msg, String linenumber) {
        return Log.w(tag + "[" + linenumber + "]", msg);
    }

    public static int w(String tag, String msg, Throwable e, String linenumber) {
        return Log.w(tag + "[" + linenumber + "]", msg, e);
    }

    public static int w(String tag, Throwable e, String linenumber) {
        return Log.w(tag + "[" + linenumber + "]", e);
    }

}
