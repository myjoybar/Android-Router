package com.joybar.androidrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.me.obo.routertable.RouteTable$$moduleshop;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);
        setListener();
    }

    private void setListener() {
        findViewById(R.id.btn_main_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Router.getRouterService().buildRule(new Rule("user", "user_main")).navigate(MainActivity.this);
                // OR
                RouteTable$$moduleshop.GoToShop_main("obo", 23).navigate(MainActivity.this);
            }
        });
    }


}

