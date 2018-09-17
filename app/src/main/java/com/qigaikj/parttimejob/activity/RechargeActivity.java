package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.WeiXing;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.EtUtils;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.PayResult;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/1/001.
 * 充值页面
 */

public class RechargeActivity extends BaseActivity {
    @BindView(R.id.iv_recharge_wx)
    ImageView ivRechargeWx;
    @BindView(R.id.iv_recharge_zfb)
    ImageView ivRechargeZfb;
    @BindView(R.id.et_recharge_money)
    EditText etRechargeMoney;
    @BindView(R.id.tv_recharge_zhifu)
    TextView tvRechargeZhifu;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initDetail() {
        etRechargeMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        EtUtils.setRegion(etRechargeMoney, 0.01, 9999);
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
        getTvTextTitle().setText("充值");
        getTvTextTitle().setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private String payType = "0";//0：默认未选择，1：微信支付 2：支付宝支付
    private String money;//订单金额

    @OnClick({R.id.iv_recharge_wx, R.id.iv_recharge_zfb, R.id.tv_recharge_zhifu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_recharge_wx:
                payType = "1";
                ivRechargeWx.setImageResource(R.mipmap.icon_payment_yes);
                ivRechargeZfb.setImageResource(R.mipmap.icon_payment_no);
                break;
            case R.id.iv_recharge_zfb:
                payType = "2";
                ivRechargeWx.setImageResource(R.mipmap.icon_payment_no);
                ivRechargeZfb.setImageResource(R.mipmap.icon_payment_yes);
                break;
            case R.id.tv_recharge_zhifu:
                if (payType != null && !payType.equals("")) {
                    if (payType.equals("1")) {
                        Production();
                    } else if (payType.equals("2")) {
                        ZhiFuBao();
                    }
                } else {
                    showToastShort("请选择支付方式");
                }
                break;
        }
    }

    /**
     * 获取微信支付所需参数
     */
    public void Production() {
        money = etRechargeMoney.getText().toString().trim();
        String tocken = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        if (tocken != null && !tocken.equals("") && money != null && !money.equals("")) {
            HashMap hashMap = new HashMap();
            hashMap.put("token", tocken);
            hashMap.put("money", money);
            hashMap.put("state", "2");
            RetrofitManager.getInstance().createReq(BlogService.class).getPaywechatPay(hashMap).enqueue(new Callback<WrapperRspEntity<WeiXing>>() {
                @Override
                public void onResponse(Call<WrapperRspEntity<WeiXing>> call, Response<WrapperRspEntity<WeiXing>> response) {
                    if (response.body().getStatus() == RetrofitManager.CODE) {
                        sharedPreferencesHelper.putString(SharedPreferencesHelper.ORDER, response.body().getData().getOrder());
                        WIXing(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<WrapperRspEntity<WeiXing>> call, Throwable t) {

                }
            });
        }

    }

    /**
     * 调用微信支付
     */
    public void WIXing(WeiXing weiXing) {
        IWXAPI iwxapi = null;
        iwxapi = WXAPIFactory.createWXAPI(this, RetrofitManager.WXID, true);
        iwxapi.registerApp(RetrofitManager.WXID);
        PayReq request = new PayReq();

        request.appId = weiXing.getAppid();//应用id

        request.partnerId = weiXing.getPartnerid();//商户号

        request.prepayId = weiXing.getPrepayid();//预支付交易会话id

        request.packageValue = "Sign=WXPay";//扩展字段

        request.nonceStr = weiXing.getNoncestr();//随机字符串

        request.timeStamp = String.valueOf(weiXing.getTimestamp());//时间戳

        request.sign = weiXing.getSign();//签名

        iwxapi.sendReq(request);


    }

    /**
     * 调用支付宝支付
     */
    private String zfb;
    private Runnable zfbrunnable = new Runnable() {
        @Override
        public void run() {
            PayTask payTask = new PayTask(RechargeActivity.this);
            String pay = payTask.pay(zfb, true);
            Message message = new Message();
            message.what = ZFB;
            message.obj = pay;
            handler.sendMessage(message);
        }
    };
    private final int ZFB = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ZFB:
//                    LogUtils.i("支付宝返回" + msg.obj);
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    LogUtils.i("支付宝返回状态码：" + resultInfo + "---------" + resultStatus);

//                    PayTask payTask = new PayTask(CashierActivity.this);
//                    String p = payTask.getVersion();
//                    LogUtils.i("支付宝支付返回：" + p);
                    if (resultStatus.equals("9000")) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("order_sn", order_sn);
//                        ActivityUtils.startActivityForBundleData(CashierActivity.this, Cashier_2Activity.class, bundle);
                    }
                    break;

            }
        }
    };

    public void ZhiFuBao() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", "");//用户id
        map.put("order_sn", String.valueOf(""));
        //支付宝支付
//        RetrofitManager.getInstance().createReq(BlogService.class).getZFBPay(map).enqueue(new Callback<WrapperRspEntity<Pay>>() {
//            @Override
//            public void onResponse(Call<WrapperRspEntity<Pay>> call, Response<WrapperRspEntity<Pay>> response) {
//                if (response.body().getStatus() == 200) {
//                    zfb = response.body().getData().getOrderStr();
//                    if (zfb != null && !zfb.equals("")) {
//                        Thread thread = new Thread(zfbrunnable);
//                        thread.start();
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WrapperRspEntity<Pay>> call, Throwable t) {
//
//            }
//        });
    }
}
