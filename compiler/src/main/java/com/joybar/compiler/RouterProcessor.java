package com.joybar.compiler;

import com.google.auto.service.AutoService;
import com.joybar.annotation.RouterRegister;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import javax.tools.JavaFileObject;

/**
 * Created by joybar on 04/11/2017.
 */
@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Map<RouterModel, String> mStaticRouterMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mStaticRouterMap.clear();
        for (TypeElement element : annotations) {
            if (element.getQualifiedName().toString().equals(RouterRegister.class.getCanonicalName())) {
              //  processRouterMap1(element, roundEnv);

                try {
                    processRouterMap2(element, roundEnv);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(RouterRegister.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    private void processRouterMap1(TypeElement element, RoundEnvironment roundEnv) {
        Set<? extends Element> routerElements = roundEnv.getElementsAnnotatedWith(RouterRegister.class);
        for (Element e : routerElements) {
            if (!(e instanceof TypeElement)) {
                continue;
            }
            TypeElement typeElement = (TypeElement) e;//代表被注解的元素
            String module = typeElement.getAnnotation(RouterRegister.class).module();
            String path = typeElement.getAnnotation(RouterRegister.class).path();
            // Class的完整路径
            String classFullName = typeElement.getQualifiedName().toString();
            System.out.println("module=" + module);
            System.out.println("path=" + path);
            System.out.println("classFullName=" + classFullName);
            if (mStaticRouterMap.get(new RouterModel(module, path)) == null) {
                mStaticRouterMap.put(new RouterModel(module, path), classFullName);
            }
        }
        writeComponentFile();


    }


    private void writeComponentFile() {

        for (Map.Entry<RouterModel, String> entry : mStaticRouterMap.entrySet()) {
            String module = entry.getKey().module;
            String path = entry.getKey().path;
            String className = entry.getValue();
            String createClassName = className.replace(".", "_") + Config.ROUTER_MANAGER_CLASS_NAME_SUFFIX;

            JavaFileObject javaFileObject = null;
            try {
                javaFileObject = mFiler.createSourceFile(createClassName);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(javaFileObject.openWriter());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            printWriter.println("package " + Config.ROUTER_MANAGER_PKN + ";");
            printWriter.println("import android.app.Activity;");
            printWriter.println("import android.app.Service;");
            printWriter.println("import android.content.BroadcastReceiver;");
            printWriter.println("public class " + createClassName + " {");
            printWriter.println("public static void "+Config.ROUTER_MANAGER_METHOD_NAME+"() {");
            printWriter.println("com.joybar.librouter.Router.registerRouter"
                    + "(\"" + module
                    + "\", "
                    + "\"" + path
                    + "\", "
                    + className + ".class" +
                    ");");

            printWriter.println("}");
            printWriter.println("}");
            printWriter.flush();
            printWriter.close();
        }
    }


    private void processRouterMap2(TypeElement element, RoundEnvironment roundEnv) throws IOException {

        Set<? extends Element> routerElements = roundEnv.getElementsAnnotatedWith(RouterRegister.class);
        for (Element e : routerElements) {
            if (!(e instanceof TypeElement)) {
                continue;
            }
            TypeElement typeElement = (TypeElement) e;
            String module =typeElement.getAnnotation(RouterRegister.class).module();
            String path = typeElement.getAnnotation(RouterRegister.class).path();
            String fullName = typeElement.getQualifiedName().toString();

            System.out.println("element.getQualifiedName=" + typeElement.getQualifiedName());
            System.out.println("fullName=" + fullName);
            System.out.println("element.getSimpleName=" + element.getSimpleName());
            System.out.println("module=" + module);
            System.out.println("path=" + path);


            MethodSpec main = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                    .build();
            MethodSpec computeRange = computeRange("multiply10to20", 10, 20, "*");


            MethodSpec addRouter = computeAddRouter(Config.ROUTER_MANAGER_METHOD_NAME,module, path, typeElement.getQualifiedName().toString());
            TypeSpec routerManger = TypeSpec.classBuilder(fullName.replace(".", "_") + Config.ROUTER_MANAGER_CLASS_NAME_SUFFIX)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(main)
                    .addMethod(computeRange)
                    .addMethod(addRouter)
                    .build();
            JavaFile javaFile = JavaFile.builder(Config.ROUTER_MANAGER_PKN, routerManger)
                    .build();
            javaFile.writeTo(mFiler);

        }

    }


    private MethodSpec computeRange(String name, int from, int to, String op) {
        return MethodSpec.methodBuilder(name)
                .returns(int.class)
                .addStatement("int result = 0")
                .beginControlFlow("for (int i = " + from + "; i < " + to + "; i++)")
                .addStatement("result = result " + op + " i")
                .endControlFlow()
                .addStatement("return result")
                .build();
    }

    private MethodSpec computeAddRouter(String methodName, String module, String path, String classFullName) {

        classFullName = classFullName + ".class";

        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addStatement("com.joybar.librouter.Router.registerRouter"
                        + "(\"" + module + "\","
                        + "\"" + path + "\","
                        + classFullName
                        + ")")
                .build();

    }





    public static class RouterModel {
        String module;
        String path;

        public RouterModel(String module, String path) {
            this.module = module;
            this.path = path;
        }

        @Override
        public int hashCode() {
            return path.hashCode() + module.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof RouterModel) {
                RouterModel routerModel = (RouterModel) obj;
                return (path.equals(routerModel.path)
                        && module.equals(routerModel.module));
            }
            return super.equals(obj);
        }
    }
}
