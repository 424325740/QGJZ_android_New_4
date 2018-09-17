package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.util.ActivityUtils;

import butterknife.OnClick;

/**
 *更多页面
 */
public class MoreActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_more;
    }

    @Override
    protected void initDetail() {

    }

    @Override

    protected void initTitleView() {
        getTvTextTitle().setText("更多");
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.tvAbout, R.id.tvHelp, R.id.tvOpinion, R.id.tvScore, R.id.tvDisclaimer, R.id.tvCustomer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvAbout://关于我们
                showToastShort("没有，别点了   ㄟ( ▔, ▔ )ㄏ");
                break;
            case R.id.tvHelp://帮助反馈
                showToastShort("没有，别点了   ㄟ( ▔, ▔ )ㄏ");
                break;
            case R.id.tvOpinion://意见反馈
                ActivityUtils.startActivity(MoreActivity.this, WenTiActivity.class);
                break;
            case R.id.tvScore://应用评分
                showToastShort("没有，别点了   ㄟ( ▔, ▔ )ㄏ");
                break;
            case R.id.tvDisclaimer://免责申明
                showToastShort("没有，别点了   ㄟ( ▔, ▔ )ㄏ");
                break;
            case R.id.tvCustomer://联系客服
                showToastShort("没有，别点了   ㄟ( ▔, ▔ )ㄏ");
                break;
        }
    }
}
