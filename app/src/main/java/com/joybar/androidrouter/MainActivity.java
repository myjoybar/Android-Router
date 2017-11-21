package com.joybar.androidrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.joybar.librouter.Router;
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

//        RouterInject.inject("com.joybar.moduleuser.MainActivity");
//        RouterInject.inject("com.joybar.moduleshop.MainActivity");

        Router.registerRouter("user", "user_main", com.joybar.moduleuser.MainActivity.class);
        Router.registerRouter("shop", "shop_main", com.joybar.moduleshop.MainActivity.class);

        findViewById(R.id.btn_main_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Router
                        .with(MainActivity.this)
                        .buildRule(new Rule("user", "user_main"))
                        .go();
            }
        });
    }
}

