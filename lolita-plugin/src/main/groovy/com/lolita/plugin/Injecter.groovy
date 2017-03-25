package com.lolita.plugin

import javassist.ClassPool
import javassist.CtClass
import javassist.CtConstructor
import javassist.CtField
import javassist.CtMember
import javassist.CtMethod
import javassist.Modifier
import org.gradle.internal.impldep.org.apache.http.util.TextUtils
import org.gradle.util.TextUtil

public class Injecter {

    private static ClassPool pool = ClassPool.getDefault()
    private static String injectStr = "System.out.println(\"Inserted code\" ); ";

    public static void injectDir(String path, String packageName) {
        println("Begin to inject packageName = " + packageName )
        pool.appendClassPath(path)
        pool.appendClassPath("/Users/susan_sfy/Library/Android/sdk/platforms/android-24/android.jar")
        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                //确保当前文件是class文件，并且不是系统自动生成的class文件
                if (filePath.endsWith(".class")
                        && !filePath.contains('R$')
                        && !filePath.contains('R.class')
                        && !filePath.contains("BuildConfig.class")) {
                    // 判断当前目录是否是在我们的应用包里面
                    int index = filePath.indexOf(packageName);
                    boolean isMyPackage = index != -1;
                    println("Begin to inject filePath " + filePath + " isMyPackage = " + isMyPackage)
                    if (isMyPackage) {
                        int end = filePath.length() - 6 // .class = 6
                        String className = filePath.substring(index, end).replace('\\', '.').replace('/', '.')
                        //开始修改class文件
                        CtClass c = pool.getCtClass(className)

                        if (c.isFrozen()) {
                            c.defrost()
                        }
                        pool.importPackage("android.util.Log");
                        pool.importPackage("android.os.Bundle");

                        CtClass addFieldClass = ClassPool.getDefault().get("java.util.HashMap");
                        CtField f = new CtField(addFieldClass, "timeMap", c);

                        c.addField(f);

                        CtMethod[] methods =  c.getDeclaredMethods();
                        for(CtMethod method: methods) {
                            boolean emptyMethod = method.isEmpty()
                            boolean isNativeMethod = Modifier.isNative(method.getModifiers());
                            println("method name = " + method + " emptyMethod " + emptyMethod + " isNativeMethod = " + isNativeMethod)
                            if (!emptyMethod && !isNativeMethod) {
                                method.insertAfter(injectStr)
                            }
                        }
                        c.writeFile(path)
                        c.detach()
                    }
                }
            }
        }
    }


}