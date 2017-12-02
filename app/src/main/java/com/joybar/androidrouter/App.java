package com.joybar.androidrouter;

import android.app.Application;

import com.joybar.compiler.RouterInject;
import com.joybar.librouter.Router;
import com.joybar.librouter.Rule;
import com.me.obo.inrouter.InRouter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 云山 on 2017/11/28.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        InRouter.inject(this);

        //   initRouter1();
        initRouter2();
    }


    private void initRouter1() {
        RouterInject.inject("com.joybar.moduleuser.MainActivity");
        RouterInject.inject("com.joybar.moduleshop.MainActivity");
    }

    private void initRouter2() {

//        Router.registerRouter("user", "user_main", com.joybar.moduleuser.MainActivity.class);
//        Router.registerRouter("shop", "shop_main", com.joybar.moduleshop.MainActivity.class);

        Router.registerRouters(new Router.RouterTable() {
            @Override
            public List<Rule> buildRuleList() {
                List<Rule> ruleList = new ArrayList<>();
                ruleList.add(new Rule("user", "user_main", com.joybar.moduleuser.MainActivity.class));
                ruleList.add(new Rule("shop", "shop_main", com.joybar.moduleshop.MainActivity.class));
                return ruleList;
            }


        });

    }
}
