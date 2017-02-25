package com.quinn.plugin

import com.android.build.api.transform.Context
import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider

class QuinnTransform extends Transform {

    @Override
    String getName() {
        return "QuinnTransform";
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

        def outDir = outputProvider.getContentLocation("QuinnTransform", outputTypes, scopes, Format.DIRECTORY)

        outDir.deleteDir()
        outDir.mkdirs()

        inputs.each {
            it.directoryInputs.each {
                int pathBitLen = it.file.toString().length()
                it.file.traverse {
                    def path = "${it.toString().substring(pathBitLen)}"
                    if (it.isDirectory()) {
                        new File(outDir, path).mkdirs()
                    } else {
                        if (! path.endsWith("BuildConfig.class")) {
                            new File(outDir, path).bytes = it.bytes
                        }
                    }
                }
            }
        }
    }
}