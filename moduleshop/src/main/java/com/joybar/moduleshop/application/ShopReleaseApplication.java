package com.joybar.moduleshop.application;

import android.app.Application;
import android.util.Log;

import com.joybar.librouter.application.ApplicationService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by joybar on 03/12/2017.
 */

public class ShopReleaseApplication implements ApplicationService {
    private static final String TAG = "ShopReleaseApplication";
    private static Application application;

    private ShopReleaseApplication() {
    }

    private static class ShopReleaseApplicationHolder {
        private static final ShopReleaseApplication INSTANCE = new ShopReleaseApplication();
    }

    public static final ShopReleaseApplication getInstance() {
        return ShopReleaseApplicationHolder.INSTANCE;
    }

    @Override
    public Application getApplication() {
        Log.d(TAG,"get ShopReleaseApplication");
        if (null == application) {
            try {
                Class clazz = Class.forName("com.joybar.androidrouter.App");
                Method method = clazz.getMethod("getInstance");
                application = (Application) method.invoke(null);
                return application;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return application;
    }

    @Override
    public void loadModuleApplicationService() {
        Log.d(TAG, "load shop release application");
    }
}
