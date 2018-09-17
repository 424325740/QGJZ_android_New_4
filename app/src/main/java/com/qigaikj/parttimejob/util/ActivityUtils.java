package com.qigaikj.parttimejob.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

/**
 * Activity工具类
 */
public class ActivityUtils {
    /**
     * 启动一个Activity并关闭当前Activity
     *
     * @param activity 当前Activity
     * @param cls      要启动的Activity
     */
    public static void startActivityAndFinish(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 启动Activity
     *
     * @param activity 当前Activity
     * @param cls      要启动的Activity Class
     */
    public static void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    /**
     * 启动Activity并回调数据
     *
     * @param activity   当前Activity
     * @param cls        要启动的Activity Class
     * @param resultCode
     */
    public static void startActivityForResult(Activity activity, Class<?> cls, int resultCode) {
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent, resultCode);
    }

    /**
     * 启动Activity并传递Bundle数据
     *
     * @param act    当前Activity
     * @param cls    要启动的Activity Class
     * @param bundle 数据
     */
    public static void startActivityForBundleData(Activity act, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(act, cls);
        intent.putExtras(bundle);
        act.startActivity(intent);
    }


    /**
     * 打开Activity并删除中间页面
     *
     * @param act
     */
    public static void startActivityAndClear(Activity act, Class<?> cls) {
        Intent intent = new Intent(act, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        act.startActivity(intent);
    }

    /**
     * 启动网络设置
     *
     * @param activity 当前Activity
     */
    public static void startSetNetActivity(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        activity.startActivity(intent);
    }

    /**
     * 启动系统设置
     *
     * @param activity 当前Activity
     */
    public static void startSetActivity(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }
}
