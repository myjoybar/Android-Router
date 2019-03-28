package com.joybar.librouter.guider;


import com.joybar.annotation.guider.CodeMaker;

/**
 * Created by joybar on 02/12/2017.
 */

public class Builder {

	public static void main(String[] args) {
		System.out.println("=============start build=============");
		CodeMaker.autoGenerateModuleMethodName("moduleshop");
		CodeMaker.autoGenerateModuleMethodName("moduleuser");
		System.out.println("=============end build=============");
	}

}
