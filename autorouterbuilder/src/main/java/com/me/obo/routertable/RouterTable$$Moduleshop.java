package com.me.obo.routertable;

import com.me.obo.routerguider.AutoRouter;
import java.lang.Long;
import java.lang.String;

public final class RouterTable$$Moduleshop {
  public static AutoRouter LaunchShop_finish_with_result() {
    // This class was generated automatically 2017-12-07 22:03:02
    // module=shop,path=shop_finish_with_result
    AutoRouter autoRouterBuilder =  new AutoRouter("shop", "shop_finish_with_result");
    return autoRouterBuilder;
  }

  public static AutoRouter LaunchShop_main() {
    // This class was generated automatically 2017-12-07 22:03:02
    // module=shop,path=shop_main
    AutoRouter autoRouterBuilder =  new AutoRouter("shop", "shop_main");
    return autoRouterBuilder;
  }

  public static AutoRouter LaunchShop_post_module_data() {
    // This class was generated automatically 2017-12-07 22:03:02
    // module=shop,path=shop_post_module_data
    AutoRouter autoRouterBuilder =  new AutoRouter("shop", "shop_post_module_data");
    return autoRouterBuilder;
  }

  public static AutoRouter LaunchShop_receive_param(String address, Long number) {
    // This class was generated automatically 2017-12-07 22:03:02
    // module=shop,path=shop_receive_param
    AutoRouter autoRouterBuilder =  new AutoRouter("shop", "shop_receive_param");
    autoRouterBuilder.withString("address", address);
    autoRouterBuilder.withLong("number", number);
    return autoRouterBuilder;
  }
}
