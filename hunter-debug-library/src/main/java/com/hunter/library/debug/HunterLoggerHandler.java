package com.hunter.library.debug;

import android.util.Log;

/**
 * Created by quinn on 2019/1/8
 */
public class HunterLoggerHandler {

    protected void log(String tag, String msg) {

    }

    public static HunterLoggerHandler DEFAULT_IMPL = new HunterLoggerHandler() {

        @Override
        public void log(String tag, String msg) {
            Log.i(tag, msg);
        }

    };

    public static HunterLoggerHandler CUSTOM_IMPL = DEFAULT_IMPL;

    public static void installLogImpl(HunterLoggerHandler loggerHandler) {
        CUSTOM_IMPL = loggerHandler;
    }

}
