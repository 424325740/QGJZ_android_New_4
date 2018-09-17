package com.qigaikj.parttimejob.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.ThirLogin;
import com.qigaikj.parttimejob.bean.User;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginHlperActivity extends BaseActivity {

    @BindView(R.id.edtitle)
    TextView edtitle;
    private String USER_ACTION = "";
    private boolean isLogin = true;//默认为注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edtitle.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        edtitle.getPaint().setAntiAlias(true);//抗锯齿
//        imageView.setImageResource(R.drawable.icon_bg); //图片资源
//        setAnimate1();
//        bgpingyi.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_bg));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login_hlper;
    }

    @Override
    protected void initDetail() {
        hidetitle();
        //获取之前登录过的账号
        USER_ACTION = sharedPreferencesHelper.getString(SharedPreferencesHelper.USER_ACCOUNT, "");
        if (USER_ACTION == null || USER_ACTION.equals("")) {
            isLogin = true;
        } else {
            isLogin = false;
        }

        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(RetrofitManager.QQID, LoginHlperActivity.this);
    }

    @Override
    protected void initTitleView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTencent.logout(this);
    }
/*
    private void setAnimate1() {
//      imageView中凡是有get，set方法的属性，都可以通过属性动画操作
//      创建属性动画对象，并设置移动的方向和偏移量
//      translationX是imageview的平移属性
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(bgpingyi, "translationY", 0f, -200f);
//      设置移动时间
        objectAnimator.setDuration(1000);
//      开始动画
        objectAnimator.start();
    }*/

    private String LoginType = "0";//1:微信登陆；2:QQ登陆；3:微博登陆

    @OnClick({R.id.finsh, R.id.qq_denglu, R.id.shouji_denglu, R.id.weixin_denglu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finsh://关闭页面
                finish();
                break;
            case R.id.qq_denglu://QQ登陆
                if (!mTencent.isSessionValid()) {
                    //注销登录 mTencent.logout(this);
                    LogUtils.i("点击登陆-----------------------------》qq");
                    mTencent.login(this, "all", listener);
                }
                LoginType = "2";
                break;
            case R.id.shouji_denglu://手机号登陆
                ActivityUtils.startActivity(LoginHlperActivity.this, NewsLoginActivity.class);
                break;
            case R.id.weixin_denglu://微信登陆
                WXLogin();
                LoginType = "1";
                break;
        }
    }

    private String login_phone = "";//账号
    private String login_passeword = "";//密码
    /**
     * QQ登录
     */
    private Tencent mTencent;

    // 微信登录
    private static IWXAPI WXapi;
    private String WX_APP_ID = "wx838d6c655fba1d1b";

    /**
     * 登录微信
     */
    private void WXLogin() {
        WXapi = WXAPIFactory.createWXAPI(this, RetrofitManager.WXID, true);
        WXapi.registerApp(WX_APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        WXapi.sendReq(req);
        LoginType = "1";
    }

    //qq成功回掉需添加此方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
    }

    /**
     * QQ登录
     */
    private UserInfo userInfo;

    private BaseUiListener listener = new BaseUiListener();
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    Gson gson = new Gson();
                    User user = gson.fromJson(response.toString(), User.class);
                    if (user != null) {
                        LogUtils.i("userInfo:昵称：" + user.getNickname() + "  性别:" + user.getGender() + "  地址：" + user.getProvince() + user.getCity());
//                        LogUtils.i("头像路径：" + user.getFigureurl_qq_2());
                        LogUtils.i("用户id：" + userid);
                        if (userid != null && !userid.equals("")) {
                            if (user.getGender().equals("男")) {
                                getThirLogin(userid, user.getNickname(), "1", user.getFigureurl_qq_2(), "2");
                            } else {
                                getThirLogin(userid, user.getNickname(), "2", user.getFigureurl_qq_2(), "2");
                            }
                        }
                    }


                }
            }
        }
    };

    // 实现回调 IUiListener
    //调用SDK已经封装好的接口时，例如：登录、快速支付登录、应用分享、应用邀请等接口，需传入该回调的实例。
    class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            LogUtils.i("获取数据成功");
            LogUtils.i(o.toString());
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                initOpenidAndToken(jsonObject);
                updateUserInfo();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError e) {
            showToastShort("onError:code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
        }

        @Override
        public void onCancel() {
            showToastShort("onCancel");
        }
    }

    private String userid;

    /**
     * 获取登录QQ腾讯平台的权限信息(用于访问QQ用户信息)
     *
     * @param jsonObject
     */
    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            userid = openId;
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError e) {
                    LogUtils.i("qq返回错误" + e.toString());
                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    LogUtils.i("tag", response.toString());
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                }

                @Override
                public void onCancel() {
                }
            };
            userInfo = new UserInfo(LoginHlperActivity.this, mTencent.getQQToken());
            userInfo.getUserInfo(listener);
        }
    }

    /**
     * 第三方登陆
     */
    public void getThirLogin(final String uid, String name, String gender, String iconurl, final String type) {
        RetrofitManager.getInstance().createReq(BlogService.class).getThirLogin(uid, name, gender, iconurl, type).enqueue(new Callback<WrapperRspEntity<ThirLogin>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<ThirLogin>> call, Response<WrapperRspEntity<ThirLogin>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (response.body().getData().getFlag() != null && response.body().getData().getFlag().equals("1")) {//未绑定手机号
                        Bundle bundle = new Bundle();
                        bundle.putString("uid", uid);
                        bundle.putString("type", type);
                        ActivityUtils.startActivityForBundleData(LoginHlperActivity.this, BundPhoneActivity.class, bundle);
                        LoginHlperActivity.this.finish();
                    } else {//已绑定手机号
                        getRongYun(response.body().getData().getRctoken(), response.body().getData().getToken(), response.body().getData().getUserId());
                        showToastShort(response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<ThirLogin>> call, Throwable t) {

            }
        });
    }

    /**
     * 连接融云
     */
    public void getRongYun(final String rontocken, final String tocken, final String user_id) {
        RongIM.connect(rontocken, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                LogUtils.i("Tocken过期");
            }

            @Override
            public void onSuccess(String s) {
                sharedPreferencesHelper.putString(SharedPreferencesHelper.TOKEN, tocken);
                sharedPreferencesHelper.putString(SharedPreferencesHelper.RONYUN_TOKEN, rontocken);
                sharedPreferencesHelper.putString(SharedPreferencesHelper.USER_ID, user_id);
                sharedPreferencesHelper.putString(SharedPreferencesHelper.USER_ACCOUNT, login_phone);
                LogUtils.i("连接融云成功" + s);
                ActivityUtils.startActivityAndFinish(LoginHlperActivity.this, MainActivity.class);
//                RongIM.getInstance().startConversationList(getContext());
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.i(errorCode.getMessage() + "----" + errorCode.getValue());

            }
        });
    }

}
