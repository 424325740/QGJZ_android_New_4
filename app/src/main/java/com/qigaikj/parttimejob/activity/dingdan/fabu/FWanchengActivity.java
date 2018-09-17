package com.qigaikj.parttimejob.activity.dingdan.fabu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class FWanchengActivity extends BaseActivity {

    @BindView(R.id.xiala)
    ImageView xiala;
    @BindView(R.id.show_lin)
    LinearLayout showLin;
    @BindView(R.id.select_goin)
    ImageView selectGoin;
    @BindView(R.id.yongta)
    TextView yongta;
    @BindView(R.id.show)
    LinearLayout show;
    private boolean isshow = false;
    private boolean ischecked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fwancheng;
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

    @OnClick({R.id.show_lin, R.id.yongta})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.show_lin:
                if (isshow == false) {
                    show.setVisibility(View.VISIBLE);
                    isshow=true;
                }else {
                    show.setVisibility(View.GONE);
                    isshow=false;
                }
                break;
            case R.id.yongta:
                if (ischecked==false){
                    yongta.setBackground(getResources().getDrawable(R.drawable.yuanjiaohome));
                    yongta.setTextColor(getResources().getColor(R.color.mytext_2));
                }
                break;
        }
    }
}