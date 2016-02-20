package com.github.jonathanxd.textlexer.ext.parser.processor.standard.options;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jonathan on 19/02/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface OptionSupport {

    Class<?>[] value();

    String description() default "";
}
