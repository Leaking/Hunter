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
public final class DebugPreGoClassAdapter extends ClassVisitor{

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugPreGoClassAdapter.class);
    private Map<String, List<String>> methodParametersMap = new HashMap<>();
    private DebugPreGoMethodAdapter debugPreGoMethodAdapter;
    private boolean needParameter = false;

    DebugPreGoClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if(needParameter == false && debugPreGoMethodAdapter != null) {
            needParameter = true;
        }
        String methodUniqueKey = name + desc + signature;
        debugPreGoMethodAdapter = new DebugPreGoMethodAdapter(methodUniqueKey, methodParametersMap, mv);
        return mv == null ? null : debugPreGoMethodAdapter;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        logger.info("methodParametersMap " + methodParametersMap);
    }

    public void setMethodParametersMap(Map<String, List<String>> methodParametersMap){
        this.methodParametersMap = methodParametersMap;
    }

}