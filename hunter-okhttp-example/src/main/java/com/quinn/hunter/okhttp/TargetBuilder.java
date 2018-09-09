package com.quinn.hunter.okhttp;

import com.hunter.library.okhttp.EventListenerFactoryHelper;

import okhttp3.EventListener;

public class TargetBuilder {

    EventListener.Factory factory;

    public TargetBuilder(){
        this.factory = EventListenerFactoryHelper.globalFactory;
    }

}
