package com.quinn.hunter.debug;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.hunter.library.debug.HunterDebug;

public class MainActivity extends Activity {

    int aa = 5;

    @Override
    @HunterDebug(stepByStep = true)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void fun(int a) {
        Log.i("MainActivity", "a > " + a);
        int b;
        int c;
        int d = 4;
        c = a + d + aa;

    }

}
