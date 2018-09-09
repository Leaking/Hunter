package com.quinn.hunter.plugin.okhttp;

import com.android.build.gradle.AppExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import java.util.Collections;

/**
 * Created by Quinn on 25/02/2017.
 */
public class HunterTimingPlugin implements Plugin<Project> {

    @SuppressWarnings("NullableProblems")
    @Override
    public void apply(Project project) {
        AppExtension appExtension = (AppExtension)project.getProperties().get("android");
        appExtension.registerTransform(new OkhttpHunterTransform(project), Collections.EMPTY_LIST);
    }

}
