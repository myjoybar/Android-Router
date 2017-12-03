package com.me.obo.autorouter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;

import com.joybar.librouter.IRouterManagerService;
import com.joybar.librouter.InterceptorCallback;
import com.joybar.librouter.Router;
import com.joybar.librouter.Rule;
import com.joybar.librouter.interceptor.RouteInterceptor;
import com.me.obo.annotation.BigData;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by obo on 2017/11/23.
 */
@Keep
public class AutoRouter {
    public static void init(Application application) {
//        ARouter.init(application);
    }
    public static void inject(Object activity) {
//        ARouter.getInstance().inject(activity);

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


    String mPath;
    IRouterManagerService postcard;
    Rule rule;

    Bundle bundle = new Bundle();

    private InterceptorCallback mNavigationCallback;
    private RouteInterceptor interceptor;

    public AutoRouter(String module, String path) {
        rule = new Rule(module, path);
    }

    public AutoRouter withCallBack(InterceptorCallback navigationCallback) {
        mNavigationCallback = navigationCallback;
        return this;
    }

    public AutoRouter withRouteInterceptor(RouteInterceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }


    public void navigate(@NonNull  Context context) {
        IRouterManagerService service = Router.create().buildRule(rule);
        if (mNavigationCallback != null) {
            service.withInterceptorCallback(mNavigationCallback);
        }

        if (interceptor != null) {
            service.addInterceptor(interceptor);
        }
        if(null !=bundle ){
            service.withExtra(bundle);
        }
        service.navigate(context);
    }

    public void navigate(Activity activity, int requestCode) {
        IRouterManagerService service = Router.create().buildRule(rule);
        if (mNavigationCallback != null) {
            service.withInterceptorCallback(mNavigationCallback);
        }

        if (interceptor != null) {
            service.addInterceptor(interceptor);
        }
        if(null !=bundle ){
            service.withExtra(bundle);
        }
        service.navigate(activity,requestCode);
    }



    /////////////////////////////
    //with Params

    //With Bundle
    public AutoRouter withBundle(Bundle bundle) {
//        postcard.with(bundle);
        return this;
    }

    //With Int
    public AutoRouter withInt(String key, int value) {
//        postcard.withInt(key, value);
        bundle.putInt(key, value);
        return this;
    }

    //With Int
    public AutoRouter withLong(String key, long value) {
//        postcard.withLong(key, value);
        bundle.putLong(key, value);
        return this;
    }

    //With String
    public AutoRouter withString(String key, String value) {
//        postcard.withString(key, value);
        bundle.putString(key, value);
        return this;
    }

    //With Serializable
    public AutoRouter withSerializable(String key, Serializable value) {
//        postcard.withSerializable(key, value);
        bundle.putSerializable(key, value);
        return this;
    }

    //With Serializable
    public AutoRouter withParcelable(String key, Parcelable value) {
//        postcard.withParcelable(key, value);
        bundle.putParcelable(key, value);
        return this;
    }

    //With Object
    public AutoRouter withObject(String key, Object value) {
//        postcard.withObject(key, value);

        return this;
    }

    //With short
    public AutoRouter withShort(String key, short value) {
//        postcard.withShort(key, value);
        bundle.putShort(key, value);
        return this;
    }

    //With boolean
    public AutoRouter withBoolean(String key, boolean value) {
//        postcard.withBoolean(key, value);
        bundle.putBoolean(key, value);
        return this;
    }

    //With byte
    public AutoRouter withByte(String key, byte value) {
        bundle.putByte(key, value);
//        postcard.withByte(key, value);
        return this;
    }

    //With byte
    public AutoRouter withChar(String key, char value) {
        bundle.putChar(key, value);
//        postcard.withChar(key, value);
        return this;
    }

    //With bigData
    public AutoRouter withBigBitmap(String key, Bitmap value) {
        BigDataManager.putBigData(key, value);
        return this;
    }

    //////////////////////////////////////
    // Animation

    //With transition
    public AutoRouter withTransition(int enterAnim, int exitAnim) {
//        postcard.withTransition(enterAnim, exitAnim);
        return this;
    }

}
