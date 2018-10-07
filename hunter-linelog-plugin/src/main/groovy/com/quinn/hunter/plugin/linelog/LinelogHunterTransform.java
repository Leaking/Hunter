package com.quinn.hunter.plugin.linelog;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.quinn.hunter.plugin.linelog.bytecode.LinelogWeaver;
import com.quinn.hunter.transform.HunterTransform;
import com.quinn.hunter.transform.RunVariant;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;


/**
 * Created by Quinn on 15/09/2018.
 */
public final class LinelogHunterTransform extends HunterTransform {

    private Project project;
    private LinelogHunterExtension linelogHunterExtension;

    public LinelogHunterTransform(Project project) {
        super(project);
        this.project = project;
        project.getExtensions().create("linelogHunterExt", LinelogHunterExtension.class);
        this.bytecodeWeaver = new LinelogWeaver();
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        linelogHunterExtension = (LinelogHunterExtension) project.getExtensions().getByName("linelogHunterExt");
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }

    @Override
    protected RunVariant getRunVariant() {
        return linelogHunterExtension.runVariant;
    }

}
