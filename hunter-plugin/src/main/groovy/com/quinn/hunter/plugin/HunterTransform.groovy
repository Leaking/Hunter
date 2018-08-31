package com.quinn.hunter.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.transforms.TransformInputUtil
import com.quinn.hunter.plugin.log.Logging
import org.apache.commons.codec.digest.DigestUtils
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
        URLClassLoader urlClassLoader = ClassLoaderHelper.getClassLoader(inputs, referencedInputs, project);
        this.bytecodeWeaver = new BytecodeWeaver(urlClassLoader);
        for(TransformInput input : inputs) {
            for(JarInput jarInput : input.jarInputs) {
//                Status status = jarInput.getStatus();
//                if (isIncremental && status == Status.NOTCHANGED) {
//                    continue;
//                }
                File dest = outputProvider.getContentLocation(
                        jarInput.name,
                        jarInput.contentTypes,
                        jarInput.scopes,
                        Format.JAR)
                Files.deleteIfExists(dest.toPath());
                processJar(jarInput.file, dest)
//                if (!incremental || status == Status.ADDED || status == Status.CHANGED) {
//                }
            }

            for(DirectoryInput directoryInput : input.directoryInputs) {
                bytecodeWeaver.weaveByteCode(directoryInput)
                directoryInput.getChangedFiles();
                File dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

        }

        def costTime = System.currentTimeMillis() - startTime
        println (getName() + " costed " + costTime + "ms")
    }

    private void processJar(File srcJar, File destJar) {
        println("process jar " + srcJar.getAbsolutePath())
        FileUtils.copyFile(srcJar, destJar)
    }

    private void println(String str) {
        logger.log(str)
    }

}
