package com.joybar.androidrouter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.joybar.compiler.RouterInject;
import com.joybar.librouter.Router;
import com.joybar.librouter.Rule;
import com.joybar.librouter.application.ApplicationService;
import com.me.obo.autorouter.AutoRouter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by obo on 2017/11/28.
 */

public class App extends Application implements ApplicationService {

    private static App INSTANCE = null;
    private static final String TAG = "App";

    public static App getInstance() {
        Log.d(TAG,"get application");
        return INSTANCE;
    }

    public static final String[] moduleApplications = {
            "com.joybar.moduleuser.application.UserApplication",
            "com.joybar.moduleshop.application.ShopApplication",
    };

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        INSTANCE = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AutoRouter.inject(this);
        initRouterByAnnotation();
        // initRouterByDynamic();
        // OR
        loadApplicationService();
    }


    private void initRouterByAnnotation() {
        RouterInject.inject("com.joybar.moduleuser.MainActivity");
        RouterInject.inject("com.joybar.moduleshop.MainActivity");
        RouterInject.inject("com.joybar.moduleshop.ReceiveParamActivity");
        RouterInject.inject("com.joybar.moduleshop.FinishWithResultActivity");
        RouterInject.inject("com.joybar.moduleshop.PostModuleDataActivity");
    }

    private void initRouterByDynamic() {
        Router.registerRouters(new Router.RouterTable() {
            @Override
            public List<Rule> buildRuleList() {
                List<Rule> ruleList = new ArrayList<>();
                ruleList.add(new Rule("user", "user_main", com.joybar.moduleuser.MainActivity
                        .class));
                ruleList.add(new Rule("shop", "shop_main", com.joybar.moduleshop.MainActivity
                        .class));
                ruleList.add(new Rule("shop", "shop_receive_param", com.joybar.moduleshop
                        .ReceiveParamActivity.class));
                ruleList.add(new Rule("shop", "shop_receive_param", com.joybar.moduleshop
                        .FinishWithResultActivity.class));
                ruleList.add(new Rule("shop", "shop_post_module_data", com.joybar.moduleshop
                        .PostModuleDataActivity.class));
                return ruleList;
            }


        });
    }


    @Override
    public void loadApplicationService() {

        int size = moduleApplications.length;
        for (int i = 0; i < size; i++) {
            try {
                Class clazz = Class.forName(moduleApplications[i]);
                Method method = clazz.getMethod("getInstance");
                ApplicationService applicationService = (ApplicationService) method.invoke(null);
                applicationService.loadApplicationService();

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


    }
}
