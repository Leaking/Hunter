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
import jdk.internal.org.objectweb.asm.util.ASMifiable
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.internal.impldep.org.bouncycastle.crypto.Digest

import java.security.MessageDigest

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

    public LolitaTransform(Project project, String androidSdkPath){
        this.project = project;
        this.lolitaExtension = project.lolitaExt;
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
        SCOPES
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        println(getName() + " is starting...")
        inputs.each { TransformInput input ->
            input.jarInputs.each { JarInput jarInput ->
                def jarName = jarInput.name
                androidClassPaths.add(jarInput.file.getAbsolutePath());
//                println "jar path " + jarInput.file.getAbsolutePath() + " jarName = " + jarName;
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }
            input.directoryInputs.each { DirectoryInput directoryInput ->
                 com.lolita.plugin.asm.ASMUtils.weaveByteCode(androidClassPaths, directoryInput.file.absolutePath)
//                byteCodeWeaver.weave(lolitaExtension, androidClassPaths, directoryInput.file.absolutePath)
//                println "dir " + directoryInput.name;
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
    }
}
