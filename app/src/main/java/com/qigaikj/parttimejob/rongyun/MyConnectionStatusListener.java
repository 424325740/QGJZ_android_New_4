package com.qigaikj.parttimejob.rongyun;

import android.content.Intent;
import android.widget.Toast;

import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static com.qigaikj.parttimejob.base.BaseAplcation.getAplcation;
import static com.qigaikj.parttimejob.base.BaseAplcation.sharedPreferencesHelper;

/**
 * Created by Administrator on 2017/12/6/006.
 * 融云监听网络状态变化
 */

public class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {
    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        switch (connectionStatus) {

            case CONNECTED://连接成功。
                break;
            case DISCONNECTED://断开连接。

                break;
            case CONNECTING://连接中。

                break;
            case NETWORK_UNAVAILABLE://网络不可用。

                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                LogUtils.i("设备被踢下线");
                String city = sharedPreferencesHelper.getString(SharedPreferencesHelper.CITY, "");
                sharedPreferencesHelper.clear();
                sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, city);
                RongIM.getInstance().logout();//融云退出
                Toast.makeText(getAplcation(), "您的账号在别的地方登录！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(NetworkReciver.RECIVER_TYPE);
                intent.putExtra("TOCKEN_TYPE", "tockentype");
                getAplcation().sendBroadcast(intent);
                break;
        }
    }

}
