package com.me.obo;

import com.obo.autorouterbuilder.maker.CodeMaker;

/**
 * Created by joybar on 02/12/2017.
 */

public class Builder {

    public static void main(String[] args) {
        System.out.println("start");
        CodeMaker.autoGenerateModuleMethodName("moduleshop");
        CodeMaker.autoGenerateModuleMethodName("moduleuser");
    }

}
