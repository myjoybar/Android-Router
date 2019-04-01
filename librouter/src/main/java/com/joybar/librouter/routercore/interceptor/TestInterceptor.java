package com.joybar.librouter.routercore.interceptor;


import android.content.Context;

import com.joybar.librouter.routercore.RouterRequest;

/**
 * Created by joybar on 15/11/2017.
 */

public class TestInterceptor implements RouteInterceptor {

	private Context mContext;

	public TestInterceptor(Context context) {
		this.mContext = context;
	}

	@Override
	public boolean isIntercepted(RouterRequest routerRequest) {
		//Toast.makeText(mContext, "I am a test interceptor", Toast.LENGTH_LONG).show();
		return false;
	}
}
