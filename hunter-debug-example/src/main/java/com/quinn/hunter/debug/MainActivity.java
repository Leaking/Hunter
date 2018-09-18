package com.quinn.hunter.debug;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.hunter.library.debug.HunterDebug;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";
    int aa = 5;

    @Override
    @HunterDebug(stepByStep = true)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fun(23, "hahha", new int[]{1,2,3});
    }

    private List<String> paramNames = new ArrayList<>();

    public void fun(int a, String str, int[] array) {
        printarg("tag", paramNames, a, str, array);
        int b;
        int c;
        int d = 4;
        c = a + d + aa;

    }

    public void printarg(String tag, List<String> paramNames, Object...objects) {
        for(Object item : objects) {
            Log.i(TAG, item.getClass().getTypeName() + "item " + item.toString());
        }

    }

}
