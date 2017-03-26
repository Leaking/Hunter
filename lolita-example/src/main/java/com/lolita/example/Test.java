package com.lolita.example;



/**
 * Created by HuaChao on 2016/7/4.
 */
public class Test {

//    @com.lolita.annotations.MethodDebug
    public Test() {
        eat();
        sleep(1, 10,  "gg");
    }

    private int a = 0;

    @com.lolita.annotations.TimingDebug
    public void eat() {
        a = 5;
    }

    @com.lolita.annotations.ArgumentDebug
    private void sleep(int begin, double second , String hah) {
        a = 10;
    }

}
