package com.joybar.moduleshop;

import com.joybar.librouter.routerservice.inters.IBaseService;
import com.joybar.librouter.routerservice.inters.IServiceCallBack;

public class ShopService implements IBaseService {
    @Override
    public Object execute(String cmd, Object... args) {

        if (cmd.equals("testReturn")) {

            return "TestResult";
        }
        return null;
    }

    @Override
    public void executeAsync(String cmd, IServiceCallBack iServiceCallBack, Object... args) {

        if (cmd.equals("testReturnWithCallBack")) {
            iServiceCallBack.onSuccess("TestResult");
        }
    }

    @Override
    public String moduleServiceName() {

        return "DTShopService";
    }
}
