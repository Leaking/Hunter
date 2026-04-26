package com.quinn.hunter.plugin.debug.bytecode;

import com.quinn.hunter.plugin.debug.bytecode.prego.DebugPreGoClassAdapter;
import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public final class DebugWeaver extends BaseWeaver {

    private static final String PLUGIN_LIBRARY_PREFIX = "com.hunter.library.debug";

    @Override
    public boolean isWeavableClass(String fullyQualifiedClassName) {
        return super.isWeavableClass(fullyQualifiedClassName)
                && !fullyQualifiedClassName.startsWith(PLUGIN_LIBRARY_PREFIX);
    }

    /**
     * Buffers the class as a {@link ClassNode}, then runs the two-pass debug
     * pipeline. Pass one ({@link DebugPreGoClassAdapter}) collects parameter
     * names and the set of methods to instrument; pass two
     * ({@link DebugClassAdapter}) rewrites them.
     *
     * Buffering is required because AGP gives us a single {@link ClassVisitor}
     * chain but the second pass depends on data only the first pass produces.
     */
    @Override
    public ClassVisitor wrap(final ClassVisitor next) {
        return new ClassNode(Opcodes.ASM9) {
            @Override
            public void visitEnd() {
                super.visitEnd();
                DebugPreGoClassAdapter prego = new DebugPreGoClassAdapter(null);
                accept(prego);
                if (prego.isNeedParameter()) {
                    DebugClassAdapter main =
                            new DebugClassAdapter(next, prego.getMethodParametersMap());
                    main.attachIncludeMethodsAndImplMethods(
                            prego.getIncludes(), prego.getImpls());
                    accept(main);
                } else {
                    accept(next);
                }
            }
        };
    }
}
