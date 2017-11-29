package com.joybar.librouter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joybar on 2017/11/13.
 */

public class Router {
	public static Map<Rule, Rule> ruleMap = new HashMap<>();

	public static void registerRouter(String module,String path,Class klass) {
		Rule rule = new Rule(module,path,klass);
		ruleMap.put(rule,rule);
	}

	public static IRouterManagerService getRouterService() {
		RouterService routerService = new RouterService();
		return routerService;
	}

}
