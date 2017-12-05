package com.me.obo.routertable;

import com.me.obo.autorouter.AutoRouter;
import java.lang.Integer;
import java.lang.String;

public final class RouterTable$$moduleshop {
  public static AutoRouter GoToShop_finish_with_result() {
    // This class was generated automatically 2017-12-05 11:21:25
    // module=shop,path=shop_finish_with_result
    AutoRouter autoRouterBuilder =  new AutoRouter("shop", "shop_finish_with_result");
    return autoRouterBuilder;
  }

  public static AutoRouter GoToShop_main() {
    // This class was generated automatically 2017-12-05 11:21:25
    // module=shop,path=shop_main
    AutoRouter autoRouterBuilder =  new AutoRouter("shop", "shop_main");
    return autoRouterBuilder;
  }

  public static AutoRouter GoToShop_post_module_data() {
    // This class was generated automatically 2017-12-05 11:21:25
    // module=shop,path=shop_post_module_data
    AutoRouter autoRouterBuilder =  new AutoRouter("shop", "shop_post_module_data");
    return autoRouterBuilder;
  }

  public static AutoRouter GoToShop_receive_param(String name, Integer id) {
    // This class was generated automatically 2017-12-05 11:21:25
    // module=shop,path=shop_receive_param
    AutoRouter autoRouterBuilder =  new AutoRouter("shop", "shop_receive_param");
    autoRouterBuilder.withString("name", name);
    autoRouterBuilder.withInt("id", id);
    return autoRouterBuilder;
  }
}
