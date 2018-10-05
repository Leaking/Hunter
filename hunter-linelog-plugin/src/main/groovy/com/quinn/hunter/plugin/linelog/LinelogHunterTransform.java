package com.quinn.hunter.plugin.linelog;

import com.quinn.hunter.plugin.linelog.bytecode.LinelogWeaver;
import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;


/**
 * Created by Quinn on 15/09/2018.
 */
public final class LinelogHunterTransform extends HunterTransform {

    public LinelogHunterTransform(Project project) {
        super(project);
        this.bytecodeWeaver = new LinelogWeaver();
    }

}
