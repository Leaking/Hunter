package com.lolita.plugin


import com.lolita.plugin.LolitaTransform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration


/**
 * Created by Quinn on 25/02/2017.
 */

public class LolitaPlugin implements Plugin<Project> {
    void apply(Project project) {
        println "LolitaPlugin project apply " + project.getName();




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
            sdkDir = sdkDir + File.separator + "platforms" + File.separator
            //Get android.jar which is the max version.
            def androidClassPath = sdkDir + project.android.compileSdkVersion + File.separator + "android.jar";
            println "compile sdk dir = " + androidClassPath
            project.android.registerTransform(new LolitaTransform(project, androidClassPath))
        } else {
            throw new RuntimeException(
                    "No local.properties file.")
        }




    }
}
