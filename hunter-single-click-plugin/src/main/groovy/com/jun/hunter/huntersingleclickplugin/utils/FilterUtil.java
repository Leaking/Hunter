package com.jun.hunter.huntersingleclickplugin.utils;

public class FilterUtil {

    /**
     * 是否实现android/view/View$OnClickListener接口
     * @param interfaces 类的实现接口
     * @return true 匹配
     */

    public static boolean isMatchingClass(String[] interfaces) {
        return isMatchingInterfaces(interfaces, "android/view/View$OnClickListener");
    }


    /**
     * 接口名是否匹配
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
     * 是否匹配View的onClick(View v)方法
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
