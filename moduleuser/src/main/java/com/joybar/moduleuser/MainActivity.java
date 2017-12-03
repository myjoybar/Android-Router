package com.joybar.moduleuser;

import android.content.Context;
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
import com.joybar.moduleuser.application.UserApplication;
import com.me.obo.routertable.RouterTable$$moduleshop;

@RouterRegister(module = "user", path = "user_main")
public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private Button btnGotoShop;
    private Button btnGotoShopWithParam;
    private Button btnGotoShopForResult;
    private Button btnGotoShopWithInterceptor;
    private Button btnModuleEventBus;
    private TextView tvDes;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_main);
        context = this;
        initView();
        initListener();
        ModuleEventBus.getInstance().register(this);
        UserApplication.getInstance().getApplication().getApplicationContext();

    }

    @ModuleEvent()
    public void testReceiveModuleEventBusData(String msg) {
        Toast.makeText(this, "I am user main activity,receive data from shop,msg=" + msg, Toast.LENGTH_LONG).show();
    }

    @ModuleEvent()
    public void testReceiveModuleEventBusData(ShopInfo shopInfo) {
        Toast.makeText(this, "I am user main activity,receive data from shop,msg=" + shopInfo.toString(), Toast
                .LENGTH_LONG).show();
    }

    private void initView() {
        btnGotoShop = findViewById(R.id.btn_simple_navigate);
        btnGotoShopWithParam = findViewById(R.id.btn_with_param);
        btnGotoShopForResult = findViewById(R.id.btn_for_result);
        btnGotoShopWithInterceptor = findViewById(R.id.btn_with_interceptor);
        btnModuleEventBus = findViewById(R.id.btn_module_event_bus);
        tvDes = findViewById(R.id.tv_des);
    }


    private void initListener() {

        btnGotoShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterTable$$moduleshop
                        .GoToShop_main()
                        .navigate(context);
                // OR
//                Router.create()
//                        .buildRule(new Rule("shop", "shop_main"))
//                        .navigate(context);


            }
        });


        btnGotoShopWithParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterTable$$moduleshop
                        .GoToShop_receive_param("obo", 23)
                        .navigate(context);

                // OR
//                final Bundle bundle = new Bundle();
//                bundle.putInt("id", 123);
//                bundle.putString("name", "obo");
//                Router.create()
//                        .buildRule(new Rule("shop", "shop_main"))
//                        .withExtra(bundle)
//                        .navigate(context);
            }
        });

        btnGotoShopForResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RouterTable$$moduleshop.GoToShop_finish_with_result()
                        .navigate(MainActivity
                        .this, 2);;
                // OR
//                Router.create()
//                        .buildRule(new Rule("shop", "shop_finish_with_result"))
//                        .navigate(MainActivity
//                        .this, 2);

            }
        });


        btnGotoShopWithInterceptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Router.create()
                        .buildRule(new Rule("shop", "shop_main"))
                        .addInterceptor(new TestInterceptor()).withInterceptorCallback(new InterceptorCallback() {
                    @Override
                    public void onIntercept(Object result) {
                        Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onContinue() {
                        Toast.makeText(context, "continue", Toast.LENGTH_LONG).show();

                    }
                }).navigate(context);
            }
        });

        btnModuleEventBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterTable$$moduleshop
                        .GoToShop_post_module_data()
                        .navigate(context);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String result01 = data.getStringExtra("Result01");
        switch (requestCode) {
            case 2:
                Toast.makeText(this, "onActivityResult:"+result01, Toast.LENGTH_LONG).show();
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
