package com.quinn.hunter.example;


import okhttp3.OkHttpClient;

/**
 * Created by HuaChao on 2016/7/4.
 */
public class AlphaTest {

    OkHttpClient okHttpClient;

    public AlphaTest() {
        eat();
        sleep(1, 10,  "gg");
    }

    private int a = 0;

    public void eat() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a = 5;
    }

    public void eat1() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void eat2() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void eat3() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @ParameterDebug
    private void sleep(int begin, double second , String hah) {
        a = 10;
    }

    private void run() {
        a = 15;
    }

}
