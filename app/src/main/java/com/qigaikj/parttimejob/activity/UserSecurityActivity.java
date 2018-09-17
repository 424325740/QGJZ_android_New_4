package com.qigaikj.parttimejob.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.my.Xiugaipassword;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.util.ActivityUtils;

import butterknife.OnClick;

/**
 * 我的-账号安全
 */
public class UserSecurityActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_security;
    }

    @Override
    protected void initDetail() {

    }

    @Override
    protected void initTitleView() {
        setTvTextTitle("修改绑定");
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.tvUpdatePhone, R.id.tvUpDatePassward})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvUpdatePhone:
                Intent intent=new Intent(UserSecurityActivity.this, BundPhoneActivity.class);
                intent.putExtra("isbundtype","1");
                startActivity(intent);
                break;
            case R.id.tvUpDatePassward:
                ActivityUtils.startActivity(UserSecurityActivity.this, Xiugaipassword.class);
                break;
        }
    }
}
