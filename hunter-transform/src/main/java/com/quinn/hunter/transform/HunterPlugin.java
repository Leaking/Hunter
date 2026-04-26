package com.quinn.hunter.transform;

import com.android.build.api.instrumentation.FramesComputationMode;
import com.android.build.api.instrumentation.InstrumentationScope;
import com.android.build.api.variant.AndroidComponentsExtension;
import com.android.build.api.variant.Variant;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Base plugin that hides the AGP 7+ instrumentation boilerplate. Subclasses
 * declare which {@link HunterAsmClassVisitorFactory} should run, scope, and
 * how to populate parameters per variant.
 */
public abstract class HunterPlugin<P extends HunterParameters,
        F extends HunterAsmClassVisitorFactory<P>> implements Plugin<Project> {

    @Override
    public final void apply(Project project) {
        registerExtension(project);
        AndroidComponentsExtension<?, ?, ?> androidComponents =
                project.getExtensions().findByType(AndroidComponentsExtension.class);
        if (androidComponents == null) {
            throw new IllegalStateException(
                    "Hunter plugin '" + getClass().getSimpleName() + "' requires the Android Gradle Plugin"
                            + " to be applied to the project. Make sure 'com.android.application' or"
                            + " 'com.android.library' is applied before this plugin.");
        }
        androidComponents.onVariants(androidComponents.selector().all(), variant -> {
            if (skipVariant(project, variant)) {
                project.getLogger().info(
                        "Hunter [{}] skipped for variant '{}'", getClass().getSimpleName(), variant.getName());
                return;
            }
            Function1<P, Unit> configure = params -> {
                configureParams(project, variant, params);
                return Unit.INSTANCE;
            };
            variant.getInstrumentation().transformClassesWith(
                    getFactoryClass(), getInstrumentationScope(), configure);
            variant.getInstrumentation().setAsmFramesComputationMode(getFramesComputationMode());
        });
    }

    /** Register the user-facing Gradle DSL extension (if any). */
    protected void registerExtension(Project project) {
    }

    /** Return false to instrument this variant, true to skip it. */
    protected boolean skipVariant(Project project, Variant variant) {
        return false;
    }

    /** Wire variant-specific data into the worker parameters. */
    protected void configureParams(Project project, Variant variant, P params) {
        params.getVariantName().set(variant.getName());
    }

    /** AGP scope: {@link InstrumentationScope#ALL} (default) or {@link InstrumentationScope#PROJECT}. */
    protected InstrumentationScope getInstrumentationScope() {
        return InstrumentationScope.ALL;
    }

    /**
     * Frame computation mode. Default: recompute frames only for instrumented
     * methods, matching the legacy Transform API's {@code COMPUTE_MAXS} cost.
     */
    protected FramesComputationMode getFramesComputationMode() {
        return FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS;
    }

    protected abstract Class<F> getFactoryClass();
}
