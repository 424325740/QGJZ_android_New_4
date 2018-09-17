package com.qigaikj.parttimejob.util;

import android.app.Application;
import android.util.Log;

import com.qigaikj.parttimejob.activity.MainActivity;
import com.qigaikj.parttimejob.wxapi.WXEntryActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);//初始化,然后记得在清单文件配置此类。

    }
}