package com.hunter.library.debug;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by quinn on 17/09/2018
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR}) @Retention(RetentionPolicy.CLASS)
public @interface HunterDebug {
}