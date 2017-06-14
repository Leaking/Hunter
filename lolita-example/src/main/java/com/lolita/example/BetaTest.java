package com.lolita.example;

import android.util.Log;

import com.lolita.annotations.ParameterDebug;
import com.lolita.annotations.TimingDebug;


/**
 * Created by Quinn on 24/03/2017.
 */

@ParameterDebug
@TimingDebug
public class BetaTest extends ParentTest{

    public static final String TAG = "BetaTest";

    public BetaTest() {
        this(5);
        swing(0,9);
        claw();
    }

    public BetaTest(int god){
        Log.i(TAG, "god constructor");
    }

    @Override
    public void swing(int a, int b) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void claw() {
        int a = 5;
        Log.i(TAG, "claw");
    }
}
