package com.joybar.moduleshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.joybar.annotation.RegisterRouter;
import com.me.obo.annotation.DataParam;
import com.me.obo.annotation.RegisterLaunch;

@RegisterRouter(module = "shop", path = "receive_param")
public class ReceiveParamActivity extends AppCompatActivity {

    private TextView tvDes;
    @DataParam
    public int id;
    @DataParam
    public String name;
    @DataParam
    public String address;

    @RegisterLaunch
    public static void launch(Context context,String name,int id){
        Intent intent = new Intent(context,ReceiveParamActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity_receive_param);
        initView();
        initData();
    }

    private void initView() {
        tvDes = findViewById(R.id.tv_des);
    }

    private void initData() {
        Bundle mExtras = getIntent().getExtras();
        int id = mExtras.getInt("id", 0);
        String name = mExtras.getString("name", "");
        tvDes.setText(tvDes.getText().toString()+"\n"+"Receive data :ID:" + id + "," + "name:" + name);

    }

}


