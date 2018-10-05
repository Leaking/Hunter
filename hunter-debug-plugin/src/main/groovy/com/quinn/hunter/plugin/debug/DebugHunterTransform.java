package com.quinn.hunter.plugin.debug;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.debug.bytecode.DebugWeaver;
import com.quinn.hunter.transform.HunterTransform;
import com.quinn.hunter.transform.RunVariant;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Quinn on 16/09/2018.
 */
public class DebugHunterTransform extends HunterTransform {

    private Project project;
    private DebugHunterExtension debugHunterExtension;

    public DebugHunterTransform(Project project) {
        super(project);
        this.project = project;
        project.getExtensions().create("debugHunterExtension", DebugHunterExtension.class);
        this.bytecodeWeaver = new DebugWeaver();
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        debugHunterExtension = (DebugHunterExtension) project.getExtensions().getByName("debugHunterExtension");
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }


    @Override
    protected RunVariant getRunVariant() {
        if(!debugHunterExtension.enable) {
            return RunVariant.NONE;
        } else {
            return RunVariant.DEBUG;
        }
    }
}