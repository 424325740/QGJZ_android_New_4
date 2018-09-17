package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/2/002.
 * 暂时webview页面
 */

public class WebWiewActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView webView;
    private GestureDetector gestureDetector;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initDetail() {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        String url = getIntent().getStringExtra("url");
        LogUtils.i("网页地址：" + url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    setTvTextTitle(title);
                    getTvTextTitle().setVisibility(View.VISIBLE);
                }
            }
        });
        WebSettings webSettings = webView.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);
        gestureDetector = new GestureDetector(WebWiewActivity.this,
                onGestureListener);
        webView.loadUrl(url);
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.onPause();
        webView.destroy();
    }

    // 重载滑动事件
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        gestureDetector.onTouchEvent(evt);
        return super.onTouchEvent(evt);
    }

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();

            if (y > 100) {
                // 右滑 事件
                Toast.makeText(WebWiewActivity.this, "上滑动2", Toast.LENGTH_LONG).show();
            } else if (y < -100) {
                // 左滑事件
                Toast.makeText(WebWiewActivity.this, "下滑动2", Toast.LENGTH_LONG).show();
            }

            return true;
        }
    };
}
