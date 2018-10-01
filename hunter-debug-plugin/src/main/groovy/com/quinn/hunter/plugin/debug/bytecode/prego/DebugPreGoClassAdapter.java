package com.quinn.hunter.plugin.debug.bytecode.prego;

import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.debug.bytecode.Parameter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Quinn on 16/09/2018.
 */
public final class DebugPreGoClassAdapter extends ClassVisitor{

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugPreGoClassAdapter.class);
    private Map<String, List<Parameter>> methodParametersMap = new HashMap<>();
    private DebugPreGoMethodAdapter debugPreGoMethodAdapter;
    private boolean needParameter = false;
    private String className;

    public DebugPreGoClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
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
        if(debugPreGoMethodAdapter != null && debugPreGoMethodAdapter.getNeedParameter()) {
            needParameter = true;
        }
        String methodUniqueKey = name + desc;
        debugPreGoMethodAdapter = new DebugPreGoMethodAdapter(methodUniqueKey, methodParametersMap, mv);
        return mv == null ? null : debugPreGoMethodAdapter;
    }

    public Map<String, List<Parameter>> getMethodParametersMap(){
        return this.methodParametersMap;
    }

    public boolean isNeedParameter() {
        return needParameter;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        if(debugPreGoMethodAdapter != null && debugPreGoMethodAdapter.getNeedParameter()) {
            needParameter = true;
        }
    }
}