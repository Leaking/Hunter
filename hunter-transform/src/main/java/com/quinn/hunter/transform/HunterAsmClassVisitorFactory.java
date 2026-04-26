package com.quinn.hunter.transform;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.ClassContext;
import com.android.build.api.instrumentation.ClassData;

import com.quinn.hunter.transform.asm.IWeaver;

import org.objectweb.asm.ClassVisitor;

/**
 * Bridges AGP's {@link AsmClassVisitorFactory} contract to Hunter's
 * {@link IWeaver} abstraction so plugin authors can keep writing
 * {@link ClassVisitor}-based weavers as they did under the legacy
 * Transform API.
 *
 * Subclasses must:
 *  - declare a parameters interface extending {@link HunterParameters};
 *  - implement {@link #createWeaver(ClassContext)} returning the weaver.
 *
 * The same factory instance is reused across many {@code isInstrumentable}
 * and {@code createClassVisitor} invocations during a build, so the weaver
 * is lazily cached to avoid re-reading the {@link HunterParameters} for
 * every class.
 */
public abstract class HunterAsmClassVisitorFactory<P extends HunterParameters>
        implements AsmClassVisitorFactory<P> {

    private volatile IWeaver cachedWeaver;

    @Override
    public final ClassVisitor createClassVisitor(ClassContext classContext, ClassVisitor nextClassVisitor) {
        IWeaver weaver = createWeaver(classContext);
        if (weaver == null) {
            return nextClassVisitor;
        }
        return weaver.wrap(nextClassVisitor);
    }

    @Override
    public final boolean isInstrumentable(ClassData classData) {
        IWeaver weaver = getOrCreateWeaver();
        return weaver != null && weaver.isWeavableClass(classData.getClassName());
    }

    /**
     * Create the {@link IWeaver} used to instrument a class. Implementations
     * should be cheap to construct.
     *
     * @param classContext non-null when invoked via {@link #createClassVisitor},
     *                     null when invoked via {@link #isInstrumentable} (where
     *                     it is unavailable).
     */
    protected abstract IWeaver createWeaver(ClassContext classContext);

    private IWeaver getOrCreateWeaver() {
        IWeaver local = cachedWeaver;
        if (local == null) {
            synchronized (this) {
                local = cachedWeaver;
                if (local == null) {
                    local = createWeaver(null);
                    cachedWeaver = local;
                }
            }
        }
        return local;
    }
}
