package com.quinn.hunter.plugin;

import com.android.build.api.transform.*;
import com.android.build.gradle.internal.LoggerWrapper;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.build.gradle.internal.transforms.DexTransform;
import com.android.ide.common.internal.WaitableExecutor;
import com.quinn.hunter.plugin.bytecode.ClassLoaderHelper;
import com.quinn.hunter.plugin.bytecode.timing.TimingWeaver;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
/**
 * Created by Quinn on 26/02/2017.
 */
/**
 * Transform to modify bytecode
 */
class HunterTransform extends Transform {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(HunterTransform.class);

    private static final Set<QualifiedContent.Scope> SCOPES = new HashSet<>();

    static {
        SCOPES.add(QualifiedContent.Scope.PROJECT);
        SCOPES.add(QualifiedContent.Scope.SUB_PROJECTS);
        SCOPES.add(QualifiedContent.Scope.EXTERNAL_LIBRARIES);
    }

    private Project project;
    private HunterExtension hunterExtension;
    private TimingWeaver bytecodeWeaver;
    private WaitableExecutor waitableExecutor;



    public HunterTransform(Project project){
        this.project = project;
        this.hunterExtension = (HunterExtension)project.getExtensions().getByName("hunterExt");
        this.waitableExecutor = WaitableExecutor.useGlobalSharedThreadPool();
    }

    @Override
    public String getName() {
        return "HunterTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<QualifiedContent.Scope> getScopes() {
        return SCOPES;
    }

    @Override
    public boolean isIncremental() {
        return true;
    }


    @Override
    public void transform(Context context,
                   Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider,
                   boolean isIncremental) throws IOException, TransformException, InterruptedException {

        logger.info(getName() + " is starting...isIncremental = " + isIncremental);
        long startTime = System.currentTimeMillis();
        if(!isIncremental) {
            outputProvider.deleteAll();
        }
        URLClassLoader urlClassLoader = ClassLoaderHelper.getClassLoader(inputs, referencedInputs, project);
        this.bytecodeWeaver = new TimingWeaver(urlClassLoader);
        for(TransformInput input : inputs) {
            for(JarInput jarInput : input.getJarInputs()) {
                Status status = jarInput.getStatus();
                File dest = outputProvider.getContentLocation(
                        jarInput.getFile().getAbsolutePath(),
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR);
                if(isIncremental) {
                    switch(status) {
                        case NOTCHANGED:
                            continue;
                        case ADDED:
                        case CHANGED:
                            transformJar(jarInput.getFile(), dest, status);
                            break;
                        case REMOVED:
                            if (dest.exists()) {
                                FileUtils.forceDelete(dest);
                            }
                            break;
                    }
                } else {
                    transformJar(jarInput.getFile(), dest, status);
                }
            }

            for(DirectoryInput directoryInput : input.getDirectoryInputs()) {
                File dest = outputProvider.getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(), directoryInput.getScopes(),
                        Format.DIRECTORY);
                FileUtils.forceMkdir(dest);
                if(isIncremental) {
                    String srcDirPath = directoryInput.getFile().getAbsolutePath();
                    String destDirPath = dest.getAbsolutePath();
                    Map<File, Status> fileStatusMap = directoryInput.getChangedFiles();
                    for (Map.Entry<File, Status> changedFile : fileStatusMap.entrySet()) {
                        Status status = changedFile.getValue();
                        File inputFile = changedFile.getKey();
                        switch (status) {
                            case NOTCHANGED:
                                break;
                            case REMOVED:
                                if(inputFile.exists()) {
                                    FileUtils.forceDelete(inputFile);
                                }
                                break;
                            case ADDED:
                            case CHANGED:
                                String destFilePath = inputFile.getAbsolutePath().replace(srcDirPath, destDirPath);
                                File destFile = new File(destFilePath);
                                FileUtils.touch(destFile);
                                transformSingleFile(inputFile, destFile, status);
                                break;
                        }
                    }
                } else {
                    transformDir(directoryInput.getFile(), dest);
                }

            }

        }

        waitableExecutor.waitForTasksWithQuickFail(true);
        long costTime = System.currentTimeMillis() - startTime;
        logger.info((getName() + " costed " + costTime + "ms"));
    }

    private void transformSingleFile(final File inputFile, final File outputFile, final Status status) {
        waitableExecutor.execute(() -> {
            bytecodeWeaver.weaveSingleClassToFile(inputFile, outputFile);
            return null;
        });
    }

    private void transformDir(final File inputDir, final File outputDir) {
        final String inputDirPath = inputDir.getAbsolutePath();
        final String outputDirPath = outputDir.getAbsolutePath();
        if (inputDir.isDirectory()) {
            for (final File file : com.android.utils.FileUtils.getAllFiles(inputDir)) {
                waitableExecutor.execute(() -> {
                    String filePath = file.getAbsolutePath();
                    File outputFile = new File(filePath.replace(inputDirPath, outputDirPath));
                    if (bytecodeWeaver.isWeavableClass(filePath)) {
                        FileUtils.touch(outputFile);
                        bytecodeWeaver.weaveSingleClassToFile(file, outputFile);
                    } else {
                        if (file.isFile()) {
                            FileUtils.touch(outputFile);
                            FileUtils.copyFile(file, outputFile);
                        }
                    }
                    return null;
                });
            }
        }
    }

    private void transformJar(final File srcJar, final File destJar, Status status) {
        waitableExecutor.execute(() -> {
            long start = System.currentTimeMillis();
            bytecodeWeaver.weaveJar(srcJar, destJar);
            long costed = System.currentTimeMillis() - start;
            logger.info("transformJar jar " + srcJar.getAbsolutePath() + " costed " + costed);
            return null;
        });
    }



    @Override
    public boolean isCacheable() {
        return true;
    }


}
