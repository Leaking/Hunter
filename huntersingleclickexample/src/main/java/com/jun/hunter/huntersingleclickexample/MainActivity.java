package com.jun.hunter.huntersingleclickexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jun.hunter.huntersingleclicklibrary.NoSingleClick;


public class MainActivity extends Activity {

    public static final String TAG = "singleClickExample";

    private Button singleClick;
    private Button fastClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singleClick = findViewById(R.id.single_click);
        fastClick = findViewById(R.id.single_click);
        initListener();
    }

    private void initListener() {
        singleClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "singleClick: "+v.getId());
            }
        });

        fastClick.setOnClickListener(new View.OnClickListener() {
            @Override
            @NoSingleClick
            public void onClick(View v) {
                Log.i(TAG, "fastClick: "+v.getId());
            }
        });

    }
}
