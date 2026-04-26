package com.quinn.hunter.transform;

import com.android.build.api.instrumentation.InstrumentationParameters;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * Common Gradle task parameters shared by every Hunter ASM visitor factory.
 * Subclass this for plugin-specific parameters; AGP serializes the values
 * into the worker process that runs the bytecode transform.
 */
public interface HunterParameters extends InstrumentationParameters {

    @Input
    @Optional
    Property<String> getVariantName();
}
