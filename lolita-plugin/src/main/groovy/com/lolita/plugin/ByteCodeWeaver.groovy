package com.lolita.plugin

import com.lolita.annotations.ParameterDebug
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

public class ByteCodeWeaver {

    private static ClassPool pool = ClassPool.getDefault()

    private String androidClassPath;

    public ByteCodeWeaver(String androidClassPath) {
        this.androidClassPath = androidClassPath;
    }

    public void weave(String path) {
        println("Begin to weave androidClassPath = " + androidClassPath)
        println("Begin to weave path = " + path)
        pool.appendClassPath(path)
        pool.appendClassPath(androidClassPath)
        pool.importPackage("android.util.Log");
        File dir = new File(path)
        int indexOfPackage = path.length() + 1;
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                if (isWeavableClass(filePath)) {
                    println("Begin to inject filePath " + filePath)
                    int end = filePath.length() - 6 // .class = 6
                    String className = filePath.substring(indexOfPackage, end).replace(File.separator, '.')
                    CtClass clazz = pool.getCtClass(className)
                    if (clazz.isFrozen()) {
                        clazz.defrost()
                    }
                    CtMethod[] methods = clazz.getDeclaredMethods();
                    for (CtMethod method : methods) {
                        boolean emptyMethod = method.isEmpty()
                        boolean isNativeMethod = Modifier.isNative(method.getModifiers());
                        println("method name = " + method + " emptyMethod " + emptyMethod + " isNativeMethod = " + isNativeMethod)
                        if (!emptyMethod && !isNativeMethod) {
                            if (method.hasAnnotation(ParameterDebug.class)) {
                                weaveParameterDebugMethod(clazz, method)
                            }
                            if (method.hasAnnotation(TimingDebug.class)) {
                                weaveTimingDebugMethod(method)
                            }
                        }
                    }
                    clazz.writeFile(path)
                    clazz.detach()
                }
            }
        }
    }



    /**
     * Get Parmeter Names Of Certain Method
     * @param method
     * @return
     * @throws Exception
     */
    public String[] getMethodParameterNames(CtMethod method) throws Exception {
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

    public boolean isWeavableClass(String filePath){
        return filePath.endsWith(".class") && !filePath.contains('R$') && !filePath.contains('R.class') && !filePath.contains("BuildConfig.class");
    }

    public void weaveParameterDebugMethod(CtClass clazz, CtMember method) {
        String[] params = getMethodParameterNames(method)
        StringBuilder parameterDetail = new StringBuilder("\"");
        parameterDetail.append(method.getName()).append("[");
        for (int i = 0; i < params.length; i++) {
            if (i != 0) {
                parameterDetail.append("\"");
                parameterDetail.append(", ")
            }
            parameterDetail.append(params[i] + " = \" + " + "\$" + (i + 1));
            parameterDetail.append(" + ");
        }
        parameterDetail.append("\"]\"");
        println "params = " + Arrays.toString(params)
        String newCodes = "Log.i(\"" + clazz.simpleName + "\"," +
                parameterDetail.toString() +
                ");"
        print "ARG_METHOD = " + newCodes
        method.insertAfter(newCodes)
    }

    public void weaveTimingDebugMethod(CtMethod method) {
        String timeingCodes = "System.out.println(\"Time code\" ); ";
        method.insertAfter(timeingCodes)
    }


}