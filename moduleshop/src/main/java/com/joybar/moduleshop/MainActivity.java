package com.joybar.moduleshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.joybar.annotation.RouterRegister;
import com.joybar.moduleshop.application.ShopApplication;

@RouterRegister(module = "shop", path = "shop_main")
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity_main);
        ShopApplication.getInstance().getApplication();
    }


}


