package com.joybar.androidrouter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.joybar.compiler.RouterInject;
import com.joybar.librouter.*;
import com.joybar.librouter.application.ApplicationService;
import com.joybar.moduleshop.application.ShopApplication;
import com.joybar.moduleuser.application.UserReleaseApplication;
import com.me.obo.routerguider.RouterGuider;

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        INSTANCE = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RouterGuider.inject(this);
        initRouterByAnnotation();
        // OR
        // initRouterByDynamic();

        loadModuleApplicationService();
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
                ruleList.add(new Rule("user", "main", com.joybar.moduleuser.MainActivity
                        .class));
                ruleList.add(new Rule("shop", "main", com.joybar.moduleshop.MainActivity
                        .class));
                ruleList.add(new Rule("shop", "receive_param", com.joybar.moduleshop
                        .ReceiveParamActivity.class));
                ruleList.add(new Rule("shop", "receive_param", com.joybar.moduleshop
                        .FinishWithResultActivity.class));
                ruleList.add(new Rule("shop", "post_module_data", com.joybar.moduleshop
                        .PostModuleDataActivity.class));
                return ruleList;
            }


        });
    }


    @Override
    public void loadModuleApplicationService() {
        UserReleaseApplication.getInstance().loadModuleApplicationService();
        ShopApplication.getInstance().loadModuleApplicationService();
    }

    @Override
    public Application getApplication() {
        return getInstance();
    }

}
