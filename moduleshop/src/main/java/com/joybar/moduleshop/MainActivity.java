package com.joybar.moduleshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.joybar.annotation.router.annotation.RegisterRouter;
import com.joybar.moduleshop.application.ShopApplication;

@RegisterRouter(module = "shop", path = "main")
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity_main);
        ShopApplication.getInstance().getApplication();
        mTextView = findViewById(R.id.tv_des);
        mTextView.setText("我从Module User 跳转过来");

    }

}


