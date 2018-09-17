package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.Code;
import com.qigaikj.parttimejob.bean.Deposit;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.RetrofitCallback;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.TimeButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.qigaikj.parttimejob.util.EtUtils.setRegion;
import static com.qigaikj.parttimejob.util.EtUtils.setRegion;

/**
 * Created by Administrator on 2017/11/21/021.
 * 支付提现页面
 */

public class DepositActivity extends BaseActivity {
    @BindView(R.id.rl_deposit_wx)
    RelativeLayout rlDepositWx;
    @BindView(R.id.rl_deposit_zfb)
    RelativeLayout rlDepositZfb;
    @BindView(R.id.et_deposit_money)
    EditText etDepositMoney;
    @BindView(R.id.et_deposit_code)
    EditText etDepositCode;
    @BindView(R.id.btn_deposit_cod)
    TimeButton btnDepositCod;
    @BindView(R.id.tv_deposit_comment)
    TextView tvDepositComment;
    @BindView(R.id.iv_deposit_wx)
    ImageView ivDepositWx;
    @BindView(R.id.iv_deposit_zfb)
    ImageView ivDepositZfb;
    private String DEPOSIT_TYPE = "0";//默认 1：微信提现，2：支付宝提现
    private double money;//剩余金额

    @Override
    protected int getContentViewId() {
        return R.layout.activity_deposit;
    }

    @Override
    protected void initDetail() {
        etDepositMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        setRegion(etDepositMoney, 0.01, 9999);
        btnDepositCod.setBackgroundResource(R.drawable.bt_code_background);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getDeposit();
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
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("提现");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_deposit_wx, R.id.rl_deposit_zfb, R.id.btn_deposit_cod, R.id.tv_deposit_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_deposit_wx://微信支付
                ivDepositWx.setImageResource(R.mipmap.icon_payment_yes);
                ivDepositZfb.setImageResource(R.mipmap.icon_payment_no);
                DEPOSIT_TYPE = "2";
                break;
            case R.id.rl_deposit_zfb://支付宝支付
                switch (isZfbtype) {
                    case 1://未绑定
                        ActivityUtils.startActivity(DepositActivity.this, BindingActivity.class);
                        break;
                    case 2://已绑定
                        ivDepositWx.setImageResource(R.mipmap.icon_payment_no);
                        ivDepositZfb.setImageResource(R.mipmap.icon_payment_yes);
                        DEPOSIT_TYPE = "1";
                        break;
                }

                break;
            case R.id.btn_deposit_cod://获取验证码
                getCode();
                break;
            case R.id.tv_deposit_comment://确认提交
                getUserCash();
                break;
        }
    }

    private int isZfbtype = 0;//未获取到信息1:未绑定  2：已绑定

    /**
     * 获取提现结果页数据
     */
    public void getDeposit() {
        String token = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        if (token == null || token.equals("")) {
            return;
        }

//        RetrofitManager.getInstance().createReq(BlogService.class).getDeposit(token).enqueue(new RetrofitCallback<Deposit>(this) {
//            @Override
//            public void onRes(Call<WrapperRspEntity<Deposit>> call, Response<WrapperRspEntity<Deposit>> response) {
//                if (response.body().getStatus() == RetrofitManager.CODE) {
//                    money = Double.valueOf(response.body().getData().getMoney());
//                    if (response.body().getData().getZfb_accounts() == null || response.body().getData().getZfb_accounts().equals("") || response.body().getData().getZfb_realname() == null || response.body().getData().getZfb_realname().equals("")) {
//                        isZfbtype = 1;
//                    } else {
//                        isZfbtype = 2;
//                    }
//                }
//            }
//            @Override
//            public void onErr(Call<WrapperRspEntity<Deposit>> call, Throwable t) {
//
//            }
//        });
    }

    /**
     * 获取验证码
     */
    private String phone = "";
    private String code = "";
    private String code2 = "";//后台返回的验证码

    public void getCode() {
        phone = sharedPreferencesHelper.getString(SharedPreferencesHelper.PHONE, "");
        if (phone != null && !phone.equals("")) {
            RetrofitManager.getInstance().createReq(BlogService.class).getCode(phone).enqueue(new Callback<WrapperRspEntity<Code>>() {
                @Override
                public void onResponse(Call<WrapperRspEntity<Code>> call, Response<WrapperRspEntity<Code>> response) {
                    if (response.body().getStatus() == RetrofitManager.CODE) {
                        btnDepositCod.startTime();
                        code2 = response.body().getData().getVerificationCode();
                    }
                    showToastShort(response.body().getMsg());
                }

                @Override
                public void onFailure(Call<WrapperRspEntity<Code>> call, Throwable t) {

                }
            });
        }
    }

    public void getUserCash() {
        code = etDepositCode.getText().toString().trim();
        if (code != null && code.equals("")) {
            showToastShort("请输入验证码");
            return;
        }
        if (!code.equals(code2)) {
            showToastShort("验证码输入有误");
            return;
        }
        String etmoney = etDepositMoney.getText().toString().trim();
        if (etmoney == null || etmoney.equals("")) {
            showToastShort("请输入提现金额");
            return;
        }
        String token = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        if (token == null || token.equals("")) {
            return;
        }
        Double m = Double.valueOf(etmoney);
        LogUtils.i("提现金额：" + m);
        LogUtils.i("账户余额：" + money);
        if (m > money) {
            showToastShort("提现金额不能大于账户余额");
            return;
        }
        RetrofitManager.getInstance().createReq(BlogService.class).getUserCash(token, etmoney, DEPOSIT_TYPE, code).enqueue(new RetrofitCallback<Deposit>(this) {
            @Override
            public void onRes(Call<WrapperRspEntity<Deposit>> call, Response<WrapperRspEntity<Deposit>> response) {

            }
            @Override
            public void onErr(Call<WrapperRspEntity<Deposit>> call, Throwable t) {

            }
        });
    }
}
