package com.joybar.librouter.routerservice;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.joybar.librouter.routerservice.inters.IBaseService;

import java.util.HashMap;

public class RouterServiceManager {


	private static final String ROUTER_SERVICE_NAME_PREFIX = "RS";
	private Context mContext;
	private HashMap<String, IBaseService> serviceTable = new HashMap<>();


	private static class RouterServiceManagerHolder {
		private static RouterServiceManager INSTANCE = new RouterServiceManager();
	}

	public static RouterServiceManager getInstance() {
		return RouterServiceManagerHolder.INSTANCE;

	}

	public void init(Context context) {
		this.mContext = context;
		registerAllModuleService();
	}


	private void registerAllModuleService() {

		try {
			ApplicationInfo applicationInfo = mContext.getApplicationContext().getPackageManager().getApplicationInfo(mContext.getPackageName(),
					PackageManager.GET_META_DATA);
			for (String key : applicationInfo.metaData.keySet()) {
				if (key.startsWith(ROUTER_SERVICE_NAME_PREFIX)) {
					String componentClassName = applicationInfo.metaData.get(key).toString();
					IBaseService baseService = (IBaseService) Class.forName(componentClassName).newInstance();
					serviceTable.put(key, baseService);
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


	public IBaseService getService(String serviceName){
		if(serviceTable.containsKey(serviceName)){
			return serviceTable.get(serviceName);
		}else{
			throw new IllegalStateException(String.format("%s is not register in serviceTable，have you declared it in AndroidManifest or call the " +
							"registerAllModuleService method in your application",
					serviceName));
		}

	}


}
