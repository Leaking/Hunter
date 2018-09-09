package com.hunter.library.okhttp;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class DefaultEventListener extends EventListener{

    public static EventListener.Factory getFactory() {
        return new EventListener.Factory() {
            public EventListener create(Call call) {
                return new DefaultEventListener(call);
            }
        };
    }

    public static final String TAG = "DefaultEventListener";

    private String url;
    private long start;

    public DefaultEventListener(Call call) {
        this.url = call.request().url().toString();
    }

    @Override
    public void callStart(Call call) {
        super.callStart(call);
        start = System.currentTimeMillis();
    }

    @Override
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        super.connectStart(call, inetSocketAddress, proxy);
    }

    @Override
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol);
    }

    @Override
    public void callEnd(Call call) {
        super.callEnd(call);
        long cost = System.currentTimeMillis() - start;
        Log.i(TAG, url + " call success cost " + cost);
    }

    @Override
    public void callFailed(Call call, IOException ioe) {
        super.callFailed(call, ioe);
        long cost = System.currentTimeMillis() - start;
        Log.i(TAG, url + " call fail cost " + cost);
    }
}
