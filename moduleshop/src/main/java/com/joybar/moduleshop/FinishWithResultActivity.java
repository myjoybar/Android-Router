package com.joybar.moduleshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.joybar.annotation.RegisterRouter;

@RegisterRouter(module = "shop", path = "finish_with_result")
public class FinishWithResultActivity extends AppCompatActivity {
    private static final String TAG = "FinishWithResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity_finish_with_result);
        findViewById(R.id.btn_finish_with_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.putExtra("Result01", "1000");
                FinishWithResultActivity.this.setResult(3, mIntent);
                finish();
            }
        });

    }


}


