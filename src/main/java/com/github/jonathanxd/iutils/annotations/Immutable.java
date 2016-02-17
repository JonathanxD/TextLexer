package com.github.jonathanxd.iutils.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by jonathan on 17/02/16.
 */

/**
 * Indicates that the element is Immutable (like Lists)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Immutable {
}
