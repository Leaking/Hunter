package com.jun.hunter.huntersingleclickplugin;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.jun.hunter.huntersingleclickplugin.bytecode.SingleClickWeaver;
import com.quinn.hunter.transform.HunterTransform;
import com.quinn.hunter.transform.RunVariant;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;


public final class SingleClickHunterTransform extends HunterTransform {

    private Project project;
    private SingleClickHunterExtension singleClickHunterExtension;

    public SingleClickHunterTransform(Project project) {
        super(project);
        this.project = project;
        project.getExtensions().create("singleClickHunterExt", SingleClickHunterExtension.class);
        this.bytecodeWeaver = new SingleClickWeaver();
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        singleClickHunterExtension = (SingleClickHunterExtension) project.getExtensions().getByName("singleClickHunterExt");
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }

    @Override
    protected RunVariant getRunVariant() {
        return singleClickHunterExtension.runVariant;
    }

}
