package com.quinn.hunter.plugin.timing;

import com.quinn.hunter.transform.HunterParameters;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.tasks.Input;

public interface TimingHunterParameters extends HunterParameters {

    @Input
    ListProperty<String> getWhitelist();

    @Input
    ListProperty<String> getBlacklist();
}
