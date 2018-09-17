package com.qigaikj.parttimejob.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.MyWalletAdapter;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.Money;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.RetrofitCallback;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/11/18/018.
 * 我的钱包页面
 */

public class MyWalletActivity extends BaseActivity {
    @BindView(R.id.lv_my_wallet_list)
    ListView lvMyWalletList;
    @BindView(R.id.tv_my_wallet_cz)
    TextView tvMyWalletCz;
    @BindView(R.id.tv_my_wallet_tx)
    TextView tvMyWalletTx;
    @BindView(R.id.tvTitleText)
    TextView tvTitleText;
    @BindView(R.id.tvRightText)
    TextView tvRightText;
    @BindView(R.id.tvMoney)
    TextView tvMoney;
    private MyWalletAdapter myWalletAdapter;
    private List<Money.DetailBean> myWalletList;

    private ImageView ivLeft;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void initDetail() {
       /* View walltitleview = LayoutInflater.from(this).inflate(R.layout.activity_my_wallet_title, null);
        if (walltitleview != null) {
            tvMoney = (TextView) walltitleview.findViewById(R.id.tvMoney);
            ivLeft = (ImageView) walltitleview.findViewById(R.id.ivLeft);
            tvRightText = (TextView) walltitleview.findViewById(R.id.tvRightText);
            lvMyWalletList.addHeaderView(walltitleview);
        }

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWalletActivity.this.finish();
            }
        });
        tvRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(MyWalletActivity.this, MyWalletDetailsActivity.class);
            }
        });*/
//        hidetitle();
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
        setStatusBarColor(Color.BLACK);
        lvMyWalletList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position != 0) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("mid", myWalletList.get(position - 1).getMid());
//                    ActivityUtils.startActivityForBundleData(MyWalletActivity.this, MyWalletDetailsActivity.class, bundle);
//                }
            }
        });
    }

    @Override
    protected void initTitleView() {
        hidetitle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getMoneyList();
    }

    /**
     * 获取我的钱包列表页面
     */
    public void getMoneyList() {
        String tocken = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        if (tocken != null && !tocken.equals("")) {
            RetrofitManager.getInstance().createReq(BlogService.class).getMoneyList(tocken).enqueue(new RetrofitCallback<Money>(this) {
                @Override
                public void onRes(Call<WrapperRspEntity<Money>> call, Response<WrapperRspEntity<Money>> response) {
                    if (response.body().getStatus() == RetrofitManager.CODE) {
                        tvMoney.setText(response.body().getData().getMoney());
                        myWalletList.clear();
                        myWalletList.addAll(response.body().getData().getDetail());
                        myWalletAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onErr(Call<WrapperRspEntity<Money>> call, Throwable t) {

                }
            });
        }
    }

    @OnClick({R.id.tv_my_wallet_cz, R.id.tv_my_wallet_tx,R.id.ivLeft, R.id.tvRightText})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_wallet_cz://充值
                ActivityUtils.startActivity(MyWalletActivity.this, RechargeActivity.class);
                break;
            case R.id.tv_my_wallet_tx://提现
                ActivityUtils.startActivity(MyWalletActivity.this, DepositActivity.class);
                break;
            case R.id.ivLeft:
                MyWalletActivity.this.finish();
                break;
            case R.id.tvRightText:
                ActivityUtils.startActivity(MyWalletActivity.this, MyWalletDetailsActivity.class);
                break;
        }
    }

}
