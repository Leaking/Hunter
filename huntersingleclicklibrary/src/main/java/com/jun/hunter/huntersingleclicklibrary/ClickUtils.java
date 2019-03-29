

package com.jun.hunter.huntersingleclicklibrary;

import android.view.View;



public final class ClickUtils {

    /**
     * 最近一次点击的时间
     */
    private static long sLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private static int sLastClickViewId;

    /**
     * Don't let anyone instantiate this class.
     */
    private ClickUtils() {
        throw new UnsupportedOperationException("Do not need instantiate!");
    }

    /**
     * 是否是快速点击
     *
     * @param v 点击的控件
     * @return true:是，false:不是
     */
    public static boolean isFastDoubleClick(View v) {
        return isFastDoubleClick(v, 1000);
    }

    /**
     * 是否是快速点击
     *
     * @param v              点击的控件
     * @param intervalMillis 时间间期（毫秒）
     * @return true:是，false:不是
     */
    public static boolean isFastDoubleClick(View v, long intervalMillis) {
        long time = System.currentTimeMillis();
        int viewId = v.getId();
        long timeD = time - sLastClickTime;
        if (0 < timeD && timeD < intervalMillis && viewId == sLastClickViewId) {
            return true;
        } else {
            sLastClickTime = time;
            sLastClickViewId = viewId;
            return false;
        }
    }



    public void change(View view) {
        boolean fastDoubleClick = ClickUtils.isFastDoubleClick(view);
        if (fastDoubleClick){
            return;
        }
    }

    public void change1(View view) {
    }
}
