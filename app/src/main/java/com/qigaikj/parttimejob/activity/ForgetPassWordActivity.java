package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.Code;
import com.qigaikj.parttimejob.bean.Login;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.RetrofitManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 开发者：庞宇锋
 * 时间： 2018/1/2/002.
 * 功能：忘记密码页面
 */

public class ForgetPassWordActivity extends BaseActivity {
    @BindView(R.id.et_Phone)
    EditText etPhone;
    @BindView(R.id.et_Code)
    EditText etCode;
    @BindView(R.id.btn_timer)
    TextView btnTimer;
    @BindView(R.id.et_Password)
    EditText etPassword;
    @BindView(R.id.tv_Register_Agreement)
    TextView tvAgreement;

    /**
     * 计时器
     */
    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);


    @Override
    protected int getContentViewId() {
        return R.layout.activity_forg;
    }

    @Override
    protected void initDetail() {
        tvAgreement.setVisibility(View.GONE);
    }

    @Override
    protected void initTitleView() {
        hidetitle();
    }

    /**
     * 获取验证码
     */
    private String phone = "";
    private String code = "";
    private String code2 = "";//后台返回的验证码
    private String password = "";//密码

    public void getCode() {
        phone = etPhone.getText().toString().trim();
        if (phone != null && !phone.equals("")) {
            RetrofitManager.getInstance().createReq(BlogService.class).getCode(phone).enqueue(new Callback<WrapperRspEntity<Code>>() {
                @Override
                public void onResponse(Call<WrapperRspEntity<Code>> call, Response<WrapperRspEntity<Code>> response) {
                    if (response.body().getStatus() == RetrofitManager.CODE) {
                        myCountDownTimer.start();
                        code2 = response.body().getData().getVerificationCode();
                    }
                    showToastShort(response.body().getMsg());
                }

                @Override
                public void onFailure(Call<WrapperRspEntity<Code>> call, Throwable t) {

                }
            });
        } else {
            showToastShort("手机号不能为空");
        }
    }

    //修改密码
    public void getUpdatePassword() {
        code = etCode.getText().toString().trim();//获取输入的验证码
        password = etPassword.getText().toString().trim();//获取密码
        phone = etPhone.getText().toString().trim();
        if (phone == null || phone.equals("")) {
            showToastShort("请输入手机号码");
            return;
        }
        if (code == null || code.equals("")) {
            showToastShort("请输入验证码");
            return;
        }
        if (password == null || password.equals("")) {
            showToastShort("请输入新密码");
            return;
        }
        if (code.equals(code2)) {
            RetrofitManager.getInstance().createReq(BlogService.class).forgetPassWord(phone, password, code).enqueue(new Callback<WrapperRspEntity<Login>>() {
                @Override
                public void onResponse(Call<WrapperRspEntity<Login>> call, Response<WrapperRspEntity<Login>> response) {
                    if (response.body().getStatus() == RetrofitManager.CODE) {
                        finish();
                    }
                    showToastShort(response.body().getMsg());
                }

                @Override
                public void onFailure(Call<WrapperRspEntity<Login>> call, Throwable t) {

                }
            });
        } else {
            showToastShort("验证码有误");
        }
    }

    @OnClick({R.id.iv_Regrist, R.id.btn_timer,R.id.finsh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_timer://点击发送验证码
                getCode();
                break;
            case R.id.iv_Regrist://点击修改/
                getUpdatePassword();
                break;
            case R.id.finsh:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
