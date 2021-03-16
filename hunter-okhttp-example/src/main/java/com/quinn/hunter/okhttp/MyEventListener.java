package com.quinn.hunter.okhttp;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.atomic.AtomicLong;

import okhttp3.Call;
import okhttp3.EventListener;

/**
 * Created by Owenli on 15/03/2021.
 */
public class MyEventListener extends EventListener{

    private static final String TAG = "MyEventListener";

    public static final Factory FACTORY =  new Factory() {

        AtomicLong nextCallId = new AtomicLong(1L);

        @Override
        public EventListener create(Call call) {
            long callId = nextCallId.getAndIncrement();
            return new MyEventListener(callId);
        }

    };

    private long callId;
    private long callStartNanos = 0L;
    private boolean isNewConnection = false;

    public MyEventListener(long callId) {
        this.callId = callId;
    }

    @Override
    public void callStart(Call call) {
        callStartNanos = SystemClock.elapsedRealtime();
    }

    @Override
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        isNewConnection = true;
    }

    @Override
    public void callEnd(Call call) {
        printResult(true, call);
    }

    @Override
    public void callFailed(Call call, IOException ioe) {
        printResult(false, call);
    }

    @SuppressLint("DefaultLocale")
    private void printResult(boolean success, Call call) {
        float elapsed = (float) ((SystemClock.elapsedRealtime() - callStartNanos) / 1000.0);
        String from = isNewConnection ? "newest connection" : "pooled connection";
        String url = call.request().url().toString();
        String result = String.format("%04d %s Call From %s costs %.3f, url %s", callId, success ? "Success" : "Fail", from, elapsed, url);
        Log.i(TAG, result);
    }

}
