package com.bart.easyshopping.common;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 作者：${Bart} on 2017/7/21 10:18
 * 邮箱：botao_zheng@163.com
 */

public class EasyShoppingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
