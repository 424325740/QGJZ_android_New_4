package com.qigaikj.parttimejob.activity.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;

import org.json.JSONObject;

import butterknife.BindView;

public class ZhuanQianActivity extends BaseActivity {

    @BindView(R.id.web)
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web.loadUrl("http://ceshi.qigaikj.com/index.php/Homepage/Strategy");

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_zhuan_qian;
    }

    @Override
    protected void initDetail() {

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
        getTvTextTitle().setText("赚钱攻略");
    }

    /**
     * 微信分享\
     */
    public void WxShare(){

    }
}
