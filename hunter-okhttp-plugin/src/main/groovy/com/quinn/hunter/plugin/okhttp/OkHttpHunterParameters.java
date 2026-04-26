package com.quinn.hunter.plugin.okhttp;

import com.quinn.hunter.transform.HunterParameters;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

public interface OkHttpHunterParameters extends HunterParameters {

    @Input
    Property<Boolean> getWeaveEventListener();
}
