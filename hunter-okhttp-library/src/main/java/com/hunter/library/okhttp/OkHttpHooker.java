package com.hunter.library.okhttp;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Dns;
import okhttp3.EventListener;
import okhttp3.Interceptor;

/**
 * Created by Quinn on 09/09/2018.
 */
public class OkHttpHooker {

    private static EventListener.Factory globalEventFactory = new EventListener.Factory() {
        public EventListener create(Call call) {
            return EventListener.NONE;
        }
    };

    public static Dns globalDns = Dns.SYSTEM;

    public static List<Interceptor> globalInterceptors = new ArrayList<>();

    public static List<Interceptor> globalNetworkInterceptors = new ArrayList<>();

    public static EventListener.Factory getGlobalEventFactory(EventListener.Factory factory) {
        return new GlobalEventFactory(globalEventFactory, factory);
    }

    public static void installEventListenerFactory(EventListener.Factory factory) {
        globalEventFactory = factory;
    }

    public static void installDns(Dns dns) {
        globalDns = dns;
    }

    public static void installInterceptor(Interceptor interceptor) {
        if (interceptor != null)
            globalInterceptors.add(interceptor);
    }

    public static void installNetworkInterceptors(Interceptor networkInterceptor) {
        if (networkInterceptor != null)
            globalNetworkInterceptors.add(networkInterceptor);
    }


}
