package com.quinn.hunter.plugin.linelog;

import com.android.build.api.variant.Variant;

import com.quinn.hunter.plugin.linelog.bytecode.LinelogClassVisitorFactory;
import com.quinn.hunter.transform.HunterParameters;
import com.quinn.hunter.transform.HunterPlugin;

import org.gradle.api.Project;

public class LinelogHunterPlugin extends HunterPlugin<HunterParameters, LinelogClassVisitorFactory> {

    @Override
    protected void registerExtension(Project project) {
        project.getExtensions().create("linelogHunterExt", LinelogHunterExtension.class);
    }

    @Override
    protected boolean skipVariant(Project project, Variant variant) {
        LinelogHunterExtension ext =
                (LinelogHunterExtension) project.getExtensions().getByName("linelogHunterExt");
        return ext.runVariant.shouldSkip(variant);
    }

    @Override
    protected Class<LinelogClassVisitorFactory> getFactoryClass() {
        return LinelogClassVisitorFactory.class;
    }
}
