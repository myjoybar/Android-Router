package com.joybar.moduleuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joybar.annotation.RouterRegister;
import com.joybar.librouter.InterceptorCallback;
import com.joybar.librouter.Router;
import com.joybar.librouter.Rule;
import com.joybar.librouter.interceptor.TestInterceptor;

@RouterRegister(module = "user", path = "user_main")
public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        TextView tv = new TextView(this);
        tv.setTextSize(50);
        tv.setText("this is  User!!!, go to Shop");
        setContentView(tv);
        setClickForRouter3(tv);
    }

    private void setClickForRouter3(TextView tv) {
        final Bundle bundle = new Bundle();
        bundle.putInt("id", 10000);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.with(MainActivity.this)
                        .buildRule(new Rule("shop", "shop_main"))
                        .withExtra(bundle)
                        .addInterceptor(new TestInterceptor())
                        .withInterceptorCallback(new InterceptorCallback() {
                            @Override
                            public void onIntercept(Object result) {
                                Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onContinue() {
                                Toast.makeText(MainActivity.this, "continue", Toast.LENGTH_LONG).show();

                            }
                        })
                        // .goForResult(2);
                        .go();

                finish();


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

}
