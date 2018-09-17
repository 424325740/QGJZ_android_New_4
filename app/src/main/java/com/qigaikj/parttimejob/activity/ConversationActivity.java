package com.qigaikj.parttimejob.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.FriendDetails;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.qigaikj.parttimejob.base.BaseAplcation.sharedPreferencesHelper;

/**
 * Created by Administrator on 2017/11/13/013.
 * 融云会话页面
 */

public class ConversationActivity extends FragmentActivity {
    @BindView(R.id.tv_conversation_text)
    TextView tvConversationText;
    /**
     * 对方id
     */
    private String mTargetId;
    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent == null || intent.getData() == null)
            return;
        mTargetId = intent.getData().getQueryParameter("targetId");
        mConversationType = Conversation.ConversationType.valueOf(intent.getData()
                .getLastPathSegment().toUpperCase(Locale.US));
        //动态创建
//        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager()
//
//                .findFragmentById(R.id.conversation);
//
//        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//
//                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
//
//                .appendQueryParameter("targetId", mTargetId).build();
//
//        fragment.setUri(uri);
        //对方 昵称
        String title = getIntent().getData().getQueryParameter("title");
        LogUtils.i("对方姓名为：" + title);
        if (!TextUtils.isEmpty(title)) {
            //todo 设置标题为对方昵称
            tvConversationText.setText(title);
        }
        RongIM.setConversationBehaviorListener(new RongIM.ConversationBehaviorListener() {
            /**
             * 点击头像后执行
             * @param context
             * @param conversationType
             * @param userInfo
             * @return
             */
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
//                Bundle bundle = new Bundle();
//                bundle.putString("frientid", userInfo.getUserId());
//                ActivityUtils.startActivityForBundleData(ConversationActivity.this
//                        , FrientDetailsActivity.class, bundle);
                return true;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String uid = SharedPreferencesHelper.getInstance(ConversationActivity.this).getString(SharedPreferencesHelper.USER_ID, "");
//        String uid = sharedPreferencesHelper.getString(SharedPreferencesHelper.USER_ID, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getFriendsDetails(uid, mTargetId).enqueue(new Callback<WrapperRspEntity<FriendDetails>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<FriendDetails>> call, Response<WrapperRspEntity<FriendDetails>> response) {
               /* if (response.body().getStatus() == RetrofitManager.CODE) {
                    UserInfo info = RongUserInfoManager.getInstance().getUserInfo(mTargetId);
                    if (info != null) {
                        LogUtils.i("好友信息:" + info.getName());
                        LogUtils.i("好友信息:" + info.getPortraitUri());
                    }
                    if (info != null && info.getName().equals(response.body().getData().getNickname()) && String.valueOf(info.getPortraitUri()).equals(response.body().getData().getLogo())) {
                        //好友信息未更改
                        LogUtils.i("未更改好友信息" + response.body().getData().getNickname());
                        LogUtils.i("未更改好友信息" + response.body().getData().getLogo());
                    } else {//设置好友信息
                        LogUtils.i("更改好友信息" + response.body().getData().getNickname());
                        LogUtils.i("更改好友信息" + response.body().getData().getLogo());
                        tvConversationText.setText(response.body().getData().getNickname());

                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(mTargetId, response.body().getData().getNickname(), Uri.parse(response.body().getData().getLogo())));
                    }
                }*/
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<FriendDetails>> call, Throwable t) {

            }
        });
    }

    //客服系统添加
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        if (!fragment.onBackPressed()) {
            finish();
        }
    }
}
