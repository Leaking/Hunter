package com.quinn.hunter.plugin.debug.bytecode.prego;

import com.quinn.hunter.plugin.debug.bytecode.Parameter;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Quinn on 16/09/2018.
 */
public final class DebugPreGoClassAdapter extends ClassVisitor{

    private Map<String, List<Parameter>> methodParametersMap = new HashMap<>();
    private DebugPreGoMethodAdapter debugPreGoMethodAdapter;
    private boolean needParameter = false;

    private boolean classDebug = false;
    private List<String> includes = new ArrayList<>();
    private List<String> impls = new ArrayList<>();

    public DebugPreGoClassAdapter(final ClassVisitor cv) {
        super(Opcodes.ASM7, cv);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor orgin = super.visitAnnotation(desc, visible);
        if("Lcom/hunter/library/debug/HunterDebugClass;".equals(desc) ) {
            classDebug = true;
        }
        return orgin;
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        String methodUniqueKey = name + desc;
        debugPreGoMethodAdapter = new DebugPreGoMethodAdapter(name, methodUniqueKey, methodParametersMap, mv, classDebug, new MethodCollector() {
            @Override
            public void onIncludeMethod(String methodName, boolean useImpl) {
                if(useImpl){
                    impls.add(methodName);
                }
                includes.add(methodName);
                needParameter = true;
            }
        });
        return mv == null ? null : debugPreGoMethodAdapter;
    }

    public Map<String, List<Parameter>> getMethodParametersMap(){
        return this.methodParametersMap;
    }

    public List<String> getIncludes(){
        return includes;
    }

    public List<String> getImpls(){
        return impls;
    }
    public boolean isNeedParameter() {
        return needParameter;
    }



    interface MethodCollector{
        void onIncludeMethod(String methodName,boolean useImpl);
    }
}