package com.qigaikj.parttimejob.rongyun;

import android.content.Context;
import com.qigaikj.parttimejob.util.LogUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by Administrator on 2017/11/11/011.
 * 继承融云广播接收器
 */

public class SealNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        LogUtils.i("融云------------------------------------onNotificationMessageArrived");
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        LogUtils.i("融云------------------------------------onNotificationMessageClicked");
        switch (pushNotificationMessage.getConversationType()) {
            case SYSTEM://系统消息
//                Bundle bundle = new Bundle();
//                bundle.putString("frientid", pushNotificationMessage.getSenderId());
//                Intent intent = new Intent(context, FrientDetailsActivity.class);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
                break;
            case PRIVATE://私人消息

                RongIM.getInstance().startConversation(context, Conversation.ConversationType.PRIVATE, pushNotificationMessage.getSenderId(), pushNotificationMessage.getSenderName());
                break;

        }
        return true;
    }
}
