package com.quinn.hunter.plugin.timing;

import com.android.build.api.variant.Variant;

import com.quinn.hunter.plugin.timing.bytecode.TimingClassVisitorFactory;
import com.quinn.hunter.transform.HunterPlugin;

import org.gradle.api.Project;

public class TimingHunterPlugin extends HunterPlugin<TimingHunterParameters, TimingClassVisitorFactory> {

    @Override
    protected void registerExtension(Project project) {
        project.getExtensions().create("timingHunterExt", TimingHunterExtension.class);
    }

    @Override
    protected boolean skipVariant(Project project, Variant variant) {
        TimingHunterExtension ext =
                (TimingHunterExtension) project.getExtensions().getByName("timingHunterExt");
        return ext.runVariant.shouldSkip(variant);
    }

    @Override
    protected void configureParams(Project project, Variant variant, TimingHunterParameters params) {
        super.configureParams(project, variant, params);
        TimingHunterExtension ext =
                (TimingHunterExtension) project.getExtensions().getByName("timingHunterExt");
        params.getWhitelist().set(ext.whitelist);
        params.getBlacklist().set(ext.blacklist);
    }

    @Override
    protected Class<TimingClassVisitorFactory> getFactoryClass() {
        return TimingClassVisitorFactory.class;
    }
}
