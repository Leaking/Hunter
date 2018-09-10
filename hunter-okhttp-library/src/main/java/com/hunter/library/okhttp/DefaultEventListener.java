package com.hunter.library.okhttp;

import android.util.Log;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.EventListener;

/**
 * Created by Quinn on 09/09/2018.
 */
public class DefaultEventListener extends EventListener{

    public static final String TAG = "DefaultEventListener";

    private String url;

    public static EventListener.Factory getFactory() {
        return new EventListener.Factory() {
            public EventListener create(Call call) {
                return new DefaultEventListener(call);
            }
        };
    }


    public DefaultEventListener(Call call) {
        this.url = call.request().url().toString();
    }

    @Override
    public void callStart(Call call) {
        super.callStart(call);
        Log.i(TAG, "callStart");
    }


    @Override
    public void callEnd(Call call) {
        super.callEnd(call);
        Log.i(TAG, "callEnd");
    }

    @Override
    public void callFailed(Call call, IOException ioe) {
        super.callFailed(call, ioe);
        Log.i(TAG, "callFailed");
    }
}
