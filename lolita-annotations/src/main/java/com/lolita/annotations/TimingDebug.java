package com.lolita.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by susan_sfy on 3/26/17.
 */

@Target({METHOD, CONSTRUCTOR}) @Retention(CLASS)
public @interface TimingDebug {
}
