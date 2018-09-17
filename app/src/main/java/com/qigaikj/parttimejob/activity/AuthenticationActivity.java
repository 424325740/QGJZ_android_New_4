package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.my.CompanyActivity;
import com.qigaikj.parttimejob.activity.my.RealnameActivity;
import com.qigaikj.parttimejob.adapter.GrideViewHomeWelfareAdapter;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.AuthenticationBean;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.FollowBean;
import com.qigaikj.parttimejob.bean.HomeGridViewWelfare;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * 认证页
 *
 * */

public class AuthenticationActivity extends BaseActivity {

    @BindView(R.id.llRealName) // 实名认证
            LinearLayout llRealName;
    @BindView(R.id.tvRealName)
    TextView tvRealName;
    @BindView(R.id.llWeChat) // 微信认证
            LinearLayout llWeChat;
    @BindView(R.id.tvWeChat)
    TextView tvWeChat;
    @BindView(R.id.llZM) // 芝麻认证
            LinearLayout llZM;
    @BindView(R.id.tvZM)
    TextView tvZM;
    @BindView(R.id.llPhone) // 手机号认证
            LinearLayout llPhone;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.llSkill) // 技能认证
            LinearLayout llSkill;
    @BindView(R.id.tvSkill)
    TextView tvSkillv;
    @BindView(R.id.llCompany) // 公司认证
            LinearLayout llCompany;
    @BindView(R.id.tvCompany)
    TextView tvCompany;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initDetail() {
        getData();
    }

    @Override
    protected void initTitleView() {
        setTvTextTitle("认证");
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.llRealName, R.id.llWeChat, R.id.llZM, R.id.llPhone, R.id.llSkill, R.id.llCompany})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llRealName:
                ActivityUtils.startActivity(AuthenticationActivity.this, RealnameActivity.class);
                break;
            case R.id.llWeChat:
                break;
            case R.id.llZM:
                break;
            case R.id.llPhone:
                break;
            case R.id.llSkill:
                break;
            case R.id.llCompany:
                ActivityUtils.startActivity(AuthenticationActivity.this, CompanyActivity.class);
                break;
        }
    }

    public void getData() {
        String token = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getInformation(token, "1").enqueue(new Callback<WrapperRspEntity<AuthenticationBean>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<AuthenticationBean>> call, Response<WrapperRspEntity<AuthenticationBean>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (response.body().getData() != null) {
                        if ("1".equals(response.body().getData().real_name)) {
                            tvRealName.setText("已认证");
                        }
                        if ("1".equals(response.body().getData().wechat)) {
                            tvWeChat.setText("已认证");
                        }
                        if ("1".equals(response.body().getData().sesame)) {
                            tvZM.setText("已认证");
                        }
                        if ("1".equals(response.body().getData().phone)) {
                            tvPhone.setText("已认证");
                        }
                        if ("1".equals(response.body().getData().skill)) {
                            tvSkillv.setText("已认证");
                        }
                        if ("1".equals(response.body().getData().company)) {
                            tvCompany.setText("已认证");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<AuthenticationBean>> call, Throwable t) {

            }
        });
    }
}
