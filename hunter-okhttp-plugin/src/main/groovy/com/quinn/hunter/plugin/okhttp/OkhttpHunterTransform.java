package com.quinn.hunter.plugin.okhttp;


import com.android.build.api.transform.Context;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.okhttp.bytecode.OkHttpWeaver;
import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by quinn on 07/09/2018
 */
public class OkhttpHunterTransform extends HunterTransform {

    private static final LoggerWrapper logger = LoggerWrapper.getLogger(OkhttpHunterTransform.class);

    private Project project;

    public OkhttpHunterTransform(Project project) {
        super(project);
        this.project = project;
        this.bytecodeWeaver = new OkHttpWeaver();
    }


}
