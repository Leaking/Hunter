package com.quinn.hunter.plugin

import com.android.build.api.transform.*
import com.android.ide.common.internal.WaitableExecutor
import com.quinn.hunter.plugin.log.Logging
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import java.util.concurrent.Callable
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
    private WaitableExecutor waitableExecutor;
    private final Logging logger = Logging.getLogger("HunterTransform");


    public HunterTransform(Project project){
        this.project = project;
        this.hunterExtension = project.hunterExt;
        this.waitableExecutor = WaitableExecutor.useGlobalSharedThreadPool();
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
        return true;
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
                File dest = outputProvider.getContentLocation(
                        jarInput.file.absolutePath,
                        jarInput.contentTypes,
                        jarInput.scopes,
                        Format.JAR)
                if(isIncremental) {
                    switch(status) {
                        case Status.NOTCHANGED:
                            continue;
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
                } else {
                    transformJar(jarInput.file, dest)
                }
            }

            for(DirectoryInput directoryInput : input.directoryInputs) {
                File dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.forceMkdir(dest);
                if(isIncremental) {
                    String srcDirPath = directoryInput.file.absolutePath;
                    String destDirPath = dest.getAbsolutePath();
                    Map<File, Status> fileStatusMap = directoryInput.getChangedFiles()
                    println("change file size " + fileStatusMap.size())
                    for (Map.Entry<File, Status> changedFile : fileStatusMap.entrySet()) {
                        Status status = changedFile.getValue();
                        File inputFile = changedFile.getKey();
                        println(changedFile.getKey().getName() + " -- " + status)
                        switch (status) {
                            case Status.NOTCHANGED:
                                break;
                            case Status.REMOVED:
                                FileUtils.forceDelete(inputFile);
                                break;
                            case Status.ADDED:
                            case Status.CHANGED:
                                String destFilePath = inputFile.getAbsolutePath().replace(srcDirPath, destDirPath);
                                File destFile = new File(destFilePath);
                                FileUtils.touch(destFile);
                                transformSingleFile(inputFile, destFile)
                                break;
                        }
                    }
                } else {
                    transformDir(directoryInput.file, dest)
                }

            }

        }

        waitableExecutor.waitForTasksWithQuickFail(true);
        def costTime = System.currentTimeMillis() - startTime
        println (getName() + " costed " + costTime + "ms")
    }

    private void transformSingleFile(File inputFile, File outputFile) {
        bytecodeWeaver.weaveSingleClassToFile(inputFile, outputFile);
    }

    private void transformDir(File inputDir, File outputDir) {
        println("transformDir dir " + inputDir.getName())
        bytecodeWeaver.weaveDirectory(inputDir, outputDir)
    }

    private void transformJar(File srcJar, File destJar) {
        println("transformJar jar " + srcJar.getName() + " to dest " + destJar.getName())
        waitableExecutor.execute(new Callable() {
            @Override
            Object call() throws Exception {
                bytecodeWeaver.weaveJar(srcJar, destJar)
                return null
            }
        })
//        FileUtils.copyFile(srcJar, destJar)
    }

    private void println(String str) {
        logger.log(str)
    }

    @Override
    boolean isCacheable() {
        return true
    }


}
