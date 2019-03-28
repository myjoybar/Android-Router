package com.joybar.compiler.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by joybar on 19/11/2017.
 */

public class RouterInject {

	public static void registerModule(String moduleName) {
		String className = CompilerHelper.ROUTER_MANAGER_TABLE_CLASS_NAME + moduleName;
		try {
			className = CompilerHelper.ROUTER_MANAGER_TABLE_PKN + "." + className;
			System.out.println("registerModule classFullName=" + className);
			Class clazz = Class.forName(className);
			Method method = clazz.getDeclaredMethod(CompilerHelper.ROUTER_MANAGER_TABLE_METHOD_NAME);
			System.out.println("method=" + method);
			method.invoke(null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}


	public static void inject(String classPath) {
		String className = CompilerHelper.getCombineClassFullName(classPath) + CompilerHelper.ROUTER_MANAGER_CLASS_NAME_SUFFIX;
		try {
			className = CompilerHelper.ROUTER_MANAGER_PKN + "." + className;
			System.out.println("inject classFullName=" + className);
			Class clazz = Class.forName(className);
			Method method = clazz.getDeclaredMethod(CompilerHelper.ROUTER_MANAGER_METHOD_NAME);
			System.out.println("method=" + method);
			method.invoke(null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}




}
