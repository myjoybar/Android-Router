package com.me.obo.routertable;

import com.me.obo.inrouter.InRouter;
import java.lang.Integer;
import java.lang.String;

public final class RouteTable$$moduleshop {
  public static InRouter GoToShop_main(String name, Integer id) {
    // This class was automatically generated
    // module=shop,path=shop_main
    InRouter inrouter =  new InRouter("shop", "shop_main");
    inrouter.withString("name", name);
    inrouter.withInt("id", id);
    return inrouter;
  }
}
