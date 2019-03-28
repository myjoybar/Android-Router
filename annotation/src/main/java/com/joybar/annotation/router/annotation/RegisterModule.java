package com.joybar.annotation.router.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.CLASS)
public @interface RegisterModule {
	String[] moduleNames();
}
