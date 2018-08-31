package com.quinn.hunter.plugin

import com.android.build.api.transform.*
import com.quinn.hunter.plugin.log.Logging
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import java.nio.file.Files

/**
 * Created by Quinn on 26/02/2017.
 */
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
    private final Logging logger = Logging.getLogger("HunterTransform");


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
        return true
    }


    @Override
    void transform(Context context,
                   Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider,
                   boolean isIncremental) throws IOException, TransformException, InterruptedException {

        println(getName() + " is starting...isIncremental = " + isIncremental)
        def startTime = System.currentTimeMillis()
        if(!isIncremental) {
            outputProvider.deleteAll();
        }
        URLClassLoader urlClassLoader = ClassLoaderHelper.getClassLoader(inputs, referencedInputs, project);
        this.bytecodeWeaver = new BytecodeWeaver(urlClassLoader);
        for(TransformInput input : inputs) {
            for(JarInput jarInput : input.jarInputs) {
                Status status = jarInput.getStatus();
                println(jarInput.getFile().getAbsolutePath() + " : " + status)
                if (isIncremental && status == Status.NOTCHANGED) {
                    continue;
                }
                File dest = outputProvider.getContentLocation(
                        jarInput.file.absolutePath,
                        jarInput.contentTypes,
                        jarInput.scopes,
                        Format.JAR)
                switch(status) {
                    case Status.ADDED:
                    case Status.CHANGED:
                        transformJar(jarInput.file, dest)
                        break;
                    case Status.REMOVED:
                        if (dest.exists()) {
                            FileUtils.forceDelete(dest)
                        }
                        break;
                }
            }

            for(DirectoryInput directoryInput : input.directoryInputs) {
                bytecodeWeaver.weaveByteCode(directoryInput)
                File dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

        }

        def costTime = System.currentTimeMillis() - startTime
        println (getName() + " costed " + costTime + "ms")
    }

    private void transformJar(File srcJar, File destJar) {
        println("transformJar jar " + srcJar.getName() + " to dest" + destJar.getName())
        FileUtils.copyFile(srcJar, destJar)
    }

    private void println(String str) {
        logger.log(str)
    }

    @Override
    boolean isCacheable() {
        return true
    }


}
