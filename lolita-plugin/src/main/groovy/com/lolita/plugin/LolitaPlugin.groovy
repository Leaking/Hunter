package com.lolita.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Quinn on 25/02/2017.
 */

public class LolitaPlugin implements Plugin<Project> {
    void apply(Project project) {
        println "LolitaPlugin project apply " + project.getName();
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
            if(sdkDir == null || sdkDir.length() == 0) {
                throw new RuntimeException(
                        "No sdk.dir property defined in local.properties file.")
            }
            sdkDir = sdkDir + File.separator + "platforms"
            File sdkFolder = new File(sdkDir);
            def availableSdkPaths = sdkFolder.listFiles(new FilenameFilter() {
                @Override
                boolean accept(File file, String filename) {
                    if(filename != null && filename.startsWith("android")) {
                        return true
                    } else {
                        return false
                    }
                }
            })
            availableSdkPaths = availableSdkPaths.sort().reverse()
            //Get android.jar which is the max version.
            def androidClassPath = availableSdkPaths[0].getPath() + File.separator + "android.jar";
            project.android.registerTransform(new LolitaTransform(androidClassPath))
        } else {
            throw new RuntimeException(
                    "No local.properties file.")
        }
    }
}
