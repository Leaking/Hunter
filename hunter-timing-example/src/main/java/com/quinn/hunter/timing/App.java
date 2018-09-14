package com.quinn.hunter.timing;

import android.app.Application;

import com.hunter.library.timing.BlockManager;
import com.hunter.library.timing.IBlockHandler;
import com.hunter.library.timing.impl.RankingBlockHandler;

/**
 * Created by quinn on 14/09/2018
 */
public class App extends Application {

    private IBlockHandler customBlockManager = new RankingBlockHandler();

    @Override
    public void onCreate() {
        super.onCreate();
        BlockManager.installBlockManager(customBlockManager);
    }

    public IBlockHandler getCustomBlockManager(){
        return customBlockManager;
    }

}
