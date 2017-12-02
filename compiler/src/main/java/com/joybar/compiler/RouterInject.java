package com.joybar.compiler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by joybar on 19/11/2017.
 */

public class RouterInject {




    public static void inject(String classPath) {
        String fullName = classPath.replace(".", "_") + Config.ROUTER_MANAGER_CLASS_NAME_SUFFIX;
        init(fullName);
    }

    public static void init(String className) {
        try {
            className = Config.ROUTER_MANAGER_PKN + "." + className;
            System.out.println("classFullName=" + className);
            Class klass = Class.forName(className);
            Method method = klass.getDeclaredMethod(Config.ROUTER_MANAGER_METHOD_NAME);
            System.out.println("method=" + method);
            method.invoke(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


}
