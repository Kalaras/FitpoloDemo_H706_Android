package com.fitpolo.demo.h706;

import android.app.Application;

import com.fitpolo.support.MokoSupport;

/**
 * @Date 2017/5/11
 * @Author wenzheng.liu
 * @Description
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializar
        MokoSupport.getInstance().init(getApplicationContext());
    }
}
