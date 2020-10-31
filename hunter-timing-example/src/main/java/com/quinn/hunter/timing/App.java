package com.quinn.hunter.timing;

import android.app.Application;

import com.hunter.library.timing.BlockManager;
import com.hunter.library.timing.IBlockHandler;
import com.hunter.library.timing.impl.StacktraceBlockHandler;

/**
 * Created by quinn on 14/09/2018
 */
public class App extends Application {

    private IBlockHandler customBlockManager = new StacktraceBlockHandler(50);

    @Override
    public void onCreate() {
        super.onCreate();
        BlockManager.installBlockManager(customBlockManager);
    }

    public IBlockHandler getCustomBlockManager(){
        return customBlockManager;
    }

}
