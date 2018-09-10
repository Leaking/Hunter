package com.hunter.library.okhttp;

import okhttp3.EventListener;

/**
 * Created by Quinn on 09/09/2018.
 */
public class EventListenerFactoryHelper {

    public static EventListener.Factory globalFactory = null;

    public static void install(EventListener.Factory factory) {
        globalFactory = factory;
    }

}
