package com.hunter.library.timing;

import android.os.Looper;

/**
 * Asm will insert bytecode of {@link BlockManager#timingMethod(String, long)} to end of some method.
 */
public class BlockManager {

    private static IBlockHandler iBlockHandler = IBlockHandler.DEFAULT;

    public static void installBlockManager(IBlockHandler custom) {
        BlockManager.iBlockHandler = custom;
    }

    public static void timingMethod(String method, long cost) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            iBlockHandler.timingMethod(method, (int)cost);
        }
    }

}
