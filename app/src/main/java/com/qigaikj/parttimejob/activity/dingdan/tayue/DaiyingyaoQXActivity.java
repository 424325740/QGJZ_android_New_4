package com.qigaikj.parttimejob.activity.dingdan.tayue;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.PingjiaBean;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaiyingyaoQXActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_daiyingyao_qx;
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



    @OnClick(R.id.yingyao)
    public void onViewClicked() {
        getIsYingyao();
    }
    /**
     * 应邀
     */
    public void getIsYingyao() {
        String token = SharedPreferencesHelper.getInstance(DaiyingyaoQXActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getYingyao(token, "did").enqueue(new Callback<PingjiaBean>() {
            @Override
            public void onResponse(Call<PingjiaBean> call, Response<PingjiaBean> response) {
                String message = response.body().getMessage();
                Toast.makeText(DaiyingyaoQXActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<PingjiaBean> call, Throwable t) {

            }
        });
    }
}
