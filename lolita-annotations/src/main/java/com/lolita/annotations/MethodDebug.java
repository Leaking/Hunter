package com.lolita.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by Quinn on 19/03/2017.
 */

@Target({TYPE, METHOD, CONSTRUCTOR}) @Retention(CLASS)
public @interface MethodDebug {
}
