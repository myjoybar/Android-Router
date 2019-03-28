package com.joybar.moduleshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.joybar.annotation.router.annotation.RegisterRouter;
import com.joybar.moduleshop.application.ShopApplication;

@RegisterRouter(module = "shop", path = "main")
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity_main);
        ShopApplication.getInstance().getApplication();
    }


}


