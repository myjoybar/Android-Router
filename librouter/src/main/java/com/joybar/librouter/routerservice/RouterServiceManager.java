package com.joybar.librouter.routerservice;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.joybar.librouter.routerservice.inters.IBaseService;

import java.util.HashMap;

public class RouterServiceManager {


	private Context context;
	private HashMap<String, IBaseService> serviceMap = new HashMap<>();

	private static class RouterServiceManagerHolder {
		private static RouterServiceManager INSTANCE = new RouterServiceManager();
	}

	public static RouterServiceManager getInstance() {

		return RouterServiceManagerHolder.INSTANCE;

	}

	public void init(Context context) {

		this.context = context;

		registerAllModuleService();
	}



	private void registerAllModuleService() {

		try {
			ApplicationInfo applicationInfo = context.getApplicationContext().getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

			for (String key : applicationInfo.metaData.keySet()) {

				if (key.startsWith("DT")) {

					String componentClassName = applicationInfo.metaData.get(key).toString();

					IBaseService baseService = (IBaseService) Class.forName(componentClassName).newInstance();


					serviceMap.put(key, baseService);
				}
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}


	public IBaseService getService(String serviceName) {

		return serviceMap.get(serviceName);
	}


}
