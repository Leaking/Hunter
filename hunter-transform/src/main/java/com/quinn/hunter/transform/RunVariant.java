package com.quinn.hunter.transform;

import com.android.build.api.variant.Variant;

public enum RunVariant {
    DEBUG, RELEASE, ALWAYS, NEVER;

    /** Whether this filter should skip the given AGP variant. */
    public boolean shouldSkip(Variant variant) {
        String buildType = variant.getBuildType();
        switch (this) {
            case ALWAYS:
                return false;
            case NEVER:
                return true;
            case DEBUG:
                return !"debug".equals(buildType);
            case RELEASE:
                return "debug".equals(buildType);
            default:
                return false;
        }
    }
}
