package com.quinn.hunter.plugin.timing.bytecode;

import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;

import java.util.Collections;
import java.util.List;

public final class TimingWeaver extends BaseWeaver {

    private static final String PLUGIN_LIBRARY_PREFIX = "com.hunter.library.timing";

    private final List<String> whitelist;
    private final List<String> blacklist;

    public TimingWeaver(List<String> whitelist, List<String> blacklist) {
        this.whitelist = whitelist != null ? whitelist : Collections.emptyList();
        this.blacklist = blacklist != null ? blacklist : Collections.emptyList();
    }

    @Override
    public boolean isWeavableClass(String fullyQualifiedClassName) {
        if (!super.isWeavableClass(fullyQualifiedClassName)) return false;
        if (fullyQualifiedClassName.startsWith(PLUGIN_LIBRARY_PREFIX)) return false;
        if (!whitelist.isEmpty()) {
            for (String item : whitelist) {
                if (fullyQualifiedClassName.startsWith(item)) return true;
            }
            return false;
        }
        if (!blacklist.isEmpty()) {
            for (String item : blacklist) {
                if (fullyQualifiedClassName.startsWith(item)) return false;
            }
        }
        return true;
    }

    @Override
    public ClassVisitor wrap(ClassVisitor next) {
        return new TimingClassAdapter(next);
    }
}
