package com.joybar.androidrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.joybar.librouter.guider.routertable.RouterTable$$Moduleuser;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gotoUserMain();
    }

    private void gotoUserMain() {
        RouterTable$$Moduleuser
                .launchMain()
                .navigate(MainActivity.this);
        finish();
    }

}

