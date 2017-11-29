//package com.me.obo.filter;
//
//import android.app.Activity;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.Keep;
//
//import com.alibaba.android.arouter.launcher.ARouter;
//
//// URL路由转发
//@Keep
//public class SchameFilterActivity extends Activity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Uri uri = getIntent().getData();
//        if(uri != null) {
//            ARouter.getInstance()
//                    .build(Uri.parse("/" + uri.getHost() + uri.getPath()))
//                    .navigation();
//        }
//        finish();
//    }
//}
