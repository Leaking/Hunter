package com.hunter.library.timing;

import android.util.Log;

/**
 * Created by quinn on 11/09/2018
 */
public interface IBlockHandler {

    IBlockHandler DEFAULT = new IBlockHandler() {

        private static final String TAG = "Default-IBlockHandler";

        private static final int BLOCK_THRESHOLD = 100;

        @Override
        public void timingMethod(String method, int cost) {
            if(cost >= BLOCK_THRESHOLD) {
                Log.i(TAG, method + " costed " + cost);
            }
        }

        @Override
        public String dump() {
            return "";
        }

        @Override
        public void clear() {

        }
    };

    public void timingMethod(String method, int cost);

    public String dump();

    public void clear();


}
