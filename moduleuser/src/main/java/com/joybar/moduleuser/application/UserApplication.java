package com.joybar.moduleuser.application;

import android.app.Application;
import android.util.Log;

import com.joybar.librouter.application.ApplicationService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by joybar on 03/12/2017.
 */

public class UserApplication implements ApplicationService {
    private static final String TAG = "UserApplication";
    private static Application application;

    private UserApplication() {
    }

    private static class UserApplicationHolder {
        private static final UserApplication INSTANCE = new UserApplication();
    }

    public static final UserApplication getInstance() {
        return UserApplicationHolder.INSTANCE;
    }

    public Application getMainApplication() {
        if (null != application) {
            return application;
        }
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
        return null;
    }

    @Override
    public void loadApplicationService() {
        Log.d(TAG, "load user application");
    }
}
