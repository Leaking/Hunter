package com.quinn.hunter.plugin.debug.bytecode;

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

    private Map<String, List<String>> methodParametersMap;
    private DebugMethodAdapter debugMethodAdapter;

    DebugClassAdapter(final ClassVisitor cv, final Map<String, List<String>> methodParametersMap) {
        super(Opcodes.ASM5, cv);
        this.methodParametersMap = methodParametersMap;
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        String methodUniqueKey = name + desc + signature;
        debugMethodAdapter = new DebugMethodAdapter(methodUniqueKey, methodParametersMap.get(methodUniqueKey), mv);
        return mv == null ? null : debugMethodAdapter;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

}