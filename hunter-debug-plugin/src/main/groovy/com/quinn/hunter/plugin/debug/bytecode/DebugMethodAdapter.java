package com.quinn.hunter.plugin.debug.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Quinn on 16/09/2018.
 */
public final class DebugMethodAdapter extends MethodVisitor implements Opcodes {


    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugMethodAdapter.class);
    private List<String> parameters;
    private boolean debugMethod = false;
    private boolean stepByStep = false;

    public DebugMethodAdapter(String methodKey, List<String> parameters, MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
        this.parameters = parameters;
    }

    @Override
    public void visitParameter(String name, int access) {
        logger.info("parameter name" + name);
        /**
         * not called except you compile with -parameters option
         */
        super.visitParameter(name, access);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        logger.info("visit annotation " + desc);
        AnnotationVisitor defaultAv = super.visitAnnotation(desc, visible);
        if("Lcom/hunter/library/debug/HunterDebug;".equals(desc)) {
            debugMethod = true;
            return new AnnotationVisitor(Opcodes.ASM5, defaultAv) {
                @Override
                public void visit(String name, Object value) {
                    if("stepByStep".equals(name) && (Boolean)value) {
                        stepByStep = true;
                    }
                    super.visit(name, value);
                }
            };
        } else {
            return defaultAv;
        }
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

}
