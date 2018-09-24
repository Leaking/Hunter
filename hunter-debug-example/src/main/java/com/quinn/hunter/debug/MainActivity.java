package com.quinn.hunter.debug;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.hunter.library.debug.HunterDebug;
import com.hunter.library.debug.PrintUtils;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";
    int aa = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        is Boolean -> b.putBoolean(k, v)
//        is Byte -> b.putByte(k, v)
//        is Char -> b.putChar(k, v)
//        is Short -> b.putShort(k, v)
//        is Int -> b.putInt(k, v)
//        is Long -> b.putLong(k, v)
//        is Float -> b.putFloat(k, v)
//        is Double -> b.putDouble(k, v)
        boolean bool = true;
        byte byte_v = 1;
        char char_v = 2;
        short short_v = 3;
        int int_v = 4;
        long long_v = 5;
        float float_v = 6;
        double double_v = 7;
        String string_v = "string";
        int[] int_arr = new int[]{1,2,3};
        fun(bool, byte_v, char_v, short_v, int_v, long_v, float_v, double_v, string_v, int_arr);
    }

    private List<String> paramNames = new ArrayList<>();

    private void fun(boolean bool, byte byte_v, char char_v, short short_v, int int_v, long long_v, float float_v, double double_v, String string_v, int[] arr){
        PrintUtils printUtils = new PrintUtils("tag");
        printUtils.append("aa", byte_v);
        printUtils.append("aa", char_v);
        printUtils.append("aa", short_v);
        printUtils.append("aa", int_v);
        printUtils.append("aa", long_v);
        printUtils.append("aa", float_v);
        printUtils.append("aa", double_v);
        printUtils.append("aa", bool);
        printUtils.append("aa", string_v);
        printUtils.append("aa", arr);
        printUtils.print();
    }

}
