package com.quinn.hunter.plugin

import com.android.build.api.transform.*
import com.google.common.collect.ImmutableList
import com.google.common.collect.Iterables
import org.apache.commons.codec.digest.DigestUtils

/**
 * Created by Quinn on 26/02/2017.
 */
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

/**
 * Transform to modify bytecode
 */
class HunterTransform extends Transform {

    private static final Set<QualifiedContent.Scope> SCOPES = new HashSet<>();

    static {
        SCOPES.add(QualifiedContent.Scope.PROJECT);
        SCOPES.add(QualifiedContent.Scope.SUB_PROJECTS);
        SCOPES.add(QualifiedContent.Scope.EXTERNAL_LIBRARIES);
    }

    private Project project;
    private HunterExtension hunterExtension;
    private BytecodeWeaver bytecodeWeaver;

    public HunterTransform(Project project){
        this.project = project;
        this.hunterExtension = project.hunterExt;
    }

    @Override
    String getName() {
        return "HunterTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return Collections.singleton(QualifiedContent.DefaultContentType.CLASSES)
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        SCOPES
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context,
                   Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider,
                   boolean isIncremental) throws IOException, TransformException, InterruptedException {
        println(getName() + " is starting...")
        def startTime = System.currentTimeMillis()

        //initial class loader
        ImmutableList.Builder<URL> urls = new ImmutableList.Builder();
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
        URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);

        //use classloader to buid a bytecodeWeaver
        this.bytecodeWeaver = new BytecodeWeaver(urlClassLoader);

        inputs.each { TransformInput input ->
            input.jarInputs.each { JarInput jarInput ->
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }
            input.directoryInputs.each { DirectoryInput directoryInput ->
                println ("weaving dir " + directoryInput.file.absolutePath)
                bytecodeWeaver.weaveByteCode(directoryInput.file.absolutePath)
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
        def costTime = System.currentTimeMillis() - startTime
        println (getName() + " costed " + costTime + "ms"  + " success " + BytecodeWeaver.successCount + " fail " + BytecodeWeaver.failCount)
    }
}
