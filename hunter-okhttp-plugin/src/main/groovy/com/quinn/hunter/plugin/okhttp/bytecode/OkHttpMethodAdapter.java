package com.quinn.hunter.plugin.okhttp.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * Only weave okhttp3/OkHttpClient's init method
 * Created by Quinn on 09/09/2018.
 */
public final class OkHttpMethodAdapter extends LocalVariablesSorter implements Opcodes {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(OkHttpMethodAdapter.class);

    private boolean weaveEventListener;

    OkHttpMethodAdapter(int access, String desc, MethodVisitor mv, boolean weaveEventListener) {
        super(Opcodes.ASM7, access, desc, mv);
        this.weaveEventListener = weaveEventListener;
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            //EventListenFactory
            if(weaveEventListener) {
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETSTATIC, "com/hunter/library/okhttp/OkHttpHooker", "globalEventFactory", "Lokhttp3/EventListener$Factory;");
                mv.visitFieldInsn(PUTFIELD, "okhttp3/OkHttpClient$Builder", "eventListenerFactory", "Lokhttp3/EventListener$Factory;");
            }

            //Dns
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETSTATIC, "com/hunter/library/okhttp/OkHttpHooker", "globalDns", "Lokhttp3/Dns;");
            mv.visitFieldInsn(PUTFIELD, "okhttp3/OkHttpClient$Builder", "dns", "Lokhttp3/Dns;");

            //Interceptor
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "okhttp3/OkHttpClient$Builder", "interceptors", "Ljava/util/List;");
            mv.visitFieldInsn(GETSTATIC, "com/hunter/library/okhttp/OkHttpHooker", "globalInterceptors", "Ljava/util/List;");
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "addAll", "(Ljava/util/Collection;)Z", true);
            mv.visitInsn(POP);

            //NetworkInterceptor
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "okhttp3/OkHttpClient$Builder", "networkInterceptors", "Ljava/util/List;");
            mv.visitFieldInsn(GETSTATIC, "com/hunter/library/okhttp/OkHttpHooker", "globalNetworkInterceptors", "Ljava/util/List;");
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "addAll", "(Ljava/util/Collection;)Z", true);
            mv.visitInsn(POP);
        }
        super.visitInsn(opcode);
    }

}
