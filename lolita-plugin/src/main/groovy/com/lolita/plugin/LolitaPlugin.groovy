package com.lolita.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project;

/**
 * Created by Quinn on 25/02/2017.
 */

public class LolitaPlugin implements Plugin<Project> {
    void apply(Project project) {
        print "LolitaPlugin project here " + project.getName();
        project.dependencies {
            compile 'com.lolita.annotations:lolita-annotations:1.0.0'
        }
        project.android.registerTransform(new Lolita())
    }
}
