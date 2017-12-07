package com.me.obo.routertable;

import com.me.obo.routerguider.RouterGuider;
import java.lang.Integer;
import java.lang.String;

public final class RouterTable$$Moduleshop {
  public static RouterGuider LaunchShop_finish_with_result() {
    // This class was generated automatically 2017-12-07 22:10:42
    // module=shop,path=shop_finish_with_result
    RouterGuider routerGuider =  new RouterGuider("shop", "shop_finish_with_result");
    return routerGuider;
  }

  public static RouterGuider LaunchShop_main() {
    // This class was generated automatically 2017-12-07 22:10:42
    // module=shop,path=shop_main
    RouterGuider routerGuider =  new RouterGuider("shop", "shop_main");
    return routerGuider;
  }

  public static RouterGuider LaunchShop_post_module_data() {
    // This class was generated automatically 2017-12-07 22:10:42
    // module=shop,path=shop_post_module_data
    RouterGuider routerGuider =  new RouterGuider("shop", "shop_post_module_data");
    return routerGuider;
  }

  public static RouterGuider LaunchShop_receive_param(String name, Integer id) {
    // This class was generated automatically 2017-12-07 22:10:42
    // module=shop,path=shop_receive_param
    RouterGuider routerGuider =  new RouterGuider("shop", "shop_receive_param");
    routerGuider.withString("name", name);
    routerGuider.withInt("id", id);
    return routerGuider;
  }
}
