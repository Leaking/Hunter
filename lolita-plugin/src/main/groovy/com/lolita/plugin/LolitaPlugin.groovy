package com.lolita.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project;

/**
 * Created by Quinn on 25/02/2017.
 */

public class LolitaPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('testTask') << {
            println "Hello gradle plugin"
        }
        project.android.registerTransform(new Lolita())
    }
}
