package com.joybar.moduleshop.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.joybar.librouter.application.ApplicationService;

/**
 * Created by joybar on 2017/12/4.
 */

public class ShopDebugApplication extends Application implements ApplicationService {


	private static final String TAG = "ShopDebugApplication";
	private static ShopDebugApplication INSTANCE = null;

	public static ShopDebugApplication getInstance() {
		Log.d(TAG,"get ShopDebugApplication");
		return INSTANCE;
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		INSTANCE = this;
	}


	@Override
	public Application getApplication() {
		return getInstance();
	}

	@Override
	public void loadModuleApplicationService() {
		Log.d(TAG, "load shop debug application");
	}
}
