package com.qigaikj.parttimejob.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.FragmentAdapter;
import com.qigaikj.parttimejob.auth.BaseUIListener;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.IsGuanzhuBean;
import com.qigaikj.parttimejob.fragment.FragmentOne;
import com.qigaikj.parttimejob.fragment.FragmentThree;
import com.qigaikj.parttimejob.fragment.FragmentTwo;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.Config;
import com.qigaikj.parttimejob.util.DialogUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends BaseActivity {

    @BindView(R.id.iv_finsh)
    ImageView ivFinsh;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.fjyueta)
    TextView fjyueta;
    @BindView(R.id.User_avator)
    CircleImageView UserAvator;
    @BindView(R.id.guanzhu)
    LinearLayout guanzhu;
    @BindView(R.id.xingxing)
    ImageView xingxing;
    @BindView(R.id.goin)
    LinearLayout goin;
    @BindView(R.id.uname)
    TextView muname;
    @BindView(R.id.age)
    TextView mage;
    @BindView(R.id.adders)
    TextView madders;
    private ArrayList<Fragment> fragmentList;
    private FragmentOne fragmentOne;
    private FragmentTwo fragmentTwo;
    private FragmentThree fragmentThree;

    private ArrayList<String> title = new ArrayList<>();
    boolean flag = false;
    //WX
    private IWXAPI wxapi;
    private Tencent mTencent;
    private BaseUIListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date();
//        initView();
        Intent intent = getIntent();
        String zhuangtai = intent.getStringExtra("zhuangtai");
        String uname = intent.getStringExtra("uname");
        String logo = intent.getStringExtra("logo");
//        String userskill = intent.getStringExtra("userskill");
        String gender = intent.getStringExtra("gender");
        String state = intent.getStringExtra("state");
        String phone = intent.getStringExtra("phone");
        String wxstate = intent.getStringExtra("wxstate");
        String intorduce = intent.getStringExtra("intorduce");
//        muname.setText(uname);
//        UserAvator.setImageURI(Uri.parse(logo));
//        madders
        if (zhuangtai.equals("1")) {
            goin.setVisibility(View.GONE);
            return;
        }
        fjyueta.setText(zhuangtai);
    }

    @Override
    protected int getContentViewId() {
        listener = new BaseUIListener(DetailsActivity.this);
        mTencent = Tencent.createInstance(RetrofitManager.QQID, DetailsActivity.this);
        wxapi = WXAPIFactory.createWXAPI(this, RetrofitManager.WXID, false);
        return R.layout.activity_details;
    }

    @Override
    protected void initDetail() {
        hidetitle();
    }

    @Override
    protected void initTitleView() {

    }

    /**
     * Tablayout调节指示器大小
     */
    private void initView() {
        tab.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tab, 60, 60);
            }
        });
    }

    /**
     * Tablayout
     */
    private void date() {
        //为tablayout添加内容
        title.add("资料");
        title.add("动态");
        title.add("评价");

        //tablayout中的标题下划线的颜色
        tab.setSelectedTabIndicatorColor((Color.parseColor("#fd6a6a")));
        fragmentList = new ArrayList<>();
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();
        fragmentList.add(fragmentOne);
        fragmentList.add(fragmentTwo);
        fragmentList.add(fragmentThree);


        FragmentAdapter myPagerAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList, title);
        tab.setTabsFromPagerAdapter(myPagerAdapter);
        viewpager.setAdapter(myPagerAdapter);
        tab.setupWithViewPager(viewpager);


    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @OnClick({R.id.iv_finsh, R.id.guanzhu, R.id.zixun,R.id.jubao,R.id.fenxiang,R.id.pengyouquan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finsh:
                finish();
                break;
            case R.id.guanzhu:
                getIsGuanzhu();
                break;
            case R.id.zixun:
                ActivityUtils.startActivity(DetailsActivity.this, ZixunActivity.class);
                break;
            case R.id.jubao:
                ActivityUtils.startActivity(DetailsActivity.this, JuaoActivity.class);
                break;
            case R.id.fenxiang:
                DialogUtils.showSharedDialog(DetailsActivity.this, "http://ceshi.qigaikj.com/index.php/Users/FriendsInviting", new DialogUtils.SharedListener() {
                    @Override
                    public void sharedToWXFriend(String content) {//微信分享
                        sendToWeiXin("微信分享",content,"我是微信分享", BitmapFactory.decodeResource(getResources(),R.mipmap.btn_weixin),0);
                    }

                    @Override
                    public void sharedToWXFriendCircle(String content) {// 朋友圈分享
                        sendToWeiXin("微信分享",content,"我是微信分享", BitmapFactory.decodeResource(getResources(),R.mipmap.btn_weixin),1);
                    }

                    @Override
                    public void sharedToQQFriend(String content) {//qq分享
                        shareToQQ("恒富威停车","描述","http://ceshi.qigaikj.com/index.php/Users/FriendsInviting",Config.APP_LOGO_URL,2);
                    }
                });
                break;
            case R.id.pengyouquan:
                sendToWeiXin("微信分享","http://ceshi.qigaikj.com/index.php/Users/FriendsInviting","我是微信分享", BitmapFactory.decodeResource(getResources(),R.mipmap.btn_weixin),1);
                break;
        }
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
        mTencent.shareToQQ(DetailsActivity.this, params, listener);
    }
    /**
     * 关注取消关注接口请求
     */
    public void getIsGuanzhu(){
        String token = SharedPreferencesHelper.getInstance(DetailsActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getIsGuanzhu(token,"3","1").enqueue(new Callback<IsGuanzhuBean>() {
            @Override
            public void onResponse(Call<IsGuanzhuBean> call, Response<IsGuanzhuBean> response) {
                String is_like = response.body().getData().getIs_like();
                if (is_like.equals("1")){
                    Toast.makeText(DetailsActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                    xingxing.setBackground(getResources().getDrawable(R.mipmap.icon_guanzhu_pre));
                }else {
                    Toast.makeText(DetailsActivity.this, "取消关注", Toast.LENGTH_SHORT).show();
                    xingxing.setBackground(getResources().getDrawable(R.mipmap.icon_guanzhu_nor));
                }

            }

            @Override
            public void onFailure(Call<IsGuanzhuBean> call, Throwable t) {

            }
        });
    }
}
