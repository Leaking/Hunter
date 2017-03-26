package com.lolita.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.PluginContainer;

/**
 * Created by Quinn on 25/02/2017.
 */

public class LolitaPlugin implements Plugin<Project> {
    void apply(Project project) {
        println "LolitaPlugin project apply " + project.getName();
        project.dependencies {
            compile 'com.lolita.annotations:lolita-annotations:1.0.0'
        }
//        PluginContainer pluginContainer = project.getPlugins()
//        Plugin androidPlugin = pluginContainer.getPlugin("com.android.application")
//        println("property = " + androidPlugin.getProperties())
//        List<PropertyValue> pvs = androidPlugin.getMetaPropertyValues();
//        for(PropertyValue pv : pvs) {
//            println("pv = " + pv.getName() + " value = " + pv.getValue())
//        }

        def rootDir = project.rootDir
        def localProperties = new File(rootDir, "local.properties")
        println "localProperties " + localProperties
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
            println "sdkFolder = " + sdkFolder
            availableSdkPaths = availableSdkPaths.sort().reverse()
            for(File fileItem : availableSdkPaths){
                println "sdk filename = " + fileItem.getPath()
            }
            def androidClassPath = availableSdkPaths[0].getPath() + File.separator + "android.jar";
            project.android.registerTransform(new Lolita(androidClassPath))
        } else {
            throw new RuntimeException(
                    "No local.properties file.")
        }

    }
}
