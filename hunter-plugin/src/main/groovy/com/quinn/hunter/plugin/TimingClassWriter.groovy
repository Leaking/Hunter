package com.quinn.hunter.plugin

import org.objectweb.asm.ClassWriter
/**
 * Created by quinn on 30/08/2018
 */
public class TimingClassWriter extends ClassWriter {

    public static final String TAG = "TimingClassWriter";

    private URLClassLoader urlClassLoader;

    public TimingClassWriter(URLClassLoader urlClassLoader, int flags) {
        super(flags);
        this.urlClassLoader = urlClassLoader;
    }


    @Override
    protected String getCommonSuperClass(String type1, String type2) {
        Class<?> c, d;
        try {
            c = Class.forName(type1.replace('/', '.'), false, urlClassLoader);
            d = Class.forName(type2.replace('/', '.'), false, urlClassLoader);
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
        if (c.isAssignableFrom(d)) {
            return type1;
        }
        if (d.isAssignableFrom(c)) {
            return type2;
        }
        if (c.isInterface() || d.isInterface()) {
            return "java/lang/Object";
        } else {
            c = c.getSuperclass();
            while(!c.isAssignableFrom(d)) {
                c = c.getSuperclass();
            }
            return c.getName().replace('.', '/');
        }
    }
}
