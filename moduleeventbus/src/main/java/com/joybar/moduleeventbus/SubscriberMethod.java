package com.joybar.moduleeventbus;

import java.lang.reflect.Method;

/**
 * Created by joybar on 2017/11/28.
 */

public class SubscriberMethod {
	final Method method;
	final String methodString;
	final Class<?> eventType;

	public SubscriberMethod(Method method, String methodString, Class<?> eventType) {
		this.method = method;
		this.methodString = methodString;
		this.eventType = eventType;
	}


	@Override
	public int hashCode() {
		return method.hashCode() + methodString.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}
		if (obj instanceof SubscriberMethod) {
			SubscriberMethod subscriberMethod = (SubscriberMethod) obj;
			return (methodString.equals(subscriberMethod.methodString));


		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "SubscriberMethod{" + "method=" + method + ", methodString='" + methodString + '\'' + ", eventType=" + eventType + '}';
	}
}
