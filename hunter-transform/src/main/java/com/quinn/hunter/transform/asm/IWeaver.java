package com.quinn.hunter.transform.asm;

import org.objectweb.asm.ClassVisitor;

public interface IWeaver {

    boolean isWeavableClass(String fullyQualifiedClassName);

    ClassVisitor wrap(ClassVisitor next);
}
