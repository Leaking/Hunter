package com.quinn.hunter.plugin

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Handle
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

    public TimingClassWriter(int flags) {
        super(flags);
    }

    public TimingClassWriter(ClassReader classReader, int flags) {
        super(classReader, flags);
        println("TimingClassWriter");
    }

    @Override
    public byte[] toByteArray() {
        println( "toByteArray");
        return super.toByteArray();
    }

    @Override
    public int newConst(Object cst) {
        println( "newConst " + cst);
        return super.newConst(cst);
    }

    @Override
    public int newUTF8(String value) {
        println("newUTF8 " + value);
        return super.newUTF8(value);
    }

    @Override
    public int newClass(String value) {
        println( "newClass " + value);
        return super.newClass(value);
    }

    @Override
    public int newMethodType(String methodDesc) {
        println( "newMethodType " + methodDesc);
        return super.newMethodType(methodDesc);
    }


    @Override
    public int newHandle(int tag, String owner, String name, String desc, boolean itf) {
        return super.newHandle(tag, owner, name, desc, itf);
    }

    @Override
    public int newInvokeDynamic(String name, String desc, Handle bsm, Object... bsmArgs) {
        return super.newInvokeDynamic(name, desc, bsm, bsmArgs);
    }

    @Override
    public int newField(String owner, String name, String desc) {
        println( "newField " + owner + " name = " + name);
        return super.newField(owner, name, desc);
    }

    @Override
    public int newMethod(String owner, String name, String desc, boolean itf) {
        println( "newMethod " + owner + " name = " + name);
        return super.newMethod(owner, name, desc, itf);
    }

    @Override
    public int newNameType(String name, String desc) {
        println( "newNameType name " + name + " desc " + desc);
        return super.newNameType(name, desc);
    }

    @Override
    protected String getCommonSuperClass(String type1, String type2) {
        println( "getCommonSuperClass type1 " + type1 + " type2 " + type2);
        Class<?> c, d;
        try {
            c = Class.forName(type1.replace('/', '.'), false, urlClassLoader);
            d = Class.forName(type2.replace('/', '.'), false, urlClassLoader);
        } catch (Exception e) {
            println( "getCommonSuperClass fail ");
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
