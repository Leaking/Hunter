package com.jun.hunter.huntersingleclickplugin;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Collections;

public class SingleClickHunterPlugin implements Plugin<Project> {

    @SuppressWarnings("NullableProblems")
    @Override
    public void apply(Project project) {
        AppExtension appExtension = (AppExtension)project.getProperties().get("android");
        appExtension.registerTransform(new SingleClickHunterTransform(project), Collections.EMPTY_LIST);
    }

}
