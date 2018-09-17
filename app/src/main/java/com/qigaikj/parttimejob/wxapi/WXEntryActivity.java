package com.qigaikj.parttimejob.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.BundPhoneActivity;
import com.qigaikj.parttimejob.activity.MainActivity;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.Share;
import com.qigaikj.parttimejob.bean.ThirLogin;
import com.qigaikj.parttimejob.bean.WXUser;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.LoggingInterceptor;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.concurrent.TimeUnit;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.qigaikj.parttimejob.base.BaseAplcation.sharedPreferencesHelper;


/**
 * Created by xx on 2017/6/19.
 * 微信登录页面
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static final String APP_ID = "wx838d6c655fba1d1b";
    private String WX_APP_SECRET = "ba69f572ec85776a212d6056e01b23da";
    private IWXAPI iwxapi;
    private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token";
    // 获取用户个人信息
    private String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo";
    private BaseResp baseResp;
    private String code = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//            Intent intent = new Intent();
//            intent.putExtra("user", wx_userXi);
//            LogUtils.i("微信返回成功");
//            intent.setClass(WXEntryActivity.this, MainActivity.class);
//            startActivity(intent);
            finish();

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = WXAPIFactory.createWXAPI(this, RetrofitManager.WXID, false);
        iwxapi.handleIntent(getIntent(), this);
        try {
            boolean result =  iwxapi.handleIntent(getIntent(), this);
            if(!result){
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.marker_layout;
    }

    @Override
    protected void initDetail() {
        hidetitle();
    }

    @Override
    protected void initTitleView() {

    }


    @Override
    public void onReq(BaseReq baseReq) {
        Log.i("wxiwdadasdawdawda","123456");
    }

    //发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp baseResp) {

        String result = "";
        if (baseResp != null) {
            this.baseResp = baseResp;
        }
        LogUtils.i("微信返回状态码："+baseResp.errCode);
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {//分享
                    getNews_share();//分享成功通知后台
                } else if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {//登陆
                    LogUtils.i("asdf", "微信登录操作.....");
                }
                //支付成功
                if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("支付结果");
                    builder.setMessage(baseResp.errCode + "");
                    builder.show();
                }
                result = "发送成功";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                if (baseResp.transaction != null) {
                    finish();
                }
                code = ((SendAuth.Resp) baseResp).code;
                Log.i("asdf", "获取到code" + code);

            /*
             * 将你前面得到的AppID、AppSecret、code，拼接成URL 获取access_token等等的信息(微信)
             */
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                if (true) {
                    builder.addInterceptor(new LoggingInterceptor());
                }
                //设置超时
                builder.connectTimeout(15, TimeUnit.SECONDS);
                builder.readTimeout(20, TimeUnit.SECONDS);
                builder.writeTimeout(20, TimeUnit.SECONDS);
                //错误重连
                builder.retryOnConnectionFailure(true);
                OkHttpClient okHttpClient = builder.build();
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.weixin.qq.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
                retrofit.create(BlogService.class).getWxaccess_token(APP_ID, WX_APP_SECRET, code, "authorization_code").enqueue(new Callback<WXUser>() {
                    @Override
                    public void onResponse(Call<WXUser> call, Response<WXUser> response) {
                        if (!response.body().equals("")) {
                            LogUtils.i("获取微信返回的数据" + response.body().getAccess_token());
                            String access_token = response.body().getAccess_token();
                            getUserInfo(access_token, response.body().getOpenid());
                        }
                    }

                    @Override
                    public void onFailure(Call<WXUser> call, Throwable t) {

                    }


                });
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                Log.i("wxiwdadasdawdawda",result);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                Toast.makeText(this, result, Toast.LENGTH_LONG).

                        show();

                finish();
                break;
            default:
                result = "发送返回";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iwxapi.handleIntent(data,this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
        finish();
    }

    /**
     * @param
     */
    private void getUserInfo(String access_token, String id) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (true) {
            builder.addInterceptor(new LoggingInterceptor());
        }
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        OkHttpClient okHttpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.weixin.qq.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        retrofit.create(BlogService.class).getWxaccess_UserInfo(access_token, id).enqueue(new Callback<WXUser>() {
            @Override
            public void onResponse(Call<WXUser> call, Response<WXUser> response) {
                if (!response.body().equals("")) {
                    //保存微信登陆的用户信息
                    LogUtils.i("微信信息" + response.body().getUnionid() + "用户名" + response.body().getNickname() + "性别" + response.body().getSex());
                    getThirLogin(response.body().getUnionid(), response.body().getNickname(), String.valueOf(response.body().getSex()), response.body().getHeadimgurl(), "1");
                }
            }

            @Override
            public void onFailure(Call<WXUser> call, Throwable t) {

            }


        });
//        RequestParams requestParams=new RequestParams("https://api.weixin.qq.com/sns/userinfo");
//        requestParams.addQueryStringParameter("access_token",access_token);
//        requestParams.addQueryStringParameter("openid",id);
//         x.http().get(requestParams, new Callback.CommonCallback<String>() {
//         @Override
//         public void onSuccess(String result) {
//             if (result!=null){
//                  wx_userXi=JSONObject.parseObject(result,Wx_UserXi.class);
//
//                 handler.sendMessage(new Message());
//
//             }
//         }
//
//         @Override
//         public void onError(Throwable ex, boolean isOnCallback) {
//
//         }
//
//         @Override
//         public void onCancelled(CancelledException cex) {
//
//         }
//
//         @Override
//         public void onFinished() {
//
//         }
//     });
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
//                        ActivityUtils.startActivityForBundleData(WXEntryActivity.this, BundPhoneActivity.class, bundle);
                        Intent intent=new Intent(WXEntryActivity.this,BundPhoneActivity.class);
                        intent.putExtra("uid",uid);
                        intent.putExtra("type",type);
                        startActivity(intent);
                    } else {//已绑定手机号
                        String token = response.body().getData().getToken();
                        sharedPreferencesHelper.putString(SharedPreferencesHelper.TOKEN,token );
                        sharedPreferencesHelper.putString(SharedPreferencesHelper.RONYUN_TOKEN, response.body().getData().getRctoken());
                        sharedPreferencesHelper.putString(SharedPreferencesHelper.USER_ID, response.body().getData().getUserId());
                        getRongYun(response.body().getData().getRctoken());

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
    public void getRongYun(String rontocken) {
        RongIM.connect(rontocken, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                LogUtils.i("Tocken过期");
            }

            @Override
            public void onSuccess(String s) {
                LogUtils.i("连接融云成功" + s);
                ActivityUtils.startActivityAndFinish(WXEntryActivity.this, MainActivity.class);
//                RongIM.getInstance().startConversationList(getContext());
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.i(errorCode.getMessage() + "----" + errorCode.getValue());

            }
        });
    }

    /**
     * 分享次数加一
     */
    public void getNews_share() {
        String id = sharedPreferencesHelper.getString(SharedPreferencesHelper.FXID, "");
        if (id != null && !id.equals("")) {
            RetrofitManager.getInstance().createReq(BlogService.class).getnews_share(id).enqueue(new Callback<WrapperRspEntity<Share>>() {
                @Override
                public void onResponse(Call<WrapperRspEntity<Share>> call, Response<WrapperRspEntity<Share>> response) {
                    if (response.body().getStatus() == 200) {

                    }
                }

                @Override
                public void onFailure(Call<WrapperRspEntity<Share>> call, Throwable t) {

                }
            });
        }


    }
}
