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

        def rootDir = project.rootDir
        def localProperties = new File(rootDir, "local.properties")
        if (localProperties.exists()) {
            Properties properties = new Properties()
            localProperties.withInputStream { instr ->
                properties.load(instr)
            }
            def sdkDir = properties.getProperty('sdk.dir')
            print sdkDir + "\n"
        }

        project.android.registerTransform(new Lolita())
    }
}
