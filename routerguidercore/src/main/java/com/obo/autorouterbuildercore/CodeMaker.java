package com.obo.autorouterbuildercore;


import com.obo.autorouterbuildercore.utils.AnnotationUtil;
import com.obo.autorouterbuildercore.utils.FileUtils;
import com.obo.autorouterbuildercore.utils.StringUtil;
import com.obo.autorouterbuildercore.utils.TypeUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;

public class CodeMaker {

    private static final String ANNOTATION_ROUTE = "@RegisterRouter";
    private static final String ANNOTATION_AUTOWIRED = "@DataParam";
    private static final String ANNOTATION_BIGDATA = "@BigData";
    private static final String PARAM_PATH = "path";
    private static final String PARAM_MODULE = "module";
    private static final String ROUTER_TABLE_PKN = "com.me.obo.routertable";
    private static final String FILE_PATH = "/routerguider/src/main/java";
    private static final String LOCAL_ROUTE_NAME = "routerGuider";

//    public static void main(String []args) {
//        System.out.println("args.length = " + args.length);
//        autoGenerateModuleMethodName("module_shop");
//    }


    public static void autoGenerateModuleMethodName(String moduleName) {

        System.out.println("==================moduleName=" + moduleName+"==================");
        String classBuilderName = "RouterTable$$" + StringUtil.getTypeWithFirstUpperCase(moduleName) ;
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(classBuilderName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        File workFile = new File(moduleName + "/src/main/java/");
        List<File> files = FileUtils.getAllFiles(workFile);
        System.out.println("files.size() = " + files.size());

        for (File file : files) {
            StringBuffer stringBuffer = new StringBuffer("");
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
        JavaFile javaFile = JavaFile.builder(ROUTER_TABLE_PKN, classBuilder.build()).build();
        try {
            javaFile.writeTo(new File(System.getProperty("user.dir") + FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 解析类文件，获取注解和对应的参数
    static void decoString(String fileString, TypeSpec.Builder builder) {
        if (fileString.contains(ANNOTATION_ROUTE)) {
            int index = fileString.indexOf(ANNOTATION_ROUTE);
            String module = "";
            String path = "";
            for (int i = index + ANNOTATION_ROUTE.length(); i < fileString.length(); i++) {
                if (fileString.charAt(i) == ')') {
                    String content = fileString.substring(index + ANNOTATION_ROUTE.length() + 1, i);
                    Map<String, String> annotationParams = AnnotationUtil.getAnnotationParams
                            (content);
                    path = annotationParams.get(PARAM_PATH);
                    module = annotationParams.get(PARAM_MODULE);
                    break;
                }
            }
            System.out.println("module = " + module);
            System.out.println("path = " + path);
            Map<String, String> autowireParamMap = StringUtil.getParamMap(fileString,
                    ANNOTATION_AUTOWIRED);
            Map<String, String> bigValueParamMap = StringUtil.getParamMap(fileString,
                    ANNOTATION_BIGDATA);
            builder.addMethod(getMethodSpecWith(module, path, autowireParamMap, bigValueParamMap));
        }
    }

    // 生成方法
    public static MethodSpec getMethodSpecWith(String module, String path, Map<String, String>
            autowireParamMap, Map<String, String> bigValueParamMap) {

        ClassName classAutoRouter = ClassName.get("com.me.obo.routerguider", "RouterGuider");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("Launch" + StringUtil
                .getTypeWithFirstUpperCase(path)).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addComment("This class was generated automatically "+sdf.format(d))
                .addComment("module="+module + ",path=" + path)
                .returns(classAutoRouter);

        //创建 路由对象
        methodBuilder.addStatement("$T $N =  new $T($S, $S)", classAutoRouter, LOCAL_ROUTE_NAME,
                classAutoRouter, module, path);

        //添加 autowireParam
        for (String key : autowireParamMap.keySet()) {
            TypeName type = TypeUtil.getTypeNameWithStr(autowireParamMap.get(key));
            methodBuilder.addParameter(type, key);
            methodBuilder.addStatement("$N.with" + "" + StringUtil.getTypeWithFirstUpperCase
                    (autowireParamMap.get(key)) + "($S, $N)", LOCAL_ROUTE_NAME, key, key);
        }

        //添加 bigDataParam
        for (String key : bigValueParamMap.keySet()) {
            TypeName type = TypeUtil.getTypeNameWithStr(bigValueParamMap.get(key));
            methodBuilder.addParameter(type, key);
            methodBuilder.addStatement("$N.withBig" + "" + StringUtil.getTypeWithFirstUpperCase
                    (bigValueParamMap.get(key)) + "($S, $N)", LOCAL_ROUTE_NAME, key, key);
        }

        //添加 返回值
        methodBuilder.addStatement("return $N", LOCAL_ROUTE_NAME);
        return methodBuilder.build();
    }

}
