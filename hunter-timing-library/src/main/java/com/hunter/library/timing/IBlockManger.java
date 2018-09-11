package com.hunter.library.timing;

import android.util.Log;

/**
 * Created by quinn on 11/09/2018
 */
public interface IBlockManger {


    IBlockManger DEFAULT = new IBlockManger() {

        private static final String TAG = "Default-IBlockManger";

        private static final int BLOCK_THRESHOLD = 100;

        @Override
        public void timingMethod(String method, int cost) {
            if(cost >= BLOCK_THRESHOLD) {
                Log.i(TAG, method + " costed " + cost);
            }
        }

        @Override
        public void dump() {

        }

    };

    public void timingMethod(String method, int cost);

    public void dump();


}
