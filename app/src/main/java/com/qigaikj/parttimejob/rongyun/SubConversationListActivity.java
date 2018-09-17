package com.qigaikj.parttimejob.rongyun;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2017/11/14/014.
 * 融云聚合会话列表
 */

public class SubConversationListActivity extends FragmentActivity {

    @BindView(R.id.iv_subconver_left)
    ImageView ivSubconverLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subconver);
        ButterKnife.bind(this);
        /**
         * 自定义消息页面拦截点击事件
         */
        RongIM.setConversationListBehaviorListener(new MyConversationListBehaviorListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.iv_subconver_left)
    public void onViewClicked() {
        finish();
    }

    private class MyConversationListBehaviorListener implements RongIM.ConversationListBehaviorListener {
        /**
         * 点击头像
         *
         * @param context
         * @param conversationType
         * @param s
         * @return
         */
        @Override
        public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
            return false;
        }

        /**
         * 长按会话头像
         *
         * @param context
         * @param conversationType
         * @param s
         * @return
         */
        @Override
        public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
            return false;
        }

        /**
         * 长按列表中的item
         *
         * @param context
         * @param view
         * @param uiConversation
         * @return
         */
        @Override
        public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
            return false;
        }

        /**
         * 点击会话中的item
         *
         * @param context
         * @param view
         * @param uiConversation
         * @return
         */
        @Override
        public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
//            LogUtils.i("点击了" + uiConversation.getConversationTargetId());
//            uiConversation.getUnReadType();
            LogUtils.i("点击了" + uiConversation.getUnReadType());
//            RongIM.getInstance().getRongIMClient().setMessageReceivedStatus(uiConversation.getLatestMessageId(), new Message.ReceivedStatus(1), null);
            RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.SYSTEM, uiConversation.getConversationTargetId(), new RongIMClient.ResultCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    if (aBoolean){
                        LogUtils.i("消息状态更新成功");
                    }else{
                        LogUtils.i("消息状态更新失败");
                    }
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
            Bundle bundle = new Bundle();
            bundle.putString("frientid", uiConversation.getConversationTargetId());
//            ActivityUtils.startActivityForBundleData(SubConversationListActivity.this, FrientDetailsActivity.class, bundle);
            return true;
        }
    }

}
