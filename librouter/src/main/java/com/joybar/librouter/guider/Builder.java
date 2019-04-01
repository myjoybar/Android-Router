package com.joybar.librouter.guider;


import com.joybar.annotation.guider.CodeMaker;

/**
 * Created by joybar on 02/12/2017.
 */

public class Builder {

	private static String ROUTER_GUIDER_PKN = "com.joy.testmodule.guider.routertable";
	private static String FILE_PATH = "/app/src/main/java";


	public static void main(String[] args) {
		System.out.println("=============start build=============");
		CodeMaker.autoGenerateModuleMethodName("moduleshop");
		CodeMaker.autoGenerateModuleMethodName("moduleuser");
		System.out.println("=============end build=============");
	}

	/**
	 *
	 * @param moduleName
	 * @param filePath
	 * @param packName
	 */
	public static void buildGuiderJavaFile(String moduleName, String filePath, String packName) {
		CodeMaker.autoGenerateModuleMethodName(moduleName, filePath, packName);
		//CodeMaker.autoGenerateModuleMethodName("moduleshop","/baselib/src/main/java","com.joy.baselib.guider.routertable");
	}

}
