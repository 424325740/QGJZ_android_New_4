package com.qigaikj.parttimejob.rongyun;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.qigaikj.parttimejob.activity.LoginHlperActivity;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.view.PushDialog;

/**
 * Created by Administrator on 2017/12/7/007.
 */

public class NetworkReciver extends BroadcastReceiver {
    public static final String RECIVER_TYPE="reciver_type";
    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        String tockentype = intent.getStringExtra("TOCKEN_TYPE");

        if (action.equals(RECIVER_TYPE) && tockentype.equals("tockentype")) {
            LogUtils.i("收到了"+action+"--------------"+tockentype);
            PushDialog.showLoadDialog(context, "您的账号在别的设备登陆,您被迫下线！", "确定", "重新登录", true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginHlperActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    PushDialog.dismissLoadDialog();
                }
            });

        }
    }
}
