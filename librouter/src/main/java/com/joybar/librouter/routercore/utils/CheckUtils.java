package com.joybar.librouter.routercore.utils;


/**
 * Created by joybar on 2017/5/3.
 */

public class CheckUtils {
	public static <T> T checkNotNull(T reference) {
		if (reference == null) {
			throw new NullPointerException();
		} else {
			return reference;
		}
	}

	public static <T> T checkNotNull(T reference, Object errorMessage) {
		if (reference == null) {
			throw new NullPointerException(String.valueOf(errorMessage));
		} else {
			return reference;
		}
	}

}
