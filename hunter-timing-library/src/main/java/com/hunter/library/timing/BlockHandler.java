package com.hunter.library.timing;

import android.os.Looper;

/**
 * Asm will insert bytecode of {@link BlockHandler#timingMethod(String, long)} to end of some method.
 */
public class BlockHandler {

    public static final String TAG = "BlockHandler";

    private static IBlockManger iBlockManger = IBlockManger.DEFAULT;

    public static void installBlockManager(IBlockManger custom) {
        BlockHandler.iBlockManger = custom;
    }

    public static void timingMethod(String method, long cost) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            iBlockManger.timingMethod(method, (int)cost);
        }
    }

}
