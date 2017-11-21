package com.joybar.librouter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.joybar.librouter.interceptor.RouteInterceptor;
import com.joybar.librouter.utils.CheckUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joybar on 12/11/2017.
 */

public class RouterService implements IRouterManagerService {

    RouterRequest routerRequest;
    private static List<RouteInterceptor> routeInterceptors;

    public RouterService(Context context) {
        routerRequest = new RouterRequest();
        routeInterceptors = new ArrayList<>();
        routerRequest.setContext(context);
    }

    @Override
    public IRouterManagerService buildRule(Rule rule) {
        CheckUtils.checkNotNull(rule);
        Rule registerRule = Router.ruleMap.get(rule);
        if (registerRule == null) {
            throw new IllegalArgumentException("You cannot build an unRegisterRule,have you register it?");
        }
        routerRequest.setRule(registerRule);
        return this;
    }


    @Override
    public void go() {
        if (!isIntercepted()) {
            Context context = routerRequest.getContext();
            CheckUtils.checkNotNull(context);
            routerRequest.getContext().startActivity(buildIntent(context));
        }
    }

    @Override
    public void goForResult(int requestCode) {
        if (!isIntercepted()) {
            Context context = routerRequest.getContext();
            CheckUtils.checkNotNull(context);
            ((Activity) routerRequest.getContext()).startActivityForResult(buildIntent(context), requestCode);
        }
    }

    private Intent buildIntent(Context context) {
        Class klass = routerRequest.getRule().getClassz();
        if (klass == null) {
            throw new RuntimeException("class can  not be  null in RouterRequest,have your set it in  your RouterService");
        }
        Intent intent = new Intent(context, routerRequest.getRule().getClassz());
        Bundle bundle = routerRequest.getBundle();
        if (bundle != null) {
            intent.putExtras(routerRequest.getBundle());
        }
        return intent;
    }


    @Override
    public IRouterManagerService withInterceptorCallback(InterceptorCallback interceptorCallback) {
        routerRequest.setInterceptorCallback(interceptorCallback);
        return this;
    }

    @Override
    public IRouterManagerService addInterceptor(RouteInterceptor routeInterceptor) {
        routeInterceptors.add(routeInterceptor);
        return this;
    }

    @Override
    public boolean isIntercepted() {
        for (RouteInterceptor interceptor : routeInterceptors) {
            if (interceptor.isIntercepted(routerRequest)) {
                InterceptorCallback interceptorCallback = routerRequest.getInterceptorCallback();
                if(null!=interceptorCallback){
                    interceptorCallback.onIntercept("被拦截");
                }
                return true;
            }
        }
        InterceptorCallback interceptorCallback = routerRequest.getInterceptorCallback();
        if(null!=interceptorCallback){
            interceptorCallback.onContinue();
        }
        return false;
    }

    @Override
    public IRouterManagerService withExtra(Bundle bundle) {
        routerRequest.setBundle(bundle);
        return this;
    }


}
