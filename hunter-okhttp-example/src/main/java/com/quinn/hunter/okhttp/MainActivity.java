package com.quinn.hunter.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hunter.library.okhttp.DefaultEventListener;
import com.hunter.library.okhttp.OkHttpHooker;

import java.io.IOException;

import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_main);
        EventListener.Factory factory = DefaultEventListener.FACTORY;
//        OkHttpHooker.install(factory);
        run("http://square.github.io/okhttp/");
        run("https://github.com/");
    }

    void run(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Response response = okHttpClient.newCall(request).execute();
                    String result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
