package com.quinn.hunter.plugin;


import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.bytecode.TimingWeaver;
import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by quinn on 07/09/2018
 */
public class TimingHunterTransform extends HunterTransform {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(TimingHunterTransform.class);

    private Project project;

    public TimingHunterTransform(Project project) {
        super(project);
        this.project = project;
        project.getExtensions().create("hunterExt", HunterExtension.class);
        this.bytecodeWeaver = new TimingWeaver();
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        HunterExtension hunterExtension = (HunterExtension) project.getExtensions().getByName("hunterExt");
        logger.info("hunter2 2 ext global " + hunterExtension.global + " on " + hunterExtension.on);
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }
}
