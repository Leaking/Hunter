package com.quinn.hunter.debug.test;

import android.app.Activity;
import android.content.Intent;


import com.hunter.library.debug.HunterDebugClass;
import com.hunter.library.debug.HunterDebugImpl;
import com.hunter.library.debug.HunterDebugSkip;



@HunterDebugClass
public class ClassTest2 {


    @HunterDebugSkip
    public String test1(int age){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "fail";
        }

        return "success";
    }

//    private void test2(String name,int age){
//        throw new RuntimeException("not impl");
//    }

    @HunterDebugImpl
    protected void test3(Intent intent,String age){

    }

    @HunterDebugImpl
    public static void test4(Activity activity,String ...args){

    }
}
