package com.joybar.compiler;

import com.google.auto.service.AutoService;
import com.joybar.annotation.router.annotation.RegisterModule;
import com.joybar.annotation.router.annotation.RegisterRouter;
import com.joybar.compiler.data.RouterModule;
import com.joybar.compiler.helper.CompilerHelper;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by joybar on 04/11/2017.
 */
@AutoService(Processor.class)
public class RegisterRouterProcessor extends AbstractProcessor {
	private Filer mFiler;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		mFiler = processingEnv.getFiler();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (TypeElement element : annotations) {
			if (element.getQualifiedName().toString().equals(RegisterRouter.class.getCanonicalName())) {
				handleRegisterRouter(roundEnv);
			}
		}


		return false;
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> set = new HashSet<>();
		set.add(RegisterRouter.class.getCanonicalName());
		set.add(RegisterModule.class.getCanonicalName());
		return set;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	private String[] handleRegisterModules(RoundEnvironment roundEnv) {
		Set<? extends Element> registerModuleSet = roundEnv.getElementsAnnotatedWith(RegisterModule.class);
		String[] moduleNames = null;
		if (registerModuleSet != null && registerModuleSet.size() > 0) {
			Element modules = registerModuleSet.iterator().next();
			moduleNames = modules.getAnnotation(RegisterModule.class).moduleNames();
		}
		return moduleNames;
	}

	private void handleRegisterRouter(RoundEnvironment roundEnv) {
		System.out.println("--------start handleRegisterRouter--------");
		List<RouterModule> routerModuleList = new ArrayList<>(0);
		Set<? extends Element> registerRouterSet = roundEnv.getElementsAnnotatedWith(RegisterRouter.class);
		for (Element e : registerRouterSet) {
			if (!(e instanceof TypeElement)) {
				continue;
			}
			TypeElement typeElement = (TypeElement) e;

			String module = typeElement.getAnnotation(RegisterRouter.class).module();
			module = module == null ? "" : module;
			String path = typeElement.getAnnotation(RegisterRouter.class).path();
			path = path == null ? "" : path;
			boolean isAutoRegistered = typeElement.getAnnotation(RegisterRouter.class).isAutoRegistered();
			String classFullName = typeElement.getQualifiedName().toString();
			System.out.println("fullName=" + classFullName + ", SimpleName=" + typeElement.getSimpleName() + ", module=" + module + ", path=" + path);
			generateRouterRegisterClass(module, path, classFullName);
			RouterModule routerModule = new RouterModule(classFullName, module, path, isAutoRegistered);
			routerModuleList.add(routerModule);
		}

		generateRouterRule(routerModuleList);

	}

	private void generateRouterRegisterClass(String module, String path, String classFullName) {
		try {
			String pkgName = CompilerHelper.ROUTER_MANAGER_PKN;
			String className = CompilerHelper.getCombineClassFullName(classFullName) + CompilerHelper.ROUTER_MANAGER_CLASS_NAME_SUFFIX;
			String methodName = CompilerHelper.ROUTER_MANAGER_METHOD_NAME;
			MethodSpec registerRouter = computeAddRouter(methodName, module, path, classFullName);

			TypeSpec routerManger = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC).addMethod(registerRouter).build();
			JavaFile javaFile = JavaFile.builder(pkgName, routerManger).build();
			javaFile.writeTo(mFiler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateRouterRule(List<RouterModule> moduleList) {
		System.out.println("moduleList = " + Arrays.toString(moduleList.toArray()));
		try {
			String pkgName = CompilerHelper.ROUTER_MANAGER_TABLE_PKN;
			String className = CompilerHelper.ROUTER_MANAGER_TABLE_CLASS_NAME + moduleList.get(0).module;
			String methodName = CompilerHelper.ROUTER_MANAGER_TABLE_METHOD_NAME;
			MethodSpec registerRouter = computeRouterTable(methodName, moduleList);

			TypeSpec routerManger = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC).addMethod(registerRouter).build();
			JavaFile javaFile = JavaFile.builder(pkgName, routerManger).build();
			javaFile.writeTo(mFiler);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private MethodSpec computeRouterTable(String methodName, List<RouterModule> routerModuleList) {

		MethodSpec.Builder builder =
				MethodSpec.methodBuilder(methodName).addModifiers(Modifier.PUBLIC, Modifier.STATIC).addComment(CompilerHelper.getCommentInfo()).returns(void.class);
		for (int i = 0; i < routerModuleList.size(); i++) {
			builder.addStatement(CompilerHelper.ROUTER_MANAGER_TABLE_ROUTERINJECT + "(\"" + routerModuleList.get(i).classFullName + "\"" + ")");
		}
		return builder.build();

	}


	private MethodSpec computeAddRouter(String methodName, String module, String path, String classFullName) {
		return MethodSpec.methodBuilder(methodName).addModifiers(Modifier.PUBLIC, Modifier.STATIC).addComment(CompilerHelper.getCommentInfo()).returns(void.class).addStatement(CompilerHelper.ROUTER + "(\"" + module + "\"," + "\"" + path + "\"," + classFullName + CompilerHelper.CLASS_SUFFIX + ")").build();

	}


}
