package com.hunter.library.okhttp;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Dns;
import okhttp3.Interceptor;

/**
 * Created by quinn on 13/09/2018
 */
public class Target {

    final List<Interceptor> interceptors = new ArrayList<>();
    final List<Interceptor> networkInterceptors = new ArrayList<>();
    Dns dns = Dns.SYSTEM;

    public Target() {
        int a = 5;
        this.dns = OkHttpHooker.globalDns;
        this.interceptors.addAll(OkHttpHooker.globalInterceptors);
        this.networkInterceptors.addAll(OkHttpHooker.globalNetworkInterceptors);
    }

}
