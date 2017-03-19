package com.lolita.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Quinn on 14/03/2017.
 */

@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
public @interface Data {
}
