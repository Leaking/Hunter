package com.quinn.hunter.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Quinn on 25/02/2017.
 */

public class HunterPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create('hunterExt', HunterExtension)
        project.android.registerTransform(new TimingHunterTransform(project))
    }

}
