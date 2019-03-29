package com.jun.hunter.huntersingleclickplugin.utils;

/**
 * Created by Carl on 2018/6/12.
 */

public class FilterUtil {


    /**
     * 类是否满足匹配条件，满足的才会允许修改其中的方法
     * 实现android/view/View$OnClickListener接口的类
     * @param className  类名
     * @param interfaces 类的实现接口
     * @return true 匹配
     */

    public static boolean isMatchingClass(String className, String[] interfaces) {

        //剔除掉以android开头的类，即系统类，以避免出现不可预测的bug
        if (className.startsWith("android")) {
            return false;
        }
        return isMatchingInterfaces(interfaces, "android/view/View$OnClickListener");
    }


    /**
     * 接口名是否匹配
     *
     * @param interfaces    类的实现接口
     * @param interfaceName 需要匹配的接口名
     */
    private static boolean isMatchingInterfaces(String[] interfaces, String interfaceName) {
        boolean isMatch = false;
        // 是否满足实现的接口
        for (String anInterface : interfaces) {
            if (anInterface.equals(interfaceName)) {
                isMatch = true;
            }
        }
        return isMatch;
    }


    /**
     * 方法是否匹配到，根据方法名和参数的描述符来确定一个方法是否需要修改的初步条件，
     * View的onClick(View v)方法
     *
     * @param name 方法名
     * @param desc 参数的方法的描述符
     * @return true 匹配
     */
    public static boolean isMatchingMethod(String name, String desc) {
        if ((name.equals("onClick") && desc.equals("(Landroid/view/View;)V"))) {
            return true;
        }
        return false;
    }
}
