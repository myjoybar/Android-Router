package com.joybar.moduleuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.joybar.annotation.RouterRegister;
import com.joybar.librouter.InterceptorCallback;
import com.joybar.librouter.Router;
import com.joybar.librouter.Rule;
import com.joybar.librouter.interceptor.TestInterceptor;
import com.joybar.moduleeventbus.ModuleEvent;
import com.joybar.moduleeventbus.ModuleEventBus;
import com.joybar.moduleeventbus.data.ShopInfo;

@RouterRegister(module = "user", path = "user_main")
public class MainActivity extends AppCompatActivity {
	private static String TAG = "MainActivity";
	private Button btnGotoShop;
	private TextView tvDes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_activity_main);
		initView();
		initListener();
		initData();
		ModuleEventBus.getInstance().register(this);

	}

	@ModuleEvent()
	public void testReceiveModuleEventBusData(String msg) {
		Log.d(TAG, "msg=" + msg);
		tvDes.setText("receive data from shop,msg=" + msg);
		Toast.makeText(this,"receive data from shop,msg=" + msg,Toast.LENGTH_LONG).show();
	}

	@ModuleEvent()
	public void testReceiveModuleEventBusData(ShopInfo shopInfo) {
		Log.d(TAG, "shopInfo.toString()=" + shopInfo.toString());
		tvDes.setText("receive data from shop,shopInfo.toString()=" + shopInfo.toString());
		Toast.makeText(this,"receive data from shop,msg=" + shopInfo.toString(),Toast.LENGTH_LONG).show();
	}

	private void initView() {
		btnGotoShop = findViewById(R.id.btn_go);
		tvDes = findViewById(R.id.tv_des);
	}

	private void initData() {

	}

	private void initListener() {

		btnGotoShop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Bundle bundle = new Bundle();
				bundle.putInt("id", 10000);
				Router.with(MainActivity.this).buildRule(new Rule("shop", "shop_main")).withExtra
						(bundle).addInterceptor(new TestInterceptor()).withInterceptorCallback(new
																																															  InterceptorCallback() {
					@Override
					public void onIntercept(Object result) {
						Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG)
								.show();
					}

					@Override
					public void onContinue() {
						Toast.makeText(MainActivity.this, "continue", Toast.LENGTH_LONG).show();

					}
				})
						// .goForResult(2);
						.go();

				//finish();
			}
		});
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		String change01 = data.getStringExtra("change01");
		switch (requestCode) {
			case 2:
				Toast.makeText(this, change01, Toast.LENGTH_LONG).show();
				break;

			default:
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
		ModuleEventBus.getInstance().unregister(this);
	}
}
