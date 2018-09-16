package com.hunter.library.debug;

import android.util.Log;

public class LineNumberLog {

    public static int log(String tag, String msg, String linenumber) {
        return Log.i(tag, "[" + linenumber + "]" + msg);
    }

    public static int log(String tag, String msg) {
        return Log.i(tag, msg);
    }
}
