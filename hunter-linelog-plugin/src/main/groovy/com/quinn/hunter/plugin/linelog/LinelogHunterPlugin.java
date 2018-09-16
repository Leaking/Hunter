package com.quinn.hunter.plugin.linelog;

import com.android.build.gradle.AppExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import java.util.Collections;

/**
 * Created by Quinn on 15/09/2018.
 */
public class LinelogHunterPlugin implements Plugin<Project> {

    @SuppressWarnings("NullableProblems")
    @Override
    public void apply(Project project) {
        AppExtension appExtension = (AppExtension)project.getProperties().get("android");
        appExtension.registerTransform(new LinelogHunterTransform(project), Collections.EMPTY_LIST);
    }

}
