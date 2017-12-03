package com.joybar.moduleshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.joybar.annotation.RouterRegister;
import com.me.obo.annotation.DataParam;

@RouterRegister(module = "shop", path = "shop_receive_param")
public class ReceiveParamActivity extends AppCompatActivity {

    private TextView tvDes;
    @DataParam
    public int id;
    @DataParam
    public String name;

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


