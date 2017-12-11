package com.me.obo;


import com.obo.routerguidercore.CodeMaker2;

/**
 * Created by joybar on 02/12/2017.
 */

public class Builder {

    public static void main(String[] args) {
        System.out.println("=============start build=============");
        CodeMaker2.autoGenerateModuleMethodName("moduleshop");
        CodeMaker2.autoGenerateModuleMethodName("moduleuser");
        System.out.println("=============end build=============");
    }

}
