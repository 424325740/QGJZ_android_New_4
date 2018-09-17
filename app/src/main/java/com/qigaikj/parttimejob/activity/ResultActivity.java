package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.Result;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/11/27/027.
 * 获取支付结果页面
 */

public class ResultActivity extends BaseActivity {

    @BindView(R.id.tv_result_total)
    TextView tvResultTotal;
    @BindView(R.id.tv_result_payment)
    TextView tvResultPayment;
    @BindView(R.id.tv_result_out_trade_no)
    TextView tvResultOutTradeNo;
    @BindView(R.id.tv_result_transaction)
    TextView tvResultTransaction;
    @BindView(R.id.tv_result_addtimer)
    TextView tvResultAddtimer;
    @BindView(R.id.tv_result_endtime)
    TextView tvResultEndtime;
    @BindView(R.id.tv_result_queren)
    TextView tvResultQueren;
    @BindView(R.id.tv_result_title)
    TextView tvResultTitle;
    private int code = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_result;
    }

    @Override
    protected void initDetail() {

    }

    @Override
    protected void initTitleView() {
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("支付结果");
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getResult();//查询支付结果
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 查询支付结果
     */
    public void getResult() {
        String tocken = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        String order = sharedPreferencesHelper.getString(SharedPreferencesHelper.ORDER, "");
        if (tocken != null && !tocken.equals("") && order != null && !order.equals("")) {
            RetrofitManager.getInstance().createReq(BlogService.class).getPayNotify(tocken, order).enqueue(new Callback<WrapperRspEntity<Result>>() {
                @Override
                public void onResponse(Call<WrapperRspEntity<Result>> call, Response<WrapperRspEntity<Result>> response) {
                    if (response.body().getStatus() == RetrofitManager.CODE) {
                        code = response.body().getData().getFlag();
                        if (response.body().getData().getFlag() == 1) {
                            tvResultTotal.setText(response.body().getData().getTotal_price());
                            tvResultPayment.setText(response.body().getData().getPayment());
                            tvResultOutTradeNo.setText(response.body().getData().getOut_trade_no());
                            tvResultTransaction.setText(response.body().getData().getTransaction_id());
                            tvResultAddtimer.setText(response.body().getData().getAddtime());
                            tvResultEndtime.setText(response.body().getData().getEndtime());
                            tvResultTitle.setText("兼职发布成功");
                            tvResultQueren.setText("确认完成");
                        } else if (response.body().getData().getFlag() == 2) {
                            tvResultTitle.setText("兼职发布失败");
                            tvResultQueren.setText("重新支付");
                        } else if (response.body().getData().getFlag() == 3) {//充值成功
                            tvResultTotal.setText(response.body().getData().getTotal_price());
                            tvResultPayment.setText(response.body().getData().getPayment());
                            tvResultOutTradeNo.setText(response.body().getData().getOut_trade_no());
                            tvResultTransaction.setText(response.body().getData().getTransaction_id());
                            tvResultAddtimer.setText(response.body().getData().getAddtime());
                            tvResultEndtime.setText(response.body().getData().getEndtime());
                            tvResultTitle.setText("充值成功");
                            tvResultQueren.setText("确认完成");
                        } else if (response.body().getData().getFlag() == 4) {//充值失败
                            tvResultTitle.setText("充值失败");
                            tvResultQueren.setText("重新充值");
                        } else if (response.body().getData().getFlag() == 5) {//vip开通成功
                            tvResultTotal.setText(response.body().getData().getTotal_price());
                            tvResultPayment.setText(response.body().getData().getPayment());
                            tvResultOutTradeNo.setText(response.body().getData().getOut_trade_no());
                            tvResultTransaction.setText(response.body().getData().getTransaction_id());
                            tvResultAddtimer.setText(response.body().getData().getAddtime());
                            tvResultEndtime.setText(response.body().getData().getEndtime());
                            tvResultTitle.setText("开通成功");
                            tvResultQueren.setText("确认完成");
                        } else if (response.body().getData().getFlag() == 6) {//vip开通失败
                            tvResultTitle.setText("开通失败");
                            tvResultQueren.setText("重新开通");
                        }
                    }
                }

                @Override
                public void onFailure(Call<WrapperRspEntity<Result>> call, Throwable t) {

                }
            });
        }

    }

    @OnClick(R.id.tv_result_queren)
    public void onViewClicked() {
        if (code == 1) {//发布成功
            //确认完成
            ActivityUtils.startActivityAndClear(ResultActivity.this, MainActivity.class);
        } else if (code == 2) {//发布失败
            //确认完成
            finish();
        } else if (code == 3) {//充值成功
            ActivityUtils.startActivityAndClear(ResultActivity.this, MyWalletActivity.class);

        } else if (code == 4) {//充值失败
            finish();
        } else if (code == 5) {//vip开通成功
//            ActivityUtils.startActivityAndClear(ResultActivity.this, VipActivity.class);
        } else if (code == 6) {//vip开通失败
            finish();
        }

    }
}
