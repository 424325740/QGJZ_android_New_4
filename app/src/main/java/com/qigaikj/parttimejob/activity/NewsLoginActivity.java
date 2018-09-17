package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.Login;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsLoginActivity extends BaseActivity {

    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_passeword)
    EditText etLoginPasseword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_login;
    }

    @Override
    protected void initDetail() {
        hidetitle();
    }

    @Override
    protected void initTitleView() {

    }

    @OnClick({R.id.finsh, R.id.denglu, R.id.wangji, R.id.zhuce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finsh:
                finish();
                break;
            case R.id.denglu://登陆
                getLogin();
                break;
            case R.id.wangji://忘记密码
                ActivityUtils.startActivity(NewsLoginActivity.this, ForgetPassWordActivity.class);
                break;
            case R.id.zhuce://注册
                ActivityUtils.startActivity(NewsLoginActivity.this, RegristActivity.class);
                break;
        }
    }
    private String login_phone = "";//账号
    private String login_passeword = "";//密码
    //登录
    public void getLogin() {
        login_phone = etLoginPhone.getText().toString().trim();
        login_passeword = etLoginPasseword.getText().toString().trim();
        if (login_phone == null || login_phone.equals("")) {
            showToastShort("请输入账号");
            return;
        }
        if (login_passeword == null || login_passeword.equals("")) {
            showToastShort("请输入密码");
            return;
        }
        RetrofitManager.getInstance().createReq(BlogService.class).getLogin(login_phone, login_passeword).enqueue(new Callback<WrapperRspEntity<Login>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<Login>> call, Response<WrapperRspEntity<Login>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
//                    getRongYun(response.body().getData().getRctoken(), response.body().getData().getToken(), response.body().getData().getUserId());
//                    showToastShort(response.body().getMsg());
                    ActivityUtils.startActivityAndFinish(NewsLoginActivity.this, MainActivity.class);

                    /**登陆保存用户信息*/
                    sharedPreferencesHelper.putString(SharedPreferencesHelper.TOKEN, response.body().getData().getToken());
                } else {
                    showToastShort(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<Login>> call, Throwable t) {

            }
        });
    }
}
