package com.joybar.moduleuser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.joybar.annotation.router.annotation.RegisterRouter;
import com.joybar.librouter.guider.routertable.RouterTable$$Moduleshop;
import com.joybar.librouter.routercore.InterceptorCallback;
import com.joybar.librouter.routercore.interceptor.TestInterceptor;
import com.joybar.librouter.routerservice.RouterServiceManager;
import com.joybar.librouter.routerservice.exception.RouterServiceException;
import com.joybar.librouter.routerservice.inters.IBaseService;
import com.joybar.librouter.routerservice.inters.IServiceCallBack;
import com.joybar.moduleuser.application.UserApplication;

@RegisterRouter(module = "user", path = "main")
public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private Context mContext;
    private Button btnGotoShop;
    private Button btnGotoShopWithParam;
    private Button btnGotoShopForResult;
    private Button btnGotoShopWithInterceptor;
    private Button btnSyncInvokeComponent;
    private Button btnAsyncInvokeComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_main);
        mContext = this;
        initView();
        initListener();
        UserApplication.getInstance().getApplication();

    }

    private void initView() {
        btnGotoShop = findViewById(R.id.btn_simple_navigate);
        btnGotoShopWithParam = findViewById(R.id.btn_with_param);
        btnGotoShopForResult = findViewById(R.id.btn_for_result);
        btnGotoShopWithInterceptor = findViewById(R.id.btn_with_interceptor);
        btnSyncInvokeComponent = findViewById(R.id.btn_component_invoke_sync);
        btnAsyncInvokeComponent = findViewById(R.id.btn_component_invoke_async);
    }


    private void initListener() {
        btnGotoShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterTable$$Moduleshop
                        .launchMain()
                        .navigate(mContext);
                // OR
//                Router.create()
//                        .buildRule(new Rule("shop", "main"))
//                        .navigate(mContext);


            }
        });


        btnGotoShopWithParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterTable$$Moduleshop
                        .launchReceiveParam("obo", 25)
                        .navigate(mContext);

                // OR
//                final Bundle bundle = new Bundle();
//                bundle.putInt("id", 123);
//                bundle.putString("name", "obo");
//                Router.create()
//                        .buildRule(new Rule("shop", "receive_param"))
//                        .withExtra(bundle)
//                        .navigate(mContext);
            }
        });

        btnGotoShopForResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RouterTable$$Moduleshop
                        .launchFinishWithResult()
                        .navigate((Activity) mContext, 2);

                // OR
//                Router.create()
//                        .buildRule(new Rule("shop", "finish_with_result"))
//                        .navigate(MainActivity
//                        .this, 2);

            }
        });


        btnGotoShopWithInterceptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             RouterTable$$Moduleshop.launchMain()
                     .withRouteInterceptor(new TestInterceptor(mContext))
                     .withCallBack(new InterceptorCallback() {
                         @Override
                         public void onIntercept(Object result) {
                             Toast.makeText(mContext, result.toString(), Toast.LENGTH_LONG).show();
                         }

                         @Override
                         public void onContinue() {
                             Toast.makeText(mContext, "continue", Toast.LENGTH_LONG).show();
                         }
                     })
                     .navigate(mContext);


                // OR
//                Router.create()
//                        .buildRule(new Rule("shop", "main"))
//                        .addInterceptor(new TestInterceptor(mContext)).withInterceptorCallback(new InterceptorCallback() {
//                    @Override
//                    public void onIntercept(Object result) {
//                        Toast.makeText(mContext, result.toString(), Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onContinue() {
//                        Toast.makeText(mContext, "continue", Toast.LENGTH_LONG).show();
//
//                    }
//                }).navigate(mContext);


            }
        });



        btnSyncInvokeComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //调用同步服务
                IBaseService service = RouterServiceManager.getInstance().getService("RSShopService");
                String result = (String) service.execute("cmd_test","ABCDE");
                Toast.makeText(mContext, "User module " + result,Toast.LENGTH_LONG).show();
            }
        });

        btnAsyncInvokeComponent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //调用异步服务
				IBaseService service = RouterServiceManager.getInstance().getService("RSShopService");
				String userName = "Tom";
				String pwd = "123456";
				service.executeAsync("cmd_login", new IServiceCallBack() {

					@Override
					public void onSuccess(Object result) {

						Toast.makeText(mContext, result.toString(), Toast.LENGTH_LONG).show();

					}

					@Override
					public void onFailure(RouterServiceException routerServiceException) {
						Toast.makeText(mContext, routerServiceException.getMessage(), Toast.LENGTH_LONG).show();
					}
				}, userName, pwd);

			}
		});
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        Log.d(TAG,"requestCode = "+requestCode+", resultCode = "+resultCode);
        String result01 = data.getStringExtra("Result01");
        switch (requestCode) {
            case 2:
                Toast.makeText(mContext, "回传的值：Result01:"+result01, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
