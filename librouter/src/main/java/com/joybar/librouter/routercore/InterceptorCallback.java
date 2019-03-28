package com.joybar.librouter.routercore;

/**
 * Created by joybar on 12/11/2017.
 */

public interface InterceptorCallback {
    void onIntercept(Object result);
    void onContinue();
}
