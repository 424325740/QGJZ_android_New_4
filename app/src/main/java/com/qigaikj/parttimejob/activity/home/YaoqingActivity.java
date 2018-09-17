package com.qigaikj.parttimejob.activity.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.DetailsActivity;
import com.qigaikj.parttimejob.auth.BaseUIListener;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.util.Config;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import org.json.JSONObject;

import butterknife.BindView;

public class YaoqingActivity extends BaseActivity {

    @BindView(R.id.web)
    WebView web;
    private IWXAPI wxapi;
    private Tencent instance;
    private BaseUIListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web.loadUrl("http://ceshi.qigaikj.com/index.php/Users/InvitingFriends");
        WebSettings webSettings = web.getSettings();
        webSettings.setDomStorageEnabled(true);
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置Web视图
        web.setWebViewClient(new webViewClient());
        // 支持js调用java   jsInAndroid相当于和H5端定义的协议
        web.addJavascriptInterface(new JSInterface(), "jsInAndroid");

        // 调用H5页面的方法  无参方法 getInfo方法名
        web.loadUrl("javascript:getInfo()");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_yqoqing_activitu;
    }

    @Override
    protected void initDetail() {

    }

    @Override
    protected void initTitleView() {
        listener = new BaseUIListener(YaoqingActivity.this);
        instance = Tencent.createInstance(RetrofitManager.QQID, YaoqingActivity.this);
        wxapi = WXAPIFactory.createWXAPI(this, RetrofitManager.WXID, false);
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("邀请好友");
    }
    class JSInterface {
        // info即H5的方法  name即H5返回的数据
        @JavascriptInterface
        public String info(String name) {
            try {
                JSONObject object = new JSONObject(name);
                String shareurl = object.optString("shareurl");
                String ste=name;
                Log.i("name",ste);
                if (ste.equals("{\"id\":\"1\"}")){
                    sendToWeiXin("微信分享","http://ceshi.qigaikj.com/index.php/Users/FriendsInviting","我是微信分享", BitmapFactory.decodeResource(getResources(),R.mipmap.btn_weixin),0);
                }else if(ste.equals("{\"id\":\"2\"}")){
                    sendToWeiXin("微信分享","http://ceshi.qigaikj.com/index.php/Users/FriendsInviting","我是微信分享", BitmapFactory.decodeResource(getResources(),R.mipmap.btn_weixin),1);
                }else {
                    shareToQQ("恒富威停车","描述","http://ceshi.qigaikj.com/index.php/Users/FriendsInviting", Config.APP_LOGO_URL,2);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return name;
        }
    }
    private class webViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title = view.getTitle();
            // 加载网页标题
            if (!TextUtils.isEmpty(title)) {
            }
        }
        //
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("launchagency")) {
            } else {
                view.loadUrl(url);
            }
            return true;
        }
    }
    /**
     * 官方参考文档地址：http://wiki.open.qq.com/index.php?title=Android_API%E8%B0%83%E7%94%A8%E8%AF%B4%E6%98%8E&=45038#1.13_.E5.88.86.E4.BA.AB.E6.B6.88.E6.81.AF.E5.88.B0QQ.EF.BC.88.E6.97.A0.E9.9C.80QQ.E7.99.BB.E5.BD.95.EF.BC.89
     *
     * @param title       分享的内容title
     * @param openUrl     点击分享内容打开的地址
     * @param description 分享item的描述信息 分享的消息摘要，最长40个字。
     * @param imgUrl      分享item的图片地址
     * @param shareType   1分享到QQ空间 2分享到QQ好友
     */
    public void shareToQQ(String title, String description, String openUrl, String imgUrl, int shareType) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//消息类型 图文用默认的
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);//标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);//描述信息
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, openUrl);//这条分享消息被好友点击后的跳转URL。
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);//分享图片的URL或者本地路径
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, shareType);//分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮
        instance.shareToQQ(YaoqingActivity.this, params, listener);
    }
    /**
     * @param title       分享的标题
     * @param openUrl     点击分享item打开的网页地址url
     * @param description 网页的描述
     * @param icon        分享item的图片
     * @param requestCode 0表示为分享到微信好友  1表示为分享到朋友圈 2表示微信收藏
     */
    public void sendToWeiXin(String title, String openUrl, String description, Bitmap icon, int requestCode) {
        //初始化一个WXWebpageObject对象，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = openUrl;
        //Y用WXWebpageObject对象初始化一个WXMediaMessage对象，填写标题、描述
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;//网页标题
        msg.description = description;//网页描述
        msg.setThumbImage(icon);
        //构建一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "supplier";
        req.message = msg;
        req.scene = requestCode;
        wxapi.sendReq(req);
    }
}

