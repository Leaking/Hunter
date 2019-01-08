package com.quinn.hunter.linelog;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;


/**
 * Created by Quinn on 15/09/2018.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Throwable throwable = new Throwable();

        Log.i(TAG, "onCreate");
        Log.i(TAG, "onCreate", throwable);


        Log.d(TAG, "onCreate");
        Log.d(TAG, "onCreate", throwable);

        Log.v(TAG, "onCreate");
        Log.v(TAG, "onCreate", throwable);

        Log.e(TAG, "onCreate");
        Log.e(TAG, "onCreate", throwable);

        Log.w(TAG, "onCreate");
        Log.w(TAG, "onCreate", throwable);
        Log.w(TAG, throwable);



    }


}
