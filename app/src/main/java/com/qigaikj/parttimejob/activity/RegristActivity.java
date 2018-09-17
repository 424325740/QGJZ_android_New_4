package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.Code;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author:created by liangliang on 2018/6/21/021
 * function:
 * 注册页面
 */

public class RegristActivity extends BaseActivity {

    @BindView(R.id.et_Phone)
    EditText etPhone;
    @BindView(R.id.et_Code)
    EditText etCode;
    @BindView(R.id.btn_timer)
    TextView btnTimer;
    @BindView(R.id.et_Password)
    EditText etPassword;
    @BindView(R.id.tv_Register_Agreement)
    TextView tvRegisterAgreement;

    /**
     * 计时器
     */
    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);
    @BindView(R.id.finsh)
    ImageView finsh;
    @BindView(R.id.zhuce)
    TextView zhuce;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_regrist;
    }

    @Override
    protected void initDetail() {
        SpannableString string = new SpannableString("点击注册即表示同意用户协议");
        Pattern p = Pattern.compile("用户协议");
        Matcher m = p.matcher(string);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            string.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.pink)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tvRegisterAgreement.setText(string);
        tvRegisterAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("url", RetrofitManager.LOGINURL);
                ActivityUtils.startActivityForBundleData(RegristActivity.this, WebWiewActivity.class, bundle);
            }
        });
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

    //注册
    public void getRegist() {
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
            showToastShort("请输入密码");
            return;
        }
        if (code.equals(code2)) {
            RetrofitManager.getInstance().createReq(BlogService.class).getRegest(phone, password, code).enqueue(new Callback<WrapperRspEntity<Code>>() {
                @Override
                public void onResponse(Call<WrapperRspEntity<Code>> call, Response<WrapperRspEntity<Code>> response) {
                    if (response.body().getStatus() == RetrofitManager.CODE) {
                        ActivityUtils.startActivityAndFinish(RegristActivity.this, MainActivity.class);
                    }
                    showToastShort(response.body().getMsg());
                }

                @Override
                public void onFailure(Call<WrapperRspEntity<Code>> call, Throwable t) {

                }
            });
        } else {
            showToastShort("验证码有误");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.finsh, R.id.btn_timer, R.id.zhuce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finsh:
                finish();
                break;
            case R.id.btn_timer:
                getCode();
                break;
            case R.id.zhuce:
                getRegist();
                break;
        }
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
