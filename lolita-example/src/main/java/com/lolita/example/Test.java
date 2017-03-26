package com.lolita.example;


import com.lolita.annotations.ParameterDebug;
import com.lolita.annotations.TimingDebug;

/**
 * Created by HuaChao on 2016/7/4.
 */
public class Test {

    public Test() {
        eat();
        sleep(1, 10,  "gg");
    }

    private int a = 0;

    @TimingDebug
    public void eat() {
        a = 5;
    }

    @ParameterDebug
    private void sleep(int begin, double second , String hah) {
        a = 10;
    }

}
