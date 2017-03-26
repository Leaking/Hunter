package com.lolita.plugin

import com.lolita.annotations.ArgumentDebug
import com.lolita.annotations.TimingDebug
import javassist.ClassPool
import javassist.CtClass
import javassist.CtConstructor
import javassist.CtField
import javassist.CtMember
import javassist.CtMethod
import javassist.Modifier
import javassist.bytecode.CodeAttribute
import javassist.bytecode.LocalVariableAttribute
import javassist.bytecode.MethodInfo
import javassist.compiler.MemberResolver
import org.gradle.internal.impldep.org.apache.http.util.TextUtils
import org.gradle.util.TextUtil

public class Injecter {

    private static ClassPool pool = ClassPool.getDefault()
    private static String ARG_METHOD = "System.out.println(\"Argument code\" ); ";
    private static String TIME_METHOD = "System.out.println(\"Time code\" ); ";

    public static void injectDir(String androidClassPath, String path, String packageName) {
        println("Begin to inject packageName = " + packageName)
        println("Begin to inject androidClassPath = " + androidClassPath)
        pool.appendClassPath(path)
        pool.appendClassPath(androidClassPath)
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

                        CtMethod[] methods = c.getDeclaredMethods();
                        for (CtMethod method : methods) {
                            boolean emptyMethod = method.isEmpty()
                            boolean isNativeMethod = Modifier.isNative(method.getModifiers());
                            println("method name = " + method + " emptyMethod " + emptyMethod + " isNativeMethod = " + isNativeMethod)
                            if (!emptyMethod && !isNativeMethod) {
                                if (method.hasAnnotation(ArgumentDebug.class)) {
                                    String[] params = getMethodParameterNames(method)
                                    StringBuilder parameterDetail = new StringBuilder("\"");
                                    parameterDetail.append(method.getName()).append("[");
                                    for (int i = 0; i < params.length; i++) {
                                        if(i != 0) {
                                            parameterDetail.append("\"");
                                            parameterDetail.append(", ")
                                        }
                                        parameterDetail.append(params[i] + " = \" + "  + "\$" + (i + 1));
                                        parameterDetail.append(" + ");
                                    }
                                    parameterDetail.append("\"]\"");


                                    println "params = " + Arrays.toString(params)
                                    ARG_METHOD = "Log.i(\"" + c.simpleName + "\"," +
                                            parameterDetail.toString() +
                                            ");"
                                    print "ARG_METHOD = " + ARG_METHOD
                                    method.insertAfter(ARG_METHOD)
                                }
                                if (method.hasAnnotation(TimingDebug.class)) {
                                    method.insertAfter(TIME_METHOD)
                                }
                            }
                        }
                        c.writeFile(path)
                        c.detach()
                    }
                }
            }
        }
    }

    public static String[] getMethodParameterNames(CtMethod method) throws Exception {
        CtClass cc = method.getDeclaringClass();
        CtClass[] parameterCtClasses = new CtClass[method.getParameterTypes().length];
        for (int i = 0; i < parameterCtClasses.length; i++)
            parameterCtClasses[i] = method.getParameterTypes()[i];

        String[] parameterNames = new String[parameterCtClasses.length];
        CtMethod cm = cc.getDeclaredMethod(method.getName(), parameterCtClasses);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);

        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < parameterNames.length; i++)
            parameterNames[i] = attr.variableName(i + pos);
        return parameterNames;
    }


}