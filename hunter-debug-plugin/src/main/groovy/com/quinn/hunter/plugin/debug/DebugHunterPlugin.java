package com.quinn.hunter.plugin.debug;

import com.android.build.api.variant.Variant;

import com.quinn.hunter.plugin.debug.bytecode.DebugClassVisitorFactory;
import com.quinn.hunter.transform.HunterParameters;
import com.quinn.hunter.transform.HunterPlugin;

import org.gradle.api.Project;

public class DebugHunterPlugin extends HunterPlugin<HunterParameters, DebugClassVisitorFactory> {

    @Override
    protected void registerExtension(Project project) {
        project.getExtensions().create("debugHunterExt", DebugHunterExtension.class);
    }

    @Override
    protected boolean skipVariant(Project project, Variant variant) {
        DebugHunterExtension ext =
                (DebugHunterExtension) project.getExtensions().getByName("debugHunterExt");
        return ext.runVariant.shouldSkip(variant);
    }

    @Override
    protected Class<DebugClassVisitorFactory> getFactoryClass() {
        return DebugClassVisitorFactory.class;
    }
}
