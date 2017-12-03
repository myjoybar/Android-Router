package com.joybar.androidrouter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.me.obo.routertable.RouterTable$$moduleuser;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.app_activity_main);
        context = this;
        gotoUserMain();
    }

    private void gotoUserMain(){
        RouterTable$$moduleuser
                .GoToUser_main()
                .navigate(context);
        // OR
//                Router.create()
//                        .buildRule(new Rule("user", "user_main"))
//                        .navigate(context);

    }

}

