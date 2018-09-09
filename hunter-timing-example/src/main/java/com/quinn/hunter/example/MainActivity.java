package com.quinn.hunter.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hunter.library.BlockLogger;

import java.util.logging.LogManager;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AlphaTest();
        new BetaTest();
    }


    @Override
    protected void onResume() {
        long start = System.currentTimeMillis();
        super.onResume();
        long ellpase = System.currentTimeMillis() - start;
        BlockLogger.log("onResume", ellpase);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
