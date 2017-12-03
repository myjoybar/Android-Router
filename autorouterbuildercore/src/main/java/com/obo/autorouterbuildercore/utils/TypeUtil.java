package com.obo.autorouterbuildercore.utils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

/**
 * Created by obo on 2017/11/27.
 */

public class TypeUtil {
    public static final String BOOL = "boolean";
    public static final String INT = "int";
    public static final String BYTE = "byte";
    public static final String SHORT = "short";
    public static final String VOID = "void";
    public static final String LONG = "long";
    public static final String CHAR = "char";
    public static final String FLOAT = "float";
    public static final String DOUBLE = "double";
    public static final String BITMAP = "Bitmap";


    public static final String STRING = "String";
    public static final String OBJECT = "Object";



    public static TypeName getTypeNameWithStr(String typeStr) {
        TypeName typeName = null;
        switch (typeStr) {
            case BOOL:
            case BYTE:
            case SHORT:
            case VOID:
            case LONG:
            case CHAR:
            case FLOAT:
            case DOUBLE:
                typeName = ClassName.get("java.lang", StringUtil.getTypeWithFirstUpperCase(typeStr));
                break;
            case INT:
                typeName = ClassName.get("java.lang", "Integer");
                break;
            case STRING:
                typeName = ClassName.get("java.lang", StringUtil.getTypeWithFirstUpperCase(typeStr));
                break;
            case BITMAP:
                typeName = ClassName.get("android.graphics", "Bitmap");
                break;
        }
        return typeName;
    }
}
