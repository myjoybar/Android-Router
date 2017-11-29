package com.joybar.androidrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.joybar.compiler.RouterInject;
import com.joybar.librouter.Router;
import com.joybar.librouter.Rule;
import com.me.obo.map.RouteMap$$moduleshop;

public class MainActivity extends AppCompatActivity {

	private static String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_activity_main);
		//initRouter1();
		initRouter2();
	}

	private void initRouter1() {
		RouterInject.inject("com.joybar.moduleuser.MainActivity");
		RouterInject.inject("com.joybar.moduleshop.MainActivity");
	}

	private void initRouter2() {

		Router.registerRouter("user", "user_main", com.joybar.moduleuser.MainActivity.class);
		Router.registerRouter("shop", "shop_main", com.joybar.moduleshop.MainActivity.class);

		findViewById(R.id.btn_main_user).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Router.getRouterService().buildRule(new Rule("user", "user_main")).navigate(MainActivity.this);
				RouteMap$$moduleshop.roudToShop_main(23).navigate(MainActivity.this);
			}
		});
	}
}

