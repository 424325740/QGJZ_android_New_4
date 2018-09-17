package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.Code;
import com.qigaikj.parttimejob.bean.ThirLogin;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.VerifyCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/11/16/016.
 * 绑定手机号/更改绑定手机号
 */

public class BundPhoneActivity extends BaseActivity {
    @BindView(R.id.et_bund_phone)
    EditText etBundPhone;
    @BindView(R.id.btn_timer)
    Button btnTimer;
    @BindView(R.id.tvpassow)
    EditText tvpassow;
    private String IsBundType = "1";//1：绑定，2：修改绑定手机号

    /**
     * 计时器
     */
    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);
    private Editable getcode;
    private String code;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bundphone;
    }

    @Override
    protected void initDetail() {
        IsBundType = getIntent().getStringExtra("isbundtype");
        uid = getIntent().getStringExtra("uid");
        type = getIntent().getStringExtra("type");
        if (IsBundType != null && IsBundType.equals("2")) {
            getTvTextTitle().setText("修改绑定");
        } else {
            getTvTextTitle().setText("绑定手机号");
        }
    }

    @Override
    protected void initTitleView() {
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("绑定手机号");
        getTvRightText().setVisibility(View.VISIBLE);
        getTvRightText().setText("确定");
        getTvRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getcode = tvpassow.getText();
                code = getcode +"";
                //绑定手机号
                initBunPhone();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 获取验证码
     */
    private String phone = "";
    private String code2 = "";//后台返回的验证码

    public void getCode() {
        phone = etBundPhone.getText().toString().trim();
        if (phone != null && !phone.equals("")) {
            RetrofitManager.getInstance().createReq(BlogService.class).getCode(phone).enqueue(new Callback<WrapperRspEntity<Code>>() {
                @Override
                public void onResponse(Call<WrapperRspEntity<Code>> call, Response<WrapperRspEntity<Code>> response) {
                    if (response.body().getStatus() == RetrofitManager.CODE) {
                        myCountDownTimer.start();
                        code2 = response.body().getData().getVerificationCode();
                    }
                }

                @Override
                public void onFailure(Call<WrapperRspEntity<Code>> call, Throwable t) {

                }
            });
        } else {
            showToastShort("请输入手机号码");
        }
    }

    @OnClick({R.id.btn_timer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_timer:
                getCode();
                break;
        }
    }

    /**
     * 提交绑定手机号数据
     */
    private String uid;//第三方id
    private String type;

    public void initBunPhone() {
        if (type == null || type.equals("")) {
            return;
        }
        if (phone == null || phone.equals("")) {
            showToastShort("请输入手机号");
            return;
        }
        if (code == null || code.equals("")) {
            showToastShort("请输入验证码");
            return;
        }
        if (code+""==code2) {
            showToastShort("验证码输入有误");
            return;
        }
        if (uid == null || uid.equals("")) {
            showToastShort("未获取到第三方id");
            return;
        }
        RetrofitManager.getInstance().createReq(BlogService.class).getLoginBindPhone(uid, type, phone, code+"").enqueue(new Callback<WrapperRspEntity<ThirLogin>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<ThirLogin>> call, Response<WrapperRspEntity<ThirLogin>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (response.body().getData().getFlag() != null && response.body().getData().getFlag().equals("2")) {
                        sharedPreferencesHelper.putString(SharedPreferencesHelper.TOKEN, response.body().getData().getToken());
                        sharedPreferencesHelper.putString(SharedPreferencesHelper.RONYUN_TOKEN, response.body().getData().getRctoken());
                        sharedPreferencesHelper.putString(SharedPreferencesHelper.USER_ID, response.body().getData().getUserId());
                        getRongYun(response.body().getData().getRctoken());
                        ActivityUtils.startActivityAndFinish(BundPhoneActivity.this, MainActivity.class);
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
                ActivityUtils.startActivityAndFinish(BundPhoneActivity.this, MainActivity.class);
//                RongIM.getInstance().startConversationList(getContext());
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.i(errorCode.getMessage() + "----" + errorCode.getValue());

            }
        });
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            btnTimer.setEnabled(false);
            btnTimer.setBackgroundResource(R.drawable.bg_binding_comment_no);
            btnTimer.setText(l / 1000 + "秒后可重新获取");
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            btnTimer.setText("重新获取验证码");
            //设置可点击
            btnTimer.setEnabled(true);
            btnTimer.setBackgroundResource(R.drawable.bg_binding_comment_no);
        }
    }

}
