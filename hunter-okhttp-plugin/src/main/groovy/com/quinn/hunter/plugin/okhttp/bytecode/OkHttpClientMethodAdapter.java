package com.quinn.hunter.plugin.okhttp.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


/**
 * Created by Owenli on 03/15/2021.
 */
public final class OkHttpClientMethodAdapter extends MethodVisitor {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(OkHttpClientMethodAdapter.class);

    private boolean weaveEventListener;

    OkHttpClientMethodAdapter(MethodVisitor mv, boolean weaveEventListener) {
        super(Opcodes.ASM7, mv);
        this.weaveEventListener = weaveEventListener;
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        if (opcode == Opcodes.GETFIELD && "okhttp3/OkHttpClient$Builder".equals(owner) && "eventListenerFactory".equals(name)) {
            super.visitFieldInsn(opcode, owner, name, descriptor);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/hunter/library/okhttp/OkHttpHooker", "getGlobalEventFactory", "(Lokhttp3/EventListener$Factory;)Lokhttp3/EventListener$Factory;", false);
        } else {
            super.visitFieldInsn(opcode, owner, name, descriptor);
        }
    }

}
