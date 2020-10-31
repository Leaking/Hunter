package com.quinn.hunter.plugin.debug.bytecode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Quinn on 16/09/2018.
 */
public final class DebugClassAdapter extends ClassVisitor{

    private Map<String, List<Parameter>> methodParametersMap;
    private DebugMethodAdapter debugMethodAdapter;
    private String className;

    private List<String> includeMethods = new ArrayList<String>();
    private List<String> implMethods = new ArrayList<>();

    DebugClassAdapter(final ClassVisitor cv, final Map<String, List<Parameter>> methodParametersMap) {
        super(Opcodes.ASM7, cv);
        this.methodParametersMap = methodParametersMap;
    }

    public void attachIncludeMethodsAndImplMethods(List<String> includeMethods,List<String> implMethods){
        this.includeMethods.addAll(includeMethods);
        this.implMethods.addAll(implMethods);
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
        if(includeMethods.contains(name)){
            String methodUniqueKey = name + desc;
            debugMethodAdapter = new DebugMethodAdapter(className, methodParametersMap.get(methodUniqueKey), name, access, desc, mv);
            if(implMethods.contains(name)){
                debugMethodAdapter.switchToDebugImpl();
            }
            return debugMethodAdapter;
        }
        return mv;
    }

}