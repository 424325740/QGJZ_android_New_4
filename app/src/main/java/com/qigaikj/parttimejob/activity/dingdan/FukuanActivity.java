package com.qigaikj.parttimejob.activity.dingdan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class FukuanActivity extends BaseActivity {

    @BindView(R.id.weixin)
    ImageView weixin;
    @BindView(R.id.zhifubao)
    ImageView zhifubao;
    @BindView(R.id.qianbao)
    ImageView qianbao;
    @BindView(R.id.youhuijuan)
    ImageView youhuijuan;
    private boolean islin2=false;
    private boolean islin3=false;
    private boolean islin4=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fukuan2;
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
        getTvTextTitle().setText("支付薪水");
    }

    @Override
    protected void initTitleView() {

    }

    @OnClick({R.id.linzf1, R.id.linzf2, R.id.linzf3, R.id.linzf4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linzf1:
                weixin.setBackground(getResources().getDrawable(R.mipmap.btn_select_pre));
                zhifubao.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
                qianbao.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
                break;
            case R.id.linzf2:
                if (islin2==false){
                    zhifubao.setBackground(getResources().getDrawable(R.mipmap.btn_select_pre));
                    weixin.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
                    qianbao.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
                }
                break;
            case R.id.linzf3:
                if (islin3==false){
                    qianbao.setBackground(getResources().getDrawable(R.mipmap.btn_select_pre));
                    weixin.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
                    zhifubao.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
                }
                break;
            case R.id.linzf4:
                if (islin4==false){
                    youhuijuan.setBackground(getResources().getDrawable(R.mipmap.btn_select_pre));
                    islin4=true;
                }else {
                    youhuijuan.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
                    islin4=false;
                }
                break;
        }
    }
}
