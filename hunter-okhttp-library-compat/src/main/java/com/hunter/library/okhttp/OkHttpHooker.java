package com.hunter.library.okhttp;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Dns;
import okhttp3.Interceptor;

/**
 * Created by Quinn on 14/12/2018.
 *
 * Compat OkHttpHooker for Okhttp below 3.11
 *
 * EventListener is introduced from 3.7 and works from 3.11
 *
 */
public class OkHttpHooker {

    public static Dns globalDns = Dns.SYSTEM;

    public static List<Interceptor> globalInterceptors = new ArrayList<>();

    public static List<Interceptor> globalNetworkInterceptors = new ArrayList<>();

    public static void installDns(Dns dns) {
        globalDns = dns;
    }

    public static void installInterceptor(Interceptor interceptor) {
        if(interceptor != null)
            globalInterceptors.add(interceptor);
    }

    public static void installNetworkInterceptors(Interceptor networkInterceptor) {
        if(networkInterceptor != null)
            globalNetworkInterceptors.add(networkInterceptor);
    }


}
