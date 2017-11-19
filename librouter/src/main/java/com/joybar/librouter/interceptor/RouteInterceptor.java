package com.joybar.librouter.interceptor;


import com.joybar.librouter.RouterRequest;

/**
 * Created by joybar on 15/11/2017.
 */

public interface RouteInterceptor {
    boolean isIntercepted(RouterRequest routerRequest);
}
