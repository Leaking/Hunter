package com.hunter.library.okhttp;

import okhttp3.EventListener;

public class EventListenerFactoryHelper {

    public static EventListener.Factory globalFactory = null;

    public static void install(EventListener.Factory factory) {
        globalFactory = factory;
    }

    public static void setEventListenerFactory(EventListener.Factory factory){
        if(globalFactory != null) {
            factory = globalFactory;
        }
    }

}
