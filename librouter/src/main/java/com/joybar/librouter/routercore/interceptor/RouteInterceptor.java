package com.joybar.librouter.routercore.interceptor;


import com.joybar.librouter.routercore.RouterRequest;

/**
 * Created by joybar on 15/11/2017.
 */

public interface RouteInterceptor {
    boolean isIntercepted(RouterRequest routerRequest);
}
