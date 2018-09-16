package com.quinn.hunter.debug;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hunter.library.debug.LineNumberLog;

/**
 * Created by Quinn on 24/03/2017.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    int a = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");
    }


}
