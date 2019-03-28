package com.joybar.librouter.guider.routertable;

import com.joybar.librouter.guider.routerguider.RouterGuider;
import java.lang.Integer;
import java.lang.String;

public final class RouterTable$$Moduleshop {
  public static RouterGuider launchFinishWithResult() {
    // This class was generated automatically 2019-03-28 09:51:45
    // module=shop,path=finish_with_result
    RouterGuider routerGuider =  new RouterGuider("shop", "finish_with_result");
    return routerGuider;
  }

  public static RouterGuider launchMain() {
    // This class was generated automatically 2019-03-28 09:51:45
    // module=shop,path=main
    RouterGuider routerGuider =  new RouterGuider("shop", "main");
    return routerGuider;
  }

  public static RouterGuider launchPostModuleData() {
    // This class was generated automatically 2019-03-28 09:51:45
    // module=shop,path=post_module_data
    RouterGuider routerGuider =  new RouterGuider("shop", "post_module_data");
    return routerGuider;
  }

  public static RouterGuider launchReceiveParam(String address) {
    // This class was generated automatically 2019-03-28 09:51:45
    // module=shop,path=receive_param
    RouterGuider routerGuider =  new RouterGuider("shop", "receive_param");
    routerGuider.withString("address", address);
    return routerGuider;
  }

  public static RouterGuider launchReceiveParam(String name, Integer id) {
    // This class was generated automatically 2019-03-28 09:51:45
    // module=shop,path=receive_param
    RouterGuider routerGuider =  new RouterGuider("shop", "receive_param");
    routerGuider.withString("name", name);
    routerGuider.withInt("id", id);
    return routerGuider;
  }
}
