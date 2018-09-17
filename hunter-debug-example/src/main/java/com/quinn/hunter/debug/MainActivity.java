package com.quinn.hunter.debug;

import android.app.Activity;
import android.os.Bundle;

import com.hunter.library.debug.HunterDebug;

public class MainActivity extends Activity {

    @Override
    @HunterDebug
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
