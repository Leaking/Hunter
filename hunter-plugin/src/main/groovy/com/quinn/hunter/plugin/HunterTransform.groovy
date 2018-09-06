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
                            transformJar(jarInput.file, dest, status)
                            break;
                        case Status.REMOVED:
                            if (dest.exists()) {
                                FileUtils.forceDelete(dest)
                            }
                            break;
                    }
                } else {
                    transformJar(jarInput.file, dest, status)
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
                    for (Map.Entry<File, Status> changedFile : fileStatusMap.entrySet()) {
                        Status status = changedFile.getValue();
                        File inputFile = changedFile.getKey();
                        switch (status) {
                            case Status.NOTCHANGED:
                                break;
                            case Status.REMOVED:
                                if(inputFile.exists()) {
                                    FileUtils.forceDelete(inputFile)
                                }
                                break;
                            case Status.ADDED:
                            case Status.CHANGED:
                                String destFilePath = inputFile.getAbsolutePath().replace(srcDirPath, destDirPath);
                                File destFile = new File(destFilePath);
                                FileUtils.touch(destFile);
                                transformSingleFile(inputFile, destFile, status)
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

    private void transformSingleFile(File inputFile, File outputFile, Status status) {
        println("transform single File - " + inputFile.getName() + "; status - " + status)
        waitableExecutor.execute(new Callable() {
            @Override
            Object call() throws Exception {
                bytecodeWeaver.weaveSingleClassToFile(inputFile, outputFile);
                return null
            }
        })
    }

    private void transformDir(File inputDir, File outputDir) {
        long start = System.currentTimeMillis();
        String inputDirPath = inputDir.getAbsolutePath();
        String outputDirPath = outputDir.getAbsolutePath();
        if (inputDir.isDirectory()) {
            inputDir.eachFileRecurse { File file0 ->
                waitableExecutor.execute(new Callable() {
                    @Override
                    Object call() throws Exception {
                        File file = file0;
                        String filePath = file.absolutePath
                        File outputFile = new File(filePath.replace(inputDirPath, outputDirPath))
                        if (bytecodeWeaver.isWeavableClass(filePath)) {
                            FileUtils.touch(outputFile);
                            bytecodeWeaver.weaveSingleClassToFile(file, outputFile);
                        } else {
                            if(file.isFile()) {
                                FileUtils.touch(outputFile);
                                FileUtils.copyFile(file, outputFile);
                            }
                        }
                        return null
                    }
                })
            }
        }
        long costed = System.currentTimeMillis() - start;
        println("transformDir dir " + inputDir.getAbsolutePath() + " costed " + costed)
    }

    private void transformJar(File srcJar, File destJar, Status status) {
        waitableExecutor.execute(new Callable() {
            @Override
            Object call() throws Exception {
                long start = System.currentTimeMillis();
                bytecodeWeaver.weaveJar(srcJar, destJar)
                long costed = System.currentTimeMillis() - start;
                println("transformJar jar " + srcJar.getAbsolutePath() + " costed " + costed)
                return null
            }
        })
    }

    private void println(String str) {
        logger.log(str)
    }

    @Override
    boolean isCacheable() {
        return true
    }


}
