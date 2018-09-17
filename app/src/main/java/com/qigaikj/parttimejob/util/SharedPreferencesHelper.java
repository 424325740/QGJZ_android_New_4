package com.qigaikj.parttimejob.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesHelper {

    public static final String USER_ACCOUNT = "user_account";//用户账号
    public static final String USER_ID = "userId";//用户id
    public static final String TOKEN = "token";//自己后台验证
    public static final String RONYUN_TOKEN = "ronyun_token";//融云验证
    public static final String PHONE = "phone";//用户登录手机号码
    public static final String CITY = "city";//当前选择城市
    public static final String LOCATION_CITY = "location_city";//当前定位城市
    public static final String LONG="Longitude";//经度
    public static final String LAT="Latitude";//纬度

    public static final String WIP = "wip";//1:为wip 2：不是wip
    public static final String INTEGRAL = "integral";//积分
    public static final String TICKET = "ticket";//发布券

    public static final String VERSIONS = "versions";//版本号

    public static final String FXID = "fxid";//分享信息id

    public static final String ORDER = "order";//微信订单id
    private static SharedPreferencesHelper sharedPreferencesHelper = null;

    public static SharedPreferencesHelper getInstance(Context context) {
        if (sharedPreferencesHelper == null) {
            synchronized (SharedPreferencesHelper.class) {
                if (sharedPreferencesHelper == null) {
                    sharedPreferencesHelper = new SharedPreferencesHelper();
                    sharedPreferencesHelper.setContext(context);
                    return sharedPreferencesHelper;
                }
            }
        }
        return sharedPreferencesHelper;
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean getBoolean(String key, boolean defValue) {
        try {
            return getSP().getBoolean(key, defValue);
        } catch (NullPointerException exception) {
            Log.d("hcj", "" + exception);
            return defValue;
        }
    }

    public void putBoolean(String key, boolean value) {
        try {
            SharedPreferences.Editor editor = getSP().edit();
            editor.putBoolean(key, value);
            editor.commit();
        } catch (NullPointerException exception) {
            Log.d("hcj", "" + exception);
        }
    }


    public long getLong(String key, long defValue) {
        try {
            return getSP().getLong(key, defValue);
        } catch (NullPointerException exception) {
            Log.d("hcj", "" + exception);
            return defValue;
        }
    }

    public void putLong(String key, long value) {
        try {
            SharedPreferences.Editor editor = getSP().edit();
            editor.putLong(key, value);
            editor.commit();
        } catch (NullPointerException exception) {
            Log.d("hcj", "" + exception);
        }
    }

    public int getInt(String key, int defaultValue) {
        try {
            return getSP().getInt(key, defaultValue);
        } catch (Exception e) {
            Log.d("hcj", "" + e);
            return defaultValue;

        }
    }

    public void putInt(String key, int value) {
        try {
            SharedPreferences.Editor editor = getSP().edit();
            editor.putInt(key, value);
            editor.commit();
        } catch (Exception e) {
            Log.d("hcj", "" + e);
        }
    }

    public String getString(String key, String defValue) {
        try {
            return getSP().getString(key, defValue);
        } catch (NullPointerException e) {
            Log.d("hcj", "" + e);
            return defValue;
        }
    }

    public void putString(String key, String value) {
        try {
            SharedPreferences.Editor editor = getSP().edit();
            editor.putString(key, value);
            editor.commit();
        } catch (NullPointerException e) {
            Log.d("hcj", "" + e);
        }
    }

    public void clear() {
        try {
            SharedPreferences.Editor editor = getSP().edit();
            editor.clear();
            editor.commit();
        } catch (NullPointerException e) {
            Log.d("hcj", "" + e);
        }
    }

    private SharedPreferences getSP() {
        return context.getSharedPreferences("sp", Context.MODE_PRIVATE);
    }

    private SharedPreferences getSP(String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
}
