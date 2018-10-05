package com.quinn.hunter.plugin.timing;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.timing.bytecode.TimingWeaver;
import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by quinn on 07/09/2018
 */
public final class TimingHunterTransform extends HunterTransform {

    private Project project;

    public TimingHunterTransform(Project project) {
        super(project);
        this.project = project;
        project.getExtensions().create("timingHunterExtension", TimingHunterExtension.class);
        this.bytecodeWeaver = new TimingWeaver();
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        TimingHunterExtension timingHunterExtension = (TimingHunterExtension) project.getExtensions().getByName("timingHunterExtension");
        bytecodeWeaver.setExtension(timingHunterExtension);
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }

}
