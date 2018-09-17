package com.qigaikj.parttimejob.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.qigaikj.parttimejob.activity.ResultActivity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, RetrofitManager.WXID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    //    wx201711271728432ff199f8080116896673
    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        LogUtils.i("微信支付回调" + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
                    ActivityUtils.startActivity(WXPayEntryActivity.this, ResultActivity.class);
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = "发送取消";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).

                            show();

                    finish();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = "发送被拒绝";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).

                            show();

                    finish();
                    break;
                default:
                    result = "发送返回";
                    Toast.makeText(this, result, Toast.LENGTH_LONG).

                            show();

                    finish();
                    break;
            }
        }
    }
}