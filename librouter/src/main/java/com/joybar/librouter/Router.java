package com.joybar.librouter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.joybar.librouter.utils.CheckUtils;

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

	public static IRouterManagerService with(@Nullable Context context) {
		CheckUtils.checkNotNull(context);
		RouterService routerService = new RouterService(context);
		return routerService;
	}

}
