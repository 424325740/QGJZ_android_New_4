package com.qigaikj.parttimejob.activity.home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;

import butterknife.BindView;

public class QianDaoActivity extends BaseActivity {

    @BindView(R.id.web)
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web.loadUrl("http://ceshi.qigaikj.com/index.php/Users/SginIn");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_qian_dao;
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
        getTvTextTitle().setText("签到");
    }

    @Override
    protected void initTitleView() {

    }
}
