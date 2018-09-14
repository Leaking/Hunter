package com.quinn.hunter.timing;

import android.util.Log;

/**
 * Created by quinn on 14/09/2018
 */
public class NativeService {

    private static final String TAG = "NativeService";

    private final static NativeService instance = new NativeService();

    public static NativeService getInstance() {
        return instance;
    }

    public NativeService() {

    }

    public void loadSoFile() {
        try {
            Log.i(TAG, "Loading xxxx.so");
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
