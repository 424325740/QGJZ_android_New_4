package com.qigaikj.parttimejob.activity.dingdan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.util.ActivityUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class FukuanXQActivity extends BaseActivity {
    @BindView(R.id.xingbie)
    LinearLayout xingbie;
    @BindView(R.id.jineng)
    LinearLayout jineng;
    @BindView(R.id.show_lin)
    LinearLayout showLin;
    @BindView(R.id.xiala)
    ImageView xiala;
    private boolean flag = false;
    private View view1;
    private TextView jixu;
    private boolean islin1 = true;
    private boolean islin2 = false;
    private boolean islin3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fukuan;
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

    @OnClick({R.id.show_lin, R.id.chengyijin, R.id.wuchengyijin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.show_lin:
                if (flag == false) {
                    jineng.setVisibility(View.VISIBLE);
                    xingbie.setVisibility(View.VISIBLE);
                    xiala.setBackground(getResources().getDrawable(R.mipmap.btn_shangla));
                    flag = true;
                } else {
                    jineng.setVisibility(View.GONE);
                    xingbie.setVisibility(View.GONE);
                    xiala.setBackground(getResources().getDrawable(R.mipmap.btn_lalalalaal));
                    flag = false;
                }
                break;
            case R.id.chengyijin:
                ActivityUtils.startActivity(FukuanXQActivity.this, FukuanActivity.class);
                break;
            case R.id.wuchengyijin:
                ActivityUtils.startActivity(FukuanXQActivity.this, FukuanActivity.class);
                break;
        }
    }


}
