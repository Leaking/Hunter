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
        public void timingMethod(String method, long cost) {
            if(cost >= 100) {
                Log.i(TAG, method + " costed " + cost);
            }
        }

    };

    public void timingMethod(String method, long cost);


}
