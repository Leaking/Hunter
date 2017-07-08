package com.lolita.plugin

/**
 * Created by Quinn on 26/02/2017.
 */

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

/**
 * Transform to modify bytecode
 */
class LolitaTransform extends Transform {

    private static final Set<QualifiedContent.Scope> SCOPES = new HashSet<>();
    static {
        SCOPES.add(QualifiedContent.Scope.PROJECT);
        SCOPES.add(QualifiedContent.Scope.PROJECT_LOCAL_DEPS);
        SCOPES.add(QualifiedContent.Scope.SUB_PROJECTS);
        SCOPES.add(QualifiedContent.Scope.SUB_PROJECTS_LOCAL_DEPS);
        SCOPES.add(QualifiedContent.Scope.EXTERNAL_LIBRARIES);
    }

    private ArrayList<String> androidClassPaths = new ArrayList<>();
    private ByteCodeWeaver byteCodeWeaver;
    private Project project;
    private LolitaExtension lolitaExtension;

    public LolitaTransform(LolitaExtension lolitaExtension, Project project, String androidSdkPath){
        this.project = project;
        this.lolitaExtension = lolitaExtension;
        this.androidClassPaths.add(androidSdkPath);
        byteCodeWeaver = new ByteCodeWeaver();
    }

    @Override
    String getName() {
        return "LolitaTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return Collections.singleton(QualifiedContent.DefaultContentType.CLASSES)
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return Collections.singleton(QualifiedContent.Scope.PROJECT)
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        println("Start to transform")
        //获取所有dependency的路径
        for (Iterator<Configuration> iter = project.getConfigurations().iterator(); iter.hasNext(); ) {
            Configuration element = iter.next();
            Set<File> filesSet = element.resolve();
            for (Iterator<File> filesIterator = filesSet.iterator(); filesIterator.hasNext();) {
                File file = filesIterator.next();
                androidClassPaths.add(file.getPath());
            }
        }
        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput directoryInput ->
                byteCodeWeaver.weave(lolitaExtension, androidClassPaths, directoryInput.file.absolutePath)
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
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
        }
    }
}
