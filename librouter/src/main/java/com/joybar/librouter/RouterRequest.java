package com.joybar.librouter;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by joybar on 12/11/2017.
 */

public class RouterRequest {

	private Rule rule;
	private InterceptorCallback interceptorCallback;
	private Bundle bundle;
	private byte [] bytes;


	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public InterceptorCallback getInterceptorCallback() {
		return interceptorCallback;
	}

	public void setInterceptorCallback(InterceptorCallback interceptorCallback) {
		this.interceptorCallback = interceptorCallback;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
