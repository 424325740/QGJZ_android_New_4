package com.qigaikj.parttimejob.activity.home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;

import butterknife.BindView;

public class ChongzhiActivity extends BaseActivity {

    @BindView(R.id.web)
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web.loadUrl("http://ceshi.qigaikj.com/index.php/Homepage/Recharge");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_chongzhi;
    }

    @Override
    protected void initDetail() {
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("充值返利");
    }

    @Override
    protected void initTitleView() {

    }
}
