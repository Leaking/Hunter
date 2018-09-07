package com.quinn.hunter.plugin;


import com.quinn.hunter.plugin.bytecode.TimingWeaver;
import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

/**
 * Created by quinn on 07/09/2018
 */
public class TimingHunterTransform extends HunterTransform {

    public TimingHunterTransform(Project project) {
        super(project);
        this.bytecodeWeaver = new TimingWeaver();
    }
}
