package com.qigaikj.parttimejob.activity.dingdan.tayue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;

public class JinxingQXActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_jinxing_qx;
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
        getTvTextTitle().setText("订单详情");
    }

    @Override
    protected void initTitleView() {

    }
}