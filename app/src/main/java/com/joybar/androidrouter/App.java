package com.joybar.androidrouter;

import android.app.Application;

import com.me.obo.inrouter.InRouter;

/**
 * Created by 云山 on 2017/11/28.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InRouter.inject(this);
    }
}
