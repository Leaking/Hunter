package com.quinn.hunter.timing.black;


import android.util.Log;

/**
 * Created by Quinn on 24/03/2017.
 */
public class NetWorkManager {

    private static final String TAG = "NetWorkManager";
    private final static NetWorkManager instance = new NetWorkManager();

    public static NetWorkManager getInstance() {
        return instance;
    }

    public NetWorkManager(){

    }

    public void uploadHugeFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(TAG, "Uploading Huge File");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
