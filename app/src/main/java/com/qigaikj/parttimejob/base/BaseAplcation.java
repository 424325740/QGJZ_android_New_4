package com.qigaikj.parttimejob.base;

import android.app.Application;
import android.content.Context;

import com.qigaikj.parttimejob.rongyun.MyConnectionStatusListener;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.push.RongPushClient;


/**
 * Created by xx on 2017/7/12.
 */

public class BaseAplcation extends Application {
    private static Context mcontext = null;
    // 用于存放倒计时时间（TimeButton）
    public static Map<String, Long> map;
    public static SharedPreferencesHelper sharedPreferencesHelper;
    public static IWXAPI iwxapi;

    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
        this.mcontext = getApplicationContext();
        iwxapi = WXAPIFactory.createWXAPI(this, RetrofitManager.WXID, false);
        iwxapi.registerApp(RetrofitManager.WXID);
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(getApplicationContext());
        //百度地图初始化
//        SDKInitializer.initialize(getApplicationContext());
        RongPushClient.registerHWPush(this);
        //初始化融云
        RongIM.init(this);
        /**
         * 监听融云网络状态变化
         */
        RongIM.setConnectionStatusListener(new MyConnectionStatusListener());
        /**
         * 监听消息
         */
        RongIM.getInstance().setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                LogUtils.i("监听到消息***********************************************************************" + i);
                LogUtils.i("接受方的id" + message.getSenderUserId() + "====" + message.getSentStatus().toString());
                switch (message.getConversationType()) {

                    case SYSTEM:
                        RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, message.getSenderUserId(), new RongIMClient.ResultCallback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean aBoolean) {
                                if (aBoolean) {
                                    LogUtils.i("列表中删除好友成功");
                                } else {
                                    LogUtils.i("列表中删除好友失败");
                                }
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {
                                LogUtils.i("删除好友错误信息：" + errorCode.getMessage());
                            }
                        });
                        break;
                    default:
                        break;
                }


                return false;
            }
        });
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "e91e9e0791ee58a0d39c3e6106e8ef42");
//        UMConfigure.init(this, "5a6eddf3f43e48191e000316", "182034117931111111", UMConfigure.DEVICE_TYPE_PHONE, "e91e9e0791ee58a0d39c3e6106e8ef42");
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回device token
//                LogUtils.i("注册成功"+deviceToken);
//                LogUtils.i("注成功了+---------------------------------");
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//            LogUtils.i("错误"+s+"----------"+s1);
//            }
//        });
//        UMConfigure.setLogEnabled(true);
//        HuaWeiRegister.register(this);
    }

    public static Context getAplcation() {
        return mcontext;
    }
}