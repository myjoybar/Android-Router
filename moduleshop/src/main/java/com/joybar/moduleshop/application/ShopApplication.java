package com.joybar.moduleshop.application;

import android.app.Application;

import com.joybar.modulebase.application.ApplicationService;
import com.joybar.moduleshop.BuildConfig;

/**
 * Created by joybar on 2017/12/4.
 */

public class ShopApplication implements ApplicationService {

	private static final String TAG = "UserApplication";

	private ShopApplication() {
	}

	private static class UserApplicationHolder {
		private static final ShopApplication INSTANCE = new ShopApplication();
	}

	public static final ShopApplication getInstance() {
		return UserApplicationHolder.INSTANCE;
	}


	@Override
	public void loadModuleApplicationService() {
		if (BuildConfig.IS_DEBUG_TYPE) {
			ShopDebugApplication.getInstance().loadModuleApplicationService();
		} else {
			ShopReleaseApplication.getInstance().loadModuleApplicationService();
		}
	}

	@Override
	public Application getApplication() {
		if (BuildConfig.IS_DEBUG_TYPE) {
			return ShopDebugApplication.getInstance().getApplication();
		} else {
			return ShopReleaseApplication.getInstance().getApplication();
		}
	}
}
