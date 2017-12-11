package com.me.obo.routertable;

import com.me.obo.routerguider.RouterGuider;
import java.lang.Long;
import java.lang.String;

public final class RouterTable$$Moduleshop {
  public static RouterGuider launchFinishWithResult() {
    // This class was generated automatically 2017-12-07 23:02:04
    // module=shop,path=finish_with_result
    RouterGuider routerGuider =  new RouterGuider("shop", "finish_with_result");
    return routerGuider;
  }

  public static RouterGuider launchMain() {
    // This class was generated automatically 2017-12-07 23:02:04
    // module=shop,path=main
    RouterGuider routerGuider =  new RouterGuider("shop", "main");
    return routerGuider;
  }

  public static RouterGuider launchPostModuleData() {
    // This class was generated automatically 2017-12-07 23:02:04
    // module=shop,path=post_module_data
    RouterGuider routerGuider =  new RouterGuider("shop", "post_module_data");
    return routerGuider;
  }

  public static RouterGuider launchReceiveParam(String address, Long number) {
    // This class was generated automatically 2017-12-07 23:02:04
    // module=shop,path=receive_param
    RouterGuider routerGuider =  new RouterGuider("shop", "receive_param");
    routerGuider.withString("address", address);
    routerGuider.withLong("number", number);
    return routerGuider;
  }
}
