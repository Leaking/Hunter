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
        MainPresenter mainPresenter = new MainPresenter();
        mainPresenter.load("1212");
        mainPresenter.loadMore("12123232");
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
        fun(bool, byte_v, char_v, short_v, int_v, long_v, float_v, double_v, string_v, int_arr, savedInstanceState);
    }

    private List<String> paramNames = new ArrayList<>();

    @HunterDebug
    private void fun(boolean bool_v, byte byte_v, char char_v, short short_v, int int_v, long long_v, float float_v, double double_v, String string_v, int[] arr, Bundle savedInstanceState){
        PrintUtils printUtils = new PrintUtils("tag");
        printUtils.append("bool_v", bool_v);
        printUtils.append("byte_v", byte_v);
        printUtils.append("char_v", char_v);
        printUtils.append("short_v", short_v);
        printUtils.append("int_v", int_v);
        printUtils.append("long_v", long_v);
        printUtils.append("float_v", float_v);
        printUtils.append("double_v", double_v);
        printUtils.append("string_v", string_v);
        printUtils.append("array", arr);
        printUtils.append("bundle", savedInstanceState);
        printUtils.print();
    }

}
