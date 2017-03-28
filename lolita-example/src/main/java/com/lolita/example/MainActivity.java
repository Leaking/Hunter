package com.lolita.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lolita.annotations.ParameterDebug;


@ParameterDebug
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
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
