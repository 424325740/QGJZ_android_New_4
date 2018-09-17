package com.qigaikj.parttimejob.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BaomingBean;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.HomeJianzhiQXBean;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JianzhiQXActivity extends BaseActivity {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.unit)
    TextView unit;
    @BindView(R.id.state)
    ImageView state;
    @BindView(R.id.way)
    TextView way;
    @BindView(R.id.start_date)
    TextView startDate;
    @BindView(R.id.end_date)
    TextView endDate;
    @BindView(R.id.introduce)
    TextView introduce;
    @BindView(R.id.require)
    TextView require;
    @BindView(R.id.is_like)
    ImageView isLike;
    private String wid;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getJianzhi();
    }

    @Override
    protected int getContentViewId() {
        Intent intent = getIntent();
        wid = intent.getStringExtra("wid");
        token = SharedPreferencesHelper.getInstance(JianzhiQXActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        return R.layout.activity_jianzhi_qx;
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
        getTvTextTitle().setText("兼职详情");
    }

    @Override
    protected void initTitleView() {

    }

    /**
     * 兼职详情接口数据请求
     */
    public void getJianzhi() {
        RetrofitManager.getInstance().createReq(BlogService.class).getJianzhiQX(token, wid).enqueue(new Callback<HomeJianzhiQXBean>() {
            @Override
            public void onResponse(Call<HomeJianzhiQXBean> call, Response<HomeJianzhiQXBean> response) {
                name.setText(response.body().getData().getName());
                money.setText(response.body().getData().getMoney());
                startDate.setText(response.body().getData().getStart_date());
                endDate.setText("至" + response.body().getData().getEnd_date());
                introduce.setText(response.body().getData().getIntroduce());
                require.setText(response.body().getData().getRequire());
                if (response.body().getData().getIs_like().equals("1")) {
                    isLike.setBackground(getResources().getDrawable(R.mipmap.icon_guanzhu_pre));
                } else {
                    isLike.setBackground(getResources().getDrawable(R.mipmap.icon_guanzhu_nor));
                }
                if (response.body().getData().getWay() != null) {
                    if (response.body().getData().getWay().equals("1")) {
                        way.setText("日结");
                    } else {
                        way.setText("完工结");
                    }
                }
                if (response.body().getData().getState() != null) {
                    if (response.body().getData().getState().equals("1")) {
                        state.setVisibility(View.VISIBLE);
                    }
                }
                if (response.body().getData().getUnit() != null) {
                    if (response.body().getData().getUnit().equals("1")) {
                        unit.setText("天");
                    } else {
                        unit.setText("时");
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeJianzhiQXBean> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.fenxiang, R.id.guanzhu, R.id.zixun, R.id.baoming})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fenxiang:
                break;
            case R.id.guanzhu:
                break;
            case R.id.zixun:
                break;
            case R.id.baoming:
                getIsBaoming();
                break;
        }
    }

    /**
     * 报名接口
     */
    public void getIsBaoming(){
        RetrofitManager.getInstance().createReq(BlogService.class).getIsBaoming(token,wid).enqueue(new Callback<BaomingBean>() {
            @Override
            public void onResponse(Call<BaomingBean> call, Response<BaomingBean> response) {
                Toast.makeText(JianzhiQXActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<BaomingBean> call, Throwable t) {

            }
        });
    }
}
