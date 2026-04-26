package com.quinn.hunter.plugin.okhttp;

import com.android.build.api.variant.Variant;

import com.quinn.hunter.plugin.okhttp.bytecode.OkHttpClassVisitorFactory;
import com.quinn.hunter.transform.HunterPlugin;

import org.gradle.api.Project;

public final class OkHttpHunterPlugin extends HunterPlugin<OkHttpHunterParameters, OkHttpClassVisitorFactory> {

    @Override
    protected void registerExtension(Project project) {
        project.getExtensions().create("okHttpHunterExt", OkHttpHunterExtension.class);
    }

    @Override
    protected boolean skipVariant(Project project, Variant variant) {
        OkHttpHunterExtension ext =
                (OkHttpHunterExtension) project.getExtensions().getByName("okHttpHunterExt");
        return ext.runVariant.shouldSkip(variant);
    }

    @Override
    protected void configureParams(Project project, Variant variant, OkHttpHunterParameters params) {
        super.configureParams(project, variant, params);
        OkHttpHunterExtension ext =
                (OkHttpHunterExtension) project.getExtensions().getByName("okHttpHunterExt");
        params.getWeaveEventListener().set(ext.weaveEventListener);
    }

    @Override
    protected Class<OkHttpClassVisitorFactory> getFactoryClass() {
        return OkHttpClassVisitorFactory.class;
    }
}
