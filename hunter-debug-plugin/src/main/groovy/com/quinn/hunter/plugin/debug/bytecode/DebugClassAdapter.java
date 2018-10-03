package com.quinn.hunter.plugin.debug.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Quinn on 16/09/2018.
 */
public final class DebugClassAdapter extends ClassVisitor{

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugClassAdapter.class);
    private Map<String, List<Parameter>> methodParametersMap;
    private DebugMethodAdapter debugMethodAdapter;
    private String className;

    DebugClassAdapter(final ClassVisitor cv, final Map<String, List<Parameter>> methodParametersMap) {
        super(Opcodes.ASM5, cv);
        this.methodParametersMap = methodParametersMap;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        String methodUniqueKey = name + desc;
        debugMethodAdapter = new DebugMethodAdapter(className, methodParametersMap.get(methodUniqueKey), name, access, desc, mv);
        return mv == null ? null : debugMethodAdapter;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();

    }

}