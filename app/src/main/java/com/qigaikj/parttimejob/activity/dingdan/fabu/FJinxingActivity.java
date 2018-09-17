package com.qigaikj.parttimejob.activity.dingdan.fabu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.dingdan.FukuanActivity;
import com.qigaikj.parttimejob.adapter.LuyongAdapter;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BaomingGuanliBean;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FJinxingActivity extends BaseActivity {

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
    @BindView(R.id.rv)
    RecyclerView rv;
    private boolean isshow = false;
    private boolean ischecked = false;
    private String token;
    private String wid;
    private List<BaomingGuanliBean.DataBean> dataBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLuyong();
    }

    @Override
    protected int getContentViewId() {
        token = SharedPreferencesHelper.getInstance(FJinxingActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        Intent intent = getIntent();
        wid = intent.getStringExtra("wid");
        return R.layout.activity_fjinxing;
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

    @OnClick({R.id.show_lin, R.id.yongta, R.id.fukuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.show_lin:
                if (isshow == false) {
                    show.setVisibility(View.VISIBLE);
                    isshow = true;
                } else {
                    show.setVisibility(View.GONE);
                    isshow = false;
                }
                break;
            case R.id.yongta:
                if (ischecked == false) {
                    yongta.setBackground(getResources().getDrawable(R.drawable.yuanjiaohome));
                    yongta.setText("已付款");
                    yongta.setTextColor(getResources().getColor(R.color.mytext_2));
                }
                break;
            case R.id.fukuan:
                ActivityUtils.startActivity(FJinxingActivity.this, FukuanActivity.class);
                break;
        }
    }

    /**
     * 录用管理请求数据
     */
    public void getLuyong() {
        RetrofitManager.getInstance().createReq(BlogService.class).getLuyong(token, wid).enqueue(new Callback<BaomingGuanliBean>() {
            @Override
            public void onResponse(Call<BaomingGuanliBean> call, Response<BaomingGuanliBean> response) {
                if (response.body().getData().getUsers() != null) {
                    if (dataBeans != null) {
                        dataBeans.clear();
                    }
                    dataBeans.add(response.body().getData());
                    LuyongAdapter luyongAdapter = new LuyongAdapter(FJinxingActivity.this, dataBeans);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FJinxingActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rv.setLayoutManager(linearLayoutManager);
                    rv.setAdapter(luyongAdapter);
                    luyongAdapter.setClickCallBack(new LuyongAdapter.ItemClickCallBack() {
                        @Override
                        public void onItemClick(String uid, TextView textView) {

                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<BaomingGuanliBean> call, Throwable t) {

            }
        });
    }
}