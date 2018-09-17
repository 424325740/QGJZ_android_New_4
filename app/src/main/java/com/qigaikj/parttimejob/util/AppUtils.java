package com.qigaikj.parttimejob.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/11/13/013.
 */

public class AppUtils {

    public static int getWeighi(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 获取当前版本更新次数
     *
     * @param context
     * @return
     */
    public static int getAppCode(Context context) {
        PackageManager pm = context.getPackageManager();//context为当前Activity上下文
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            String version = pi.versionName;
            int code = pi.versionCode;
            LogUtils.i("当前版本号为：" + code);
            return code;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前版本信息
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {
        PackageManager pm = context.getPackageManager();//context为当前Activity上下文
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            String version = pi.versionName;
            int code = pi.versionCode;
            LogUtils.i("当前版本号名称为：" + code);
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
