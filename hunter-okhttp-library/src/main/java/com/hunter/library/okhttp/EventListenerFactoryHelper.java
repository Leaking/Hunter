package com.hunter.library.okhttp;

import android.util.Log;

import okhttp3.EventListener;
import okhttp3.OkHttpClient;

public class EventListenerFactoryHelper {

    public static final String TAG = "EventListenFactoHelper";
    public static EventListener.Factory globalFactory = null;

    public static void install(EventListener.Factory factory) {
        globalFactory = factory;
    }

}
