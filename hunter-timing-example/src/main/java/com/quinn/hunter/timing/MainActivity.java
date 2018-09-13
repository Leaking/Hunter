package com.quinn.hunter.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hunter.library.timing.BlockHandler;
import com.hunter.library.timing.IBlockManger;
import com.hunter.library.timing.impl.BlockManagerImpl;
import com.quinn.hunter.example.black.AlphaTest;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    IBlockManger iBlockManger = new BlockManagerImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AlphaTest();
        new BetaTest();
        BlockHandler.installBlockManager(iBlockManger);
        findViewById(R.id.dump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "dump");
                iBlockManger.dump();
            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
