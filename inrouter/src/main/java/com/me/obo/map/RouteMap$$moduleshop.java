package com.me.obo.map;

import com.me.obo.inrouter.InRouter;
import java.lang.Integer;

public final class RouteMap$$moduleshop {
  public static InRouter roudToShop_main(Integer id) {
    // Path = shop_main
    InRouter inrouter =  new InRouter("shop", "shop_main");
    inrouter.withInt("id", id);
    return inrouter;
  }
}
