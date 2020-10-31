package com.quinn.hunter.plugin.timing;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.quinn.hunter.plugin.timing.bytecode.TimingWeaver;
import com.quinn.hunter.transform.HunterTransform;
import com.quinn.hunter.transform.RunVariant;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by quinn on 07/09/2018
 */
public final class TimingHunterTransform extends HunterTransform {

    private Project project;
    private TimingHunterExtension timingHunterExtension;

    public TimingHunterTransform(Project project) {
        super(project);
        this.project = project;
        project.getExtensions().create("timingHunterExt", TimingHunterExtension.class);
        this.bytecodeWeaver = new TimingWeaver();
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        timingHunterExtension = (TimingHunterExtension) project.getExtensions().getByName("timingHunterExt");
        bytecodeWeaver.setExtension(timingHunterExtension);
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }

    protected RunVariant getRunVariant() {
        return timingHunterExtension.runVariant;
    }

    @Override
    protected boolean inDuplicatedClassSafeMode() {
        return timingHunterExtension.duplicatedClassSafeMode;
    }
}
