package com.hunter.library.okhttp;

import okhttp3.EventListener;

/**
 * Created by Quinn on 09/09/2018.
 */
public class OkHttpHooker {

    public static EventListener.Factory globalEventFactory = DefaultEventListener.FACTORY;

    public static void install(EventListener.Factory factory) {
        globalEventFactory = factory;
    }

}
