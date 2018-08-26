package com.hunter.library;

import android.os.Looper;
import android.util.Log;

public class BlockLogger {

    public static final String TAG = "BlockLogger";

    public static void log(String method, long miss) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.i(TAG, method + " " + miss);
        }
    }

}
