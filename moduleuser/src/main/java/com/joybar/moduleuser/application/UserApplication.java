package com.joybar.moduleuser.application;

import android.app.Application;

import com.joybar.modulebase.application.ApplicationService;
import com.joybar.moduleuser.BuildConfig;

/**
 * Created by joybar on 2017/12/4.
 */

public class UserApplication implements ApplicationService {

	private static final String TAG = "UserApplication";

	private UserApplication() {
	}

	private static class UserApplicationHolder {
		private static final UserApplication INSTANCE = new UserApplication();
	}

	public static final UserApplication getInstance() {
		return UserApplicationHolder.INSTANCE;
	}


	@Override
	public void loadModuleApplicationService() {
		if (BuildConfig.IS_DEBUG_TYPE) {
			UserDebugApplication.getInstance().loadModuleApplicationService();
		} else {
			UserReleaseApplication.getInstance().loadModuleApplicationService();
		}
	}

	@Override
	public Application getApplication() {
		if (BuildConfig.IS_DEBUG_TYPE) {
			return UserDebugApplication.getInstance().getApplication();
		} else {
			return UserReleaseApplication.getInstance().getApplication();
		}
	}
}
