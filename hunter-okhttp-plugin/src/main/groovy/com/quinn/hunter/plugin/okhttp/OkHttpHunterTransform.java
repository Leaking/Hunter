package com.quinn.hunter.plugin.okhttp;


import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.okhttp.bytecode.OkHttpWeaver;
import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

/**
 * Created by quinn on 07/09/2018
 */
public class OkHttpHunterTransform extends HunterTransform {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(OkHttpHunterTransform.class);

    private Project project;

    public OkHttpHunterTransform(Project project) {
        super(project);
        this.project = project;
        this.bytecodeWeaver = new OkHttpWeaver();
    }


}
