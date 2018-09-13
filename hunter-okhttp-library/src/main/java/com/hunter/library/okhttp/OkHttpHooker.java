package com.hunter.library.okhttp;

import okhttp3.Call;
import okhttp3.Dns;
import okhttp3.EventListener;

/**
 * Created by Quinn on 09/09/2018.
 */
public class OkHttpHooker {

    public static EventListener.Factory globalEventFactory = new EventListener.Factory() {
        public EventListener create(Call call) {
            return new EventListener() {};
        }
    };;

    public static Dns globalDns = Dns.SYSTEM;

    public static void installEventListenerFactory(EventListener.Factory factory) {
        globalEventFactory = factory;
    }

    public static void installDns(Dns dns) {
        globalDns = dns;
    }

}
