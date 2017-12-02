package com.joybar.moduleshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.joybar.annotation.RouterRegister;
import com.joybar.librouter.Router;
import com.joybar.librouter.Rule;
import com.joybar.moduleeventbus.ModuleEventBus;
import com.joybar.moduleeventbus.data.ShopInfo;
import com.me.obo.annotation.DataParam;

@RouterRegister(module = "shop", path = "shop_main")
public class MainActivity extends AppCompatActivity {
	private static String TAG = "MainActivity";

	private Button btnGotoUser;
	private Button btnPostDataForUser;
	private TextView tvDes;

	@DataParam
	public int id;
	@DataParam
	public String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_activity_main);
		initView();
		initListener();
		initData();

	}

	private void initView() {
		btnGotoUser = findViewById(R.id.btn_go);
		btnPostDataForUser = findViewById(R.id.btn_post_data);
		tvDes = findViewById(R.id.tv_des);
	}

	private void initData() {
		Bundle mExtras = getIntent().getExtras();
		int id = mExtras.getInt("id", 0);
		tvDes.setText(tvDes.getText().toString() + "\n" + "传过来的ID：" + id);

	}

	private void initListener() {

		btnGotoUser.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Router.getRouterService().buildRule(new Rule("user", "user_main")).navigate(MainActivity.this);
				RouteMap$$moduleuser.roudToUser_main().navigate(MainActivity.this);
				finish();

//				Intent mIntent = new Intent();
//				mIntent.putExtra("change01", "1000");
//				mIntent.putExtra("change02", "2000");
//				// 设置结果，并进行传送
//				MainActivity.this.setResult(3, mIntent);
//				finish();
			}
		});

		btnPostDataForUser.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				ModuleEventBus.getInstance().post("I am form shop");
				ModuleEventBus.getInstance().post(new ShopInfo("AAA", "hanzghou"));
			}
		});

	}

}


