package com.jun.hunter.huntersingleclickexample;

import android.app.Application;

import com.jun.hunter.huntersingleclicklibrary.ClickUtils;


public class SingleClickExampleApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ClickUtils.setFrozenTimeMillis(3000);
    }
}
