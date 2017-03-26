package com.lolita.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lolita.annotations.ParameterDebug;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    @ParameterDebug
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "<===================");
        new Test();
        new Test2();
        Log.e(TAG, "===================>");
    }
}
