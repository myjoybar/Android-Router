package com.joybar.androidrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.joybar.androidrouter.InjectComponentUtil.InjectComponentUtil;
import com.joybar.librouter.RouterManager;
import com.joybar.librouter.Rule;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRouter3();
    }

    private void initRouter3() {

        InjectComponentUtil.inject("com.joybar.moduleuser.MainActivity");
        InjectComponentUtil.inject("com.joybar.moduleshop.MainActivity");

//        RouterManager.registerRouter("user", "user_main", com.joybar.moduleuser.MainActivity.class);
//        RouterManager.registerRouter("shop", "shop_main", com.joybar.moduleshop.MainActivity.class);

        findViewById(R.id.btn_main_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterManager
                        .with(MainActivity.this)
                        .buildRule(new Rule("user", "user_main"))
                        .go();
            }
        });
    }
}

