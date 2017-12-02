package com.me.obo.map;

import com.me.obo.inrouter.InRouter;
import java.lang.Integer;
import java.lang.String;

public final class RouteMap$$moduleshop {
  public static InRouter GoToShop_main(String name, Integer id) {
    // module=shop,Path = shop_main
    InRouter inrouter =  new InRouter("shop", "shop_main");
    inrouter.withString("name", name);
    inrouter.withInt("id", id);
    return inrouter;
  }
}
