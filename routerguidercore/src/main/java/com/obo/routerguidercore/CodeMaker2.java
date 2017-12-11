package com.obo.routerguidercore;


import com.obo.routerguidercore.utils.FileUtils;
import com.obo.routerguidercore.utils.StringUtil;
import com.obo.routerguidercore.utils.TypeUtil;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;

public class CodeMaker2 {

	private static final String ANNOTATION_ROUTER = "@RegisterRouter";
	private static final String ANNOTATION_LAUNCH = "@RegisterLaunch";
	private static final String PARAM_PATH = "path";
	private static final String PARAM_MODULE = "module";
	private static final String ROUTER_TABLE_PKN = "com.me.obo.routertable";
	private static final String FILE_PATH = "/routerguider/src/main/java";
	private static final String LOCAL_ROUTE_NAME = "routerGuider";
	private static final String METHOD_PREFIX = "launch";
//    public static void main(String []args) {
//        System.out.println("args.length = " + args.length);
//        autoGenerateModuleMethodName("module_shop");
//    }


	public static void autoGenerateModuleMethodName(String moduleName) {

		System.out.println("==================moduleName=" + moduleName + "==================");

		String classBuilderName = "RouterTable$$" + StringUtil.getTypeWithFirstUpperCase(moduleName) ;
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder(classBuilderName).addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		File workFile = new File(moduleName + "/src/main/java/");
		List<File> files = FileUtils.getAllFiles(workFile);
		System.out.println("files.size() = " + files.size());

		int i = 0;
		for (File file : files) {
			System.out.println("===================" + moduleName + i++ + "==================");
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


	static void decoString(String fileString, TypeSpec.Builder builder) {
		if (fileString.contains(ANNOTATION_ROUTER)) {
			String paramRouterStr = StringUtil.getFirstParamsStrByAnnotation(fileString, ANNOTATION_ROUTER);
			Map<String, String> paramRouterMap = StringUtil.getParamMapByAnnotationParamsStr(paramRouterStr);
			String module = paramRouterMap.get(PARAM_MODULE);
			String path = paramRouterMap.get(PARAM_PATH);
			System.out.println("module = " + module);
			System.out.println("path = " + path);
			List<Map<String, String>>  list = StringUtil.getParamMapListByMethodParamsStr(fileString,ANNOTATION_LAUNCH);
			if(null==list ||list.size()==0){
				MethodSpec methodSpec = getMethodSpecWith(module,path,null);
				builder.addMethod(methodSpec);
			}else{
				for (Map<String, String> paramMap:list){
					MethodSpec methodSpec = getMethodSpecWith(module,path,paramMap);
					builder.addMethod(methodSpec);
				}
			}
		}

	}

	public static MethodSpec getMethodSpecWith(String module,String path,Map<String, String> paramMap){
		ClassName classRouterGuider = ClassName.get("com.me.obo.routerguider", "RouterGuider");
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String methodName = METHOD_PREFIX+StringUtil.upperCaseFirst(StringUtil.underlineToCamel(path));
		MethodSpec.Builder methodBuilder = MethodSpec
				.methodBuilder(methodName)
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.addComment("This class was generated automatically " + sdf.format(d))
				.addComment("module=" + module + "," +	"path=" + path).returns(classRouterGuider);

		methodBuilder.addStatement("$T $N =  new $T($S, $S)", classRouterGuider, LOCAL_ROUTE_NAME,
				classRouterGuider, module, path);

		if(null!=paramMap){
			Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				TypeName type = TypeUtil.getTypeNameWithStr(key);
				System.out.println("--- ");
				System.out.println("key = " + key+",value = " + value+",type = " + type);
				if(null == type){
					continue;
				}
				methodBuilder.addParameter(type, value);
				methodBuilder.addStatement("$N.with" + "" +  StringUtil.getTypeWithFirstUpperCase(key) + "($S, $N)", LOCAL_ROUTE_NAME, value, value);

			}
		}
		methodBuilder.addStatement("return $N", LOCAL_ROUTE_NAME);
		return methodBuilder.build();

	}




}
