package com.joybar.androidrouter;

import android.app.Application;

import com.joybar.compiler.RouterInject;
import com.joybar.librouter.Router;
import com.joybar.librouter.Rule;
import com.me.obo.autorouter.AutoRouter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 云山 on 2017/11/28.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AutoRouter.inject(this);
        initRouterByAnnotation();
        // initRouterByDynamic();
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
                ruleList.add(new Rule("user", "user_main", com.joybar.moduleuser.MainActivity.class));
                ruleList.add(new Rule("shop", "shop_main", com.joybar.moduleshop.MainActivity.class));
                ruleList.add(new Rule("shop", "shop_receive_param", com.joybar.moduleshop.ReceiveParamActivity.class));
                ruleList.add(new Rule("shop", "shop_receive_param", com.joybar.moduleshop.FinishWithResultActivity.class));
                ruleList.add(new Rule("shop", "shop_post_module_data", com.joybar.moduleshop.PostModuleDataActivity.class));
                return ruleList;
            }


        });
    }



}
