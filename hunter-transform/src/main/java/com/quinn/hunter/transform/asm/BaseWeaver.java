package com.quinn.hunter.transform.asm;

import org.objectweb.asm.ClassVisitor;

/**
 * Default {@link IWeaver} implementation. Filters out generated R, R$* and
 * BuildConfig classes; subclasses override {@link #wrap(ClassVisitor)} to
 * append their own ClassVisitor to the chain that the AGP instrumentation
 * pipeline drives.
 */
public abstract class BaseWeaver implements IWeaver {

    @Override
    public boolean isWeavableClass(String fullyQualifiedClassName) {
        if (fullyQualifiedClassName == null) return false;
        return !fullyQualifiedClassName.contains("R$")
                && !fullyQualifiedClassName.endsWith(".R")
                && !fullyQualifiedClassName.endsWith(".BuildConfig");
    }

    @Override
    public ClassVisitor wrap(ClassVisitor next) {
        return next;
    }
}
