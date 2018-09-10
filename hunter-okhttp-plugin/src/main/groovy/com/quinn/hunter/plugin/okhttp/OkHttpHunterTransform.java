package com.quinn.hunter.plugin.okhttp;


import com.android.build.gradle.internal.LoggerWrapper;
import com.quinn.hunter.plugin.okhttp.bytecode.OkHttpWeaver;
import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

/**
 * Created by Quinn on 09/09/2018.
 */
public final class OkHttpHunterTransform extends HunterTransform {

    OkHttpHunterTransform(Project project) {
        super(project);
        this.bytecodeWeaver = new OkHttpWeaver();
    }

}
