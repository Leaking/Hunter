package com.quinn.hunter.plugin.timing.bytecode;


import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.timing.TimingHunterExtension;
import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Created by Quinn on 09/07/2017.
 */
public final class TimingWeaver extends BaseWeaver {

    private static final String PLUGIN_LIBRARY = "com.hunter.library.timing";
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(TimingWeaver.class);

    private TimingHunterExtension timingHunterExtension;

    @Override
    public void setExtension(Object extension) {
        if(extension == null) return;
        this.timingHunterExtension = (TimingHunterExtension) extension;
    }

    @Override
    public boolean isWeavableClass(String fullQualifiedClassName) {
        boolean superResult = super.isWeavableClass(fullQualifiedClassName);
        boolean isByteCodePlugin = fullQualifiedClassName.startsWith(PLUGIN_LIBRARY);
        boolean inBlackList = false;
        if(timingHunterExtension != null) {
            for(String item : timingHunterExtension.blacklist) {
                if(fullQualifiedClassName.startsWith(item)) {
                    inBlackList = true;
                }
            }
        }
        if(inBlackList) logger.info("In blacklist " + fullQualifiedClassName);
        if(isByteCodePlugin) logger.info("In ByteCodePlugin " + fullQualifiedClassName);
        return superResult && !isByteCodePlugin && !inBlackList;
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new TimingClassAdapter(classWriter);
    }

}
