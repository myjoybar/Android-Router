package com.joybar.compiler.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by joybar on 19/11/2017.
 */

public class CompilerHelper {
	public static final String ROUTER_MANAGER_PKN = "com.joybar.routermanager.helper";
	public static final String ROUTER_MANAGER_CLASS_NAME_SUFFIX = "$RouterRegister";
	public static final String ROUTER_MANAGER_METHOD_NAME = "registerRouter";
	public static final String CLASS_SUFFIX = ".class";
	public static final String ROUTER = " com.joybar.librouter.routercore.Router.registerRouter";
	public static final String COMMENT = "This class was generated automatically ";

	public static final String ROUTER_MANAGER_TABLE_PKN = "com.joybar.routermanager.helper.table";
	public static final String ROUTER_MANAGER_TABLE_CLASS_NAME = "RouterTable_";
	public static final String ROUTER_MANAGER_TABLE_METHOD_NAME = "createRouterTable";
	public static final String ROUTER_MANAGER_TABLE_ROUTERINJECT= "com.joybar.compiler.helper.RouterInject.inject";


	public static String getCombineClassFullName(String classFullName){
		return classFullName.replace(".", "_");
	}

	public static String getCommentInfo() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return COMMENT + sdf.format(d);
	}

	public static void autoRegister(String className){
		RouterInject.inject(className);
	}





}
