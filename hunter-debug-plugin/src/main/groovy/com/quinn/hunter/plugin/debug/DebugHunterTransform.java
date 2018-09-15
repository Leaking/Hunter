package com.quinn.hunter.plugin.debug;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.debug.bytecode.DebugWeaver;
import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by quinn on 07/09/2018
 */
public final class DebugHunterTransform extends HunterTransform {

    private Project project;
    private static final LoggerWrapper logger = LoggerWrapper.getLogger(DebugWeaver.class);

    public DebugHunterTransform(Project project) {
        super(project);
        this.project = project;
        project.getExtensions().create("debugHunterExtension", DebugHunterExtension.class);
        this.bytecodeWeaver = new DebugWeaver();
    }

    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        DebugHunterExtension debugHunterExtension = (DebugHunterExtension) project.getExtensions().getByName("debugHunterExtension");
        if(debugHunterExtension != null) {
            logger.info("debugHunterExtension " + debugHunterExtension.toString());
            bytecodeWeaver.setExtension(debugHunterExtension);
        }
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }

}
