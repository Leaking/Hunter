package com.quinn.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project;

/**
 * Created by Quinn on 25/02/2017.
 */

public class PluginImpl implements Plugin<Project> {
    void apply(Project project) {
        project.task('testTask') << {
            println "Hello gradle plugin"
        }
        target.android.registerTransform(new QuinnTransform())
    }
}
