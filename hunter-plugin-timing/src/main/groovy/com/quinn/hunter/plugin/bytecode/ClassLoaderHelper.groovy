package com.quinn.hunter.plugin.bytecode

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInput
import com.google.common.collect.ImmutableList
import com.google.common.collect.Iterables
import org.gradle.api.Project
/**
 * Created by quinn on 31/08/2018
 */
public class ClassLoaderHelper {


    public static URLClassLoader getClassLoader(Collection<TransformInput> inputs,
                                                Collection<TransformInput> referencedInputs,
                                                Project project) {
        ImmutableList.Builder<URL> urls = new ImmutableList.Builder();
        String androidJarPath  = getAndroidJarPath(project);
        File file = new File(androidJarPath);
        URL androidJarURL = file.toURI().toURL();
        urls.add(androidJarURL)
        for (TransformInput totalInputs : Iterables.concat(inputs, referencedInputs)) {
            for (DirectoryInput directoryInput : totalInputs.getDirectoryInputs()) {
                if (directoryInput.getFile().isDirectory()) {
                    urls.add(directoryInput.getFile().toURI().toURL());
                }
            }
            for (JarInput jarInput : totalInputs.getJarInputs()) {
                if (jarInput.getFile().isFile()) {
                    urls.add(jarInput.getFile().toURI().toURL());
                }
            }
        }
        ImmutableList<URL> allUrls = urls.build();
        URL[] classLoaderUrls = allUrls.toArray(new URL[allUrls.size()]);
        return new URLClassLoader(classLoaderUrls);
    }


    private static String getAndroidJarPath(Project project) {
        String rootDir = project.rootDir
        File localProperties = new File(rootDir, "local.properties")
        if (localProperties.exists()) {
            Properties properties = new Properties()
            localProperties.withInputStream { instr ->
                properties.load(instr)
            }
            String sdkDir = properties.getProperty('sdk.dir')
            if(sdkDir == null || sdkDir.length() == 0) {
                throw new RuntimeException(
                        "No sdk.dir property defined in local.properties file.");
            }
            sdkDir = sdkDir + File.separator + "platforms" + File.separator
            String androidJarPath = sdkDir + project.android.compileSdkVersion + File.separator + "android.jar";
            return androidJarPath
        } else {
            throw new RuntimeException(
                    "No local.properties file, you need it!!")
        }
    }


}
