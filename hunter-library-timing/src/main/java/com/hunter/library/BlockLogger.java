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

    //算法设计（在N时记录开始时间，Y时用最新时间减去开始时间，得出时间差）
    //用一个双向树结构存储数据，
    //进入一个方法，首先检查，如果当前
    // A (N)
      // B (N)
        // C (N)  -> C (Y)
        // D (N)
          //E (N) -> E(Y)
          //F (N) -> F(Y)
        // D (Y)
      // B (Y)
    // A (Y)


}
