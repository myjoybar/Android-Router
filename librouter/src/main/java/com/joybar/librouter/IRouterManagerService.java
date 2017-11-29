package com.joybar.librouter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.joybar.librouter.interceptor.RouteInterceptor;


/**
 * Created by joybar on 12/11/2017.
 */

public interface IRouterManagerService {

    IRouterManagerService buildRule(Rule rule);

    IRouterManagerService withExtra(Bundle bundle);

    IRouterManagerService withExtra(byte [] bytes);

    IRouterManagerService withInterceptorCallback(InterceptorCallback interceptorCallback);

    IRouterManagerService addInterceptor(RouteInterceptor routeInterceptor);

    boolean isIntercepted();

    void navigate(Context context);

    void navigate(Activity activity,int requestCode);

}
