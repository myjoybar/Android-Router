package com.joybar.librouter.routerservice;

public class RouterServiceManager {


	private static class RouterServiceManagerHolder {
		private static RouterServiceManager INSTANCE = new RouterServiceManager();
	}

	public static RouterServiceManager getInstance() {

		return RouterServiceManagerHolder.INSTANCE;

	}

	public void registerAllModuleService() {


	}


	private void getAllModuleService() {

	}


}
