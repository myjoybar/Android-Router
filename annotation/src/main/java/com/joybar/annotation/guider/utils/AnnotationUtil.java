package com.joybar.annotation.guider.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by obo on 2017/11/28.
 */

public class AnnotationUtil {
	public static Map<String, String> getAnnotationParams(String annotationContent) {
		Map<String, String> annotationParams = new HashMap<>();
		String[] paramSplites = annotationContent.split(",");
		for (String param : paramSplites) {
			String[] annotationKeyValueSplites = param.split("=");
			if (annotationKeyValueSplites.length == 2) {
				String key = annotationKeyValueSplites[0].trim();
				String value = annotationKeyValueSplites[1].trim().replace("\"", "");
				annotationParams.put(key, value);
			}
		}
		return annotationParams;
	}
}
