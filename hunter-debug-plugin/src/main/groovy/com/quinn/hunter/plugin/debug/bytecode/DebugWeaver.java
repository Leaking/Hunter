package com.quinn.hunter.plugin.debug.bytecode;

import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.debug.DebugHunterExtension;
import com.quinn.hunter.transform.asm.BaseWeaver;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Created by Quinn on 09/07/2017.
 */
public final class DebugWeaver extends BaseWeaver {

    private static final String PLUGIN_LIBRARY = "com.hunter.library.timing";
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugWeaver.class);

    private DebugHunterExtension extension;

    @Override
    public void setExtension(Object extension) {
        if(extension == null) return;
        this.extension = (DebugHunterExtension) extension;
    }

    @Override
    public boolean isWeavableClass(String fullQualifiedClassName) {
        boolean superResult = super.isWeavableClass(fullQualifiedClassName);
        boolean isByteCodePlugin = fullQualifiedClassName.startsWith(PLUGIN_LIBRARY);
        if(extension != null) {
            //whitelist is prior to to blacklist
            if(!extension.whitelist.isEmpty()) {
                boolean inWhiteList = false;
                for(String item : extension.whitelist) {
                    if(fullQualifiedClassName.startsWith(item)) {
                        inWhiteList = true;
                    }
                }
                return superResult && !isByteCodePlugin && inWhiteList;
            }
            if(!extension.blacklist.isEmpty()) {
                boolean inBlackList = false;
                for(String item : extension.blacklist) {
                    if(fullQualifiedClassName.startsWith(item)) {
                        inBlackList = true;
                    }
                }
                return superResult && !isByteCodePlugin && !inBlackList;
            }
        }
        return superResult && !isByteCodePlugin;
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new DebugClassAdapter(classWriter);
    }

}
