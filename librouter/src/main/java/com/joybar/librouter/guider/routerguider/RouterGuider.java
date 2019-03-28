package com.joybar.librouter.guider.routerguider;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;

import com.joybar.librouter.routercore.InterceptorCallback;
import com.joybar.librouter.routercore.Router;
import com.joybar.librouter.routercore.Rule;
import com.joybar.librouter.guider.annotation.BigData;
import com.joybar.librouter.routercore.interceptor.RouteInterceptor;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by obo on 2017/11/23.
 */
public class RouterGuider {

    private Rule rule;
    private Bundle bundle = new Bundle();
    private InterceptorCallback mNavigationCallback;
    private RouteInterceptor interceptor;

    public static void init(Application application) {
    }
    public static void inject(Object activity) {

        Field []fields = activity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(BigData.class) != null) {
                Object data = BigDataManager.removeBigData(field.getName());
                if (data != null) {
                    try {
                        field.set(activity, data);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public RouterGuider(String module, String path) {
        rule = new Rule(module, path);
    }


    public void navigate( Context context) {
          Router.create()
                  .buildRule(rule)
                  .addInterceptor(interceptor)
                  .withInterceptorCallback(mNavigationCallback)
                  .withExtra(bundle)
                  .navigate(context);
    }

    public void navigate( Context context, int flag) {
        Router.create()
                .buildRule(rule)
                .addInterceptor(interceptor)
                .withInterceptorCallback(mNavigationCallback)
                .withExtra(bundle)
                .navigate(context,flag);
    }


    public void navigate( Activity activity, int requestCode) {
        Router.create()
                .buildRule(rule)
                .addInterceptor(interceptor)
                .withInterceptorCallback(mNavigationCallback)
                .withExtra(bundle)
                .navigate(activity,requestCode);
    }

    public RouterGuider withCallBack(InterceptorCallback navigationCallback) {
        mNavigationCallback = navigationCallback;
        return this;
    }

    public RouterGuider withRouteInterceptor(RouteInterceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    public RouterGuider withBundle(Bundle bundle) {
        return this;
    }

    public RouterGuider withInt(String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public RouterGuider withLong(String key, long value) {
        bundle.putLong(key, value);
        return this;
    }

    public RouterGuider withString(String key, String value) {
        bundle.putString(key, value);
        return this;
    }

    public RouterGuider withSerializable(String key, Serializable value) {
        bundle.putSerializable(key, value);
        return this;
    }

    public RouterGuider withParcelable(String key, Parcelable value) {
        bundle.putParcelable(key, value);
        return this;
    }

    public RouterGuider withObject(String key, Object value) {
        return this;
    }

    public RouterGuider withShort(String key, short value) {
        bundle.putShort(key, value);
        return this;
    }

    public RouterGuider withBoolean(String key, boolean value) {
        bundle.putBoolean(key, value);
        return this;
    }

    public RouterGuider withByte(String key, byte value) {
        bundle.putByte(key, value);
        return this;
    }

    public RouterGuider withChar(String key, char value) {
        bundle.putChar(key, value);
        return this;
    }

    public RouterGuider withBigBitmap(String key, Bitmap value) {
        BigDataManager.putBigData(key, value);
        return this;
    }

    // Animation
    public RouterGuider withTransition(int enterAnim, int exitAnim) {
        return this;
    }

}
