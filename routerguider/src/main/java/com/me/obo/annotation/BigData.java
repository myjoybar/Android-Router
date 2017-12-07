package com.me.obo.annotation;

import android.support.annotation.Keep;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by obo on 2017/11/24.
 */
@Keep
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BigData {
    String key() default "";
}
