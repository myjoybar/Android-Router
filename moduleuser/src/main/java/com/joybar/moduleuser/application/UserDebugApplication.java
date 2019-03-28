package com.joybar.moduleuser.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.joybar.librouter.application.ApplicationService;


/**
 * Created by joybar on 2017/12/4.
 */

public class UserDebugApplication extends Application implements ApplicationService {


	private static final String TAG = "UserDebugApplication";
	private static UserDebugApplication INSTANCE = null;

	public static UserDebugApplication getInstance() {
		Log.d(TAG,"get UserDebugApplication");
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
		Log.d(TAG, "load user application");
	}
}
