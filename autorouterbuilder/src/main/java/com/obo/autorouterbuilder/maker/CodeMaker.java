package com.obo.autorouterbuilder.maker;


import com.obo.autorouterbuilder.maker.utils.AnnotationUtil;
import com.obo.autorouterbuilder.maker.utils.FileUtils;
import com.obo.autorouterbuilder.maker.utils.StringUtil;
import com.obo.autorouterbuilder.maker.utils.TypeUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;

public class CodeMaker {

    private static final String ANNO_ROUTE = "@RouterRegister";
    private static final String ANNNO_AUTOWIRED = "@DataParam";
    private static final String ANNNO_BIGDATA = "@BigData";
    private static final String PARAM_PATH = "path";
    private static final String PARAM_MODULE = "module";

    private static final String FILE_PATH = "/inrouter/src/main/java";

    public static void main(String []args) {
        System.out.println("args.length = " + args.length);
        autoGenerateModuleMethodName("module_shop");
    }


    public static void  autoGenerateModuleMethodName(String moduleName){

        System.out.println("moduleName = " + moduleName + " destinationPath = ");

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder("RouteMap$$" + moduleName).addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        File workFile = new File(moduleName + "/src/main/java/");
        List<File> files = FileUtils.getAllFiles(workFile);
        System.out.println("files.size() = " + files.size());

        for (File file: files) {
            StringBuffer stringBuffer=new StringBuffer("");
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    stringBuffer.append(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            decoString(stringBuffer.toString(), classBuilder);
        }
        JavaFile javaFile = JavaFile.builder("com.me.obo.map", classBuilder.build())
                .build();
        try {
            javaFile.writeTo(new File(System.getProperty("user.dir") + FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 解析类文件，获取注解和对应的参数
    static void decoString(String fileString, TypeSpec.Builder builder) {
        if (fileString.contains(ANNO_ROUTE)) {
            int index = fileString.indexOf(ANNO_ROUTE);
            String module = "";
            String path = "";
            for (int i = index + ANNO_ROUTE.length(); i < fileString.length(); i++) {
                if (fileString.charAt(i) == ')') {
                    String content = fileString.substring(index + ANNO_ROUTE.length() + 1, i);
                    Map<String, String>annotationParams = AnnotationUtil.getAnnotationParams(content);
                    path = annotationParams.get(PARAM_PATH);
                    module = annotationParams.get(PARAM_MODULE);
                    break;
                }
            }
            System.out.println("module = " + module);
            System.out.println("path = " + path);
            Map<String, String> autowireParamMap = StringUtil.getParamMap(fileString, ANNNO_AUTOWIRED);
            Map<String, String> bigValueParamMap = StringUtil.getParamMap(fileString, ANNNO_BIGDATA);
            builder.addMethod(getMethodSpecWith(module, path, autowireParamMap, bigValueParamMap));
        }
    }

    // 生成方法
    public static MethodSpec getMethodSpecWith(String module, String path, Map<String, String> autowireParamMap, Map<String, String> bigValueParamMap) {
        final String LOCAL_ROUTE_NAME = "inrouter";
        ClassName classInRouter = ClassName.get("com.me.obo.inrouter", "InRouter");
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("GoTo" + StringUtil.getTypeWithFirstUpperCase( path))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addComment("module="+module+",Path = " + path)
                .returns(classInRouter);

        //创建 路由对象
        methodBuilder.addStatement("$T $N =  new $T($S, $S)", classInRouter, LOCAL_ROUTE_NAME, classInRouter, module, path);

        //添加 autowireParam
        for (String key : autowireParamMap.keySet()) {
            TypeName type = TypeUtil.getTypeNameWithStr(autowireParamMap.get(key));
            methodBuilder.addParameter(type, key);
            methodBuilder.addStatement("$N.with" + "" + StringUtil.getTypeWithFirstUpperCase(autowireParamMap.get(key))+"($S, $N)", LOCAL_ROUTE_NAME, key, key);
        }

        //添加 bigDataParam
        for (String key : bigValueParamMap.keySet()) {
            TypeName type = TypeUtil.getTypeNameWithStr(bigValueParamMap.get(key));
            methodBuilder.addParameter(type, key);
            methodBuilder.addStatement("$N.withBig" + "" + StringUtil.getTypeWithFirstUpperCase(bigValueParamMap.get(key))+"($S, $N)", LOCAL_ROUTE_NAME, key, key);
        }

        //添加 返回值
        methodBuilder.addStatement("return $N", LOCAL_ROUTE_NAME);
        return methodBuilder.build();
    }

}
