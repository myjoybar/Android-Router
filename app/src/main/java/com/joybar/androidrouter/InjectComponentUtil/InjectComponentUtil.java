package com.joybar.androidrouter.InjectComponentUtil;


import com.joybar.annotationprocessors.Config;
import com.joybar.annotationprocessors.RouterHelper;

/**
 * Created by joybar on 05/11/2017.
 */

public class InjectComponentUtil {

    public static void inject(String classPath) {
        String fullName = classPath.replace(".", "_")+ Config.ROUTER_MANAGER_CLASS_NAME_SUFFIX;
        RouterHelper.Init(fullName);
    }


}
