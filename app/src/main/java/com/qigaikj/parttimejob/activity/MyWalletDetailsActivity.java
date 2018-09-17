package com.qigaikj.parttimejob.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.MyWalletAdapter;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.Money;
import com.qigaikj.parttimejob.bean.WalletDetails;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/4/004.
 * 钱包转账详情
 */

public class MyWalletDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_wall_details_total)
    TextView tvWallDetailsTotal;
    @BindView(R.id.tv_wall_details_payment)
    TextView tvWallDetailsPayment;
    @BindView(R.id.tv_wall_details_out_trade_no)
    TextView tvWallDetailsOutTradeNo;
    @BindView(R.id.tv_wall_details_transaction)
    TextView tvWallDetailsTransaction;
    @BindView(R.id.tv_wall_details_addtimer)
    TextView tvWallDetailsAddtimer;
    @BindView(R.id.tv_wall_details_endtime)
    TextView tvWallDetailsEndtime;

    @BindView(R.id.lv_my_wallet_list)
    ListView lvMyWalletList;
    private MyWalletAdapter myWalletAdapter;
    private List<Money.DetailBean> myWalletList;

    private String mid;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mywalletdetails;
    }

    @Override
    protected void initDetail() {
//        mid = getIntent().getStringExtra("mid");
        myWalletList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Money.DetailBean bean = new Money.DetailBean();
            bean.setTitle("白嫖");
            bean.setMoney("10086");
            bean.setTime("2018.07.07");
            myWalletList.add(bean);
        }
        myWalletAdapter = new MyWalletAdapter(myWalletList);
        lvMyWalletList.setAdapter(myWalletAdapter);
    }

    @Override
    protected void initTitleView() {
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTvTextTitle().setText("明细");
        getTvTextTitle().setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getWalletDetails(mid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 获取收支明细详情
     */
    public void getWalletDetails(String mid) {
        RetrofitManager.getInstance().createReq(BlogService.class).getMoneyDetails(mid).enqueue(new Callback<WrapperRspEntity<WalletDetails>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<WalletDetails>> call, Response<WrapperRspEntity<WalletDetails>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    tvWallDetailsPayment.setText(response.body().getData().getPayment());
                    tvWallDetailsOutTradeNo.setText(response.body().getData().getOut_trade_no());
                    tvWallDetailsTransaction.setText(response.body().getData().getTransaction_id());
                    tvWallDetailsAddtimer.setText(response.body().getData().getAddtime());
                    tvWallDetailsEndtime.setText(response.body().getData().getEndtime());
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<WalletDetails>> call, Throwable t) {

            }
        });
    }
}
