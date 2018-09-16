package com.quinn.hunter.plugin.linelog;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.linelog.bytecode.LinelogWeaver;
import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by quinn on 07/09/2018
 */
public final class LinelogHunterTransform extends HunterTransform {

    private Project project;
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(LinelogWeaver.class);

    public LinelogHunterTransform(Project project) {
        super(project);
        this.project = project;
        this.bytecodeWeaver = new LinelogWeaver();
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }

}
