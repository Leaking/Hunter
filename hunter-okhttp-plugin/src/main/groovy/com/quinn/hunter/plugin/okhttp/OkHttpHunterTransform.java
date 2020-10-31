package com.quinn.hunter.plugin.okhttp;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.quinn.hunter.plugin.okhttp.bytecode.OkHttpWeaver;
import com.quinn.hunter.transform.HunterTransform;
import com.quinn.hunter.transform.RunVariant;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Quinn on 09/09/2018.
 */
final class OkHttpHunterTransform extends HunterTransform {

    private Project project;
    private OkHttpHunterExtension okHttpHunterExtension;

    public OkHttpHunterTransform(Project project) {
        super(project);
        this.project = project;
        project.getExtensions().create("okHttpHunterExt", OkHttpHunterExtension.class);
        this.bytecodeWeaver = new OkHttpWeaver();
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        okHttpHunterExtension = (OkHttpHunterExtension) project.getExtensions().getByName("okHttpHunterExt");
        this.bytecodeWeaver.setExtension(okHttpHunterExtension);
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }

    @Override
    protected RunVariant getRunVariant() {
        return okHttpHunterExtension.runVariant;
    }

    @Override
    protected boolean inDuplicatedClassSafeMode() {
        return okHttpHunterExtension.duplicatedClassSafeMode;
    }
}
