package com.qigaikj.parttimejob.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.FriendDetails;
import com.qigaikj.parttimejob.bean.NewMessage;
import com.qigaikj.parttimejob.bean.New_Version;
import com.qigaikj.parttimejob.bean.Share;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.fragment.Fragment1;
import com.qigaikj.parttimejob.fragment.Fragment2;
import com.qigaikj.parttimejob.fragment.Fragment4;
import com.qigaikj.parttimejob.fragment.Fragment5;
import com.qigaikj.parttimejob.manager.UpdateManager;
import com.qigaikj.parttimejob.util.DeviceUtils;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.PopupMenuUtil;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.LoadDlialog;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;
import io.rong.push.common.RongException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity implements RongIM.UserInfoProvider, LocationSource, AMapLocationListener {
    public static final int HOME_FRAGMENT = 0;
    public static final int MESSAGE_FRAGMENT = 1;
    public static final int MINE_FRAGMENT = 2;
    public static final int SHOPPING = 3;
    public static final int Mine = 4;
    @BindView(R.id.rl_main_layout)
    FrameLayout rlMainLayout;
    @BindView(R.id.rb_home1)
    RadioButton rbHome1;
    @BindView(R.id.rb_home2)
    RadioButton rbHome2;
    @BindView(R.id.rb_home5)
    RadioButton rbHome5;
    @BindView(R.id.rb_home3)
    RelativeLayout rbHome3;
    @BindView(R.id.rb_home4)
    RadioButton rbHome4;
    @BindView(R.id.rg_activity_main)
    RadioGroup rgActivityMain;
    Fragment1 fragment1;
//    Fragment1_item fragment1;
    Fragment2 fragment2;
    //    Fragment3 fragment3;
    Fragment4 fragment4;
    Fragment5 fragment5;
    TextView tvMainShoppNumber;
    TextView tvMainMessgNumber;
    private FragmentManager messageFragment;
    private IUnReadMessageObserver iUnReadMessageObserver = new IUnReadMessageObserver() {
        @Override
        public void onCountChanged(int i) {
            if (i > 0) {
                tvMainShoppNumber.setVisibility(View.VISIBLE);
            } else {
                tvMainShoppNumber.setVisibility(View.INVISIBLE);
            }
        }
    };

    /**
     * 融云消息接收，及初始化
     */
    private void initRongMessage() {
        final Conversation.ConversationType[] conversationTypes = {Conversation.ConversationType.PRIVATE, Conversation.ConversationType.DISCUSSION,
                Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.PUBLIC_SERVICE, Conversation.ConversationType.APP_PUBLIC_SERVICE};
        RongIM.getInstance().addUnReadMessageCountChangedObserver(iUnReadMessageObserver, conversationTypes);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDetail() {
//        ImmersionBar.with(this).transparentStatusBar().init();
        //创建fragment
        initFragment();
        //初始化控件
        initView();

//        setStatusBarColor(R.color.transparent);

        rbHome1.setChecked(true);

        requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, new PermissionRunnable() {
            @Override
            public void allowable() {//获取成功
                //开始定位
                if (mlocationClient == null) {
                    //初始化定位
                    mlocationClient = new AMapLocationClient(getApplicationContext());
                    //初始化定位参数
                    mLocationOption = new AMapLocationClientOption();
                    //设置定位回调监听
                    mlocationClient.setLocationListener(MainActivity.this);
                    //设置为高精度定位模式
                    mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                    //设置定位参数
                    mlocationClient.setLocationOption(mLocationOption);
                    // 设置是否只定位一次,默认为false
                    mLocationOption.setOnceLocation(true);
                    // 设置定位间隔,单位毫秒,默认为2000ms
//            mLocationOption.setInterval(2000);
                    // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                    // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                    // 在定位结束后，在合适的生命周期调用onDestroy()方法
                    // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                    mlocationClient.startLocation();//启动定位
                }

            }

            @Override
            public void disallowable() {//获取失败
                Toast.makeText(getApplicationContext(), "需打开定位权限", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        LoginRongYun();
        initRongMessage();//消息监听
        listener = new BaseUiListener();
        try {
            RongPushClient.checkManifest(this);
        } catch (RongException e) {
            e.printStackTrace();
            LogUtils.i("错误"+e.toString());
        }

    }

    /**
     * 登陆融云
     */
    public void LoginRongYun() {
        String tocken = SharedPreferencesHelper.getInstance(getApplicationContext()).getString(SharedPreferencesHelper.RONYUN_TOKEN, "");
        if (tocken != null && !tocken.equals("")) {//容云登陆
            RongIM.connect(tocken, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
            LogUtils.i("本地获取到" + tocken);
        } else {
//            RongIM.getInstance().logout();//融云退出
        }
    }

    /**
     * 销毁页面
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(iUnReadMessageObserver);
        LoadDlialog.onDestroy();
    }

    @Override
    protected void initTitleView() {
        hidetitle();
    }

    /**
     * 标题栏设置
     */
    public void initView() {
        rlMainLayout = (FrameLayout) findViewById(R.id.rl_main_layout);
        rbHome1 = (RadioButton) findViewById(R.id.rb_home1);
        rbHome2 = (RadioButton) findViewById(R.id.rb_home2);
        rbHome3 = (RelativeLayout) findViewById(R.id.rb_home3);
        rbHome4 = (RadioButton) findViewById(R.id.rb_home4);
        rbHome5 = (RadioButton) findViewById(R.id.rb_home5);
        rgActivityMain = (RadioGroup) findViewById(R.id.rg_activity_main);

        tvMainShoppNumber = (TextView) findViewById(R.id.tv_main_shopp_number);
        tvMainMessgNumber = (TextView) findViewById(R.id.tv_main_messg_number);

        rgActivityMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home1:
                        showFragment(HOME_FRAGMENT);
                        break;
                    case R.id.rb_home2:
                        showFragment(MESSAGE_FRAGMENT);
                        break;
                    case R.id.rb_home3:
//                        showFragment(MINE_FRAGMENT);
                        break;
                    case R.id.rb_home4:
                        showFragment(SHOPPING);
                        break;
                    case R.id.rb_home5:
                        showFragment(Mine);
                        break;
                }
            }
        });
    }

    /**
     * 创建 fragment
     */
    public void initFragment() {
        messageFragment = getSupportFragmentManager();
        fragment1 = new Fragment1();
//        fragment1 = new Fragment1_item();
        fragment2 = new Fragment2();
//        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        fragment5 = new Fragment5();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //
        RongIM.setUserInfoProvider(this, true);
        //设置消息列表页面走融云默认页面
        RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {

                return false;
            }

            @Override
            public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
                return false;
            }
        });

//        Update();//检查版本更新
//        getNewMessage();//获取回复消息数量

    }

    public void showFragment(int fragmentid) {
        // 开启一个Fragment事务
        final FragmentTransaction transaction = messageFragment.beginTransaction();
        hideFragments(transaction);
        switch (fragmentid) {
            case HOME_FRAGMENT:
                if (!fragment1.isAdded()) {
                    // 如果homeFragmen没有添加到事物中，就添加进去
                    transaction.add(R.id.rl_main_layout, fragment1);
                    Log.i("asdf", "未添加");
                } else {
                    // 如果homeFragment已存在，则直接将它显示出来
                    transaction.show(fragment1);
                }
                break;
            case MESSAGE_FRAGMENT:
                if (!fragment2.isAdded()) {
                    // 如果homeFragmen没有添加到事物中，就添加进去
                    transaction.add(R.id.rl_main_layout, fragment2);
                } else {
                    transaction.show(fragment2);
                }
                break;
//            case MINE_FRAGMENT:
//                if (!fragment3.isAdded()) {
//                    // 如果homeFragmen没有添加到事物中，就添加进去
//                    transaction.add(R.id.rl_main_layout, fragment3);
//                } else {
//                    // 如果homeFragment已存在，则直接将它显示出来
//                    transaction.show(fragment3);
//                }
//                break;
            case SHOPPING:
                if (!fragment4.isAdded()) {
                    // 如果homeFragmen没有添加到事物中，就添加进去
                    transaction.add(R.id.rl_main_layout, fragment4);
                } else {
                    // 如果homeFragment已存在，则直接将它显示出来
                    transaction.show(fragment4);
                }
                break;

            case Mine:
                if (!fragment5.isAdded()) {
                    // 如果homeFragmen没有添加到事物中，就添加进去
                    transaction.add(R.id.rl_main_layout, fragment5);
                } else {
                    // 如果homeFragment已存在，则直接将它显示出来
                    transaction.show(fragment5);
                }
                break;
        }
        transaction.commit();

    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (fragment1 != null && fragment1.isAdded()) {
            transaction.hide(fragment1);
        }
        if (fragment2 != null && fragment2.isAdded()) {
            transaction.hide(fragment2);
        }
//        if (fragment3 != null && fragment3.isAdded()) {
//            transaction.hide(fragment3);
//        }
        if (fragment4 != null && fragment4.isAdded()) {
            transaction.hide(fragment4);
        }
        if (fragment5 != null && fragment5.isAdded()) {
            transaction.hide(fragment5);
        }
    }

    @OnClick({R.id.rb_home1, R.id.rb_home2, R.id.rb_home3, R.id.rb_home4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_home1:
                break;
            case R.id.rb_home2:
                break;
            case R.id.rb_home3:
                PopupMenuUtil.getInstance()._show(MainActivity.this, rbHome3);
                break;
            case R.id.rb_home4:
                break;
        }
    }

    /**
     * 按下返回按钮时提示是否退出seyo+
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Onkey = false;
        }
    };
    private boolean Onkey = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (Onkey) {
                finish();
                RongIM.getInstance().disconnect();
            } else {
                Onkey = true;
                Toast.makeText(MainActivity.this, "再按一次退出乞丐兼职", Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessageDelayed(0, 3000);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private Double d1, d2;
    private Float f;
    private String city;

    /**
     * @param s
     * @return
     */
    @Override
    public UserInfo getUserInfo(final String s) {
//        String uid = SharedPreferencesHelper.getInstance(MainActivity.this).getString(SharedPreferencesHelper.USER_ID, "");
        String uid = sharedPreferencesHelper.getString(SharedPreferencesHelper.USER_ID, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getFriendsDetails(uid, s).enqueue(new Callback<WrapperRspEntity<FriendDetails>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<FriendDetails>> call, Response<WrapperRspEntity<FriendDetails>> response) {
               /* if (response.body().getStatus() == RetrofitManager.CODE) {
                    LogUtils.i("获取好友信息" + response.body().getData().getName());
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(s, response.body().getData().getNickname(), Uri.parse(response.body().getData().getLogo())));
                }*/
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<FriendDetails>> call, Throwable t) {

            }
        });
        return null;
    }

    //高德定位
    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null && amapLocation.getErrorCode() == 0) {
            LogUtils.i("经度" + amapLocation.getLongitude() + "纬度" + amapLocation.getLatitude());
            LogUtils.i("定位城市" + amapLocation.getCity() + "定位地址" + amapLocation.getAddress());
            LogUtils.i("POI" + amapLocation.getPoiName());
            sharedPreferencesHelper.putString(SharedPreferencesHelper.LONG, String.valueOf(amapLocation.getLongitude()));
            sharedPreferencesHelper.putString(SharedPreferencesHelper.LAT, String.valueOf(amapLocation.getLatitude()));
            city = amapLocation.getCity();
            mlocationClient.stopLocation();
            //如果有之前选择的地址，设置为之前的地址，如果没有显示定位结果
            String mycity = sharedPreferencesHelper.getString(SharedPreferencesHelper.CITY, "");
            if (mycity != null && !mycity.equals("")) {
                fragment1.setAddress(mycity);
            } else {
                fragment1.setAddress(city);
                sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, city);
            }
            //设置定位城市
            sharedPreferencesHelper.putString(SharedPreferencesHelper.LOCATION_CITY, city);
        } else {
            city = sharedPreferencesHelper.getString(SharedPreferencesHelper.CITY, "");
            fragment1.setAddress(city);
            String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
            LogUtils.e("AmapErr", errText);
        }

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
        //移除定位
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 检查更新
     */
    private void Update() {
        RetrofitManager.getInstance().createReq(BlogService.class).getNew_version().enqueue(new Callback<WrapperRspEntity<New_Version>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<New_Version>> call, Response<WrapperRspEntity<New_Version>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    int code = Integer.valueOf(response.body().getData().getVersion());
                    int mycode = DeviceUtils.getVersionCode(getApplicationContext());
                    if (code != 0 && mycode != 0) {
                        if (code > mycode) {//服务器版本号大于本地版本号
                            new UpdateManager(MainActivity.this).checkUpdate(false, code,response.body().getData().getDown_url(),response.body().getData().getExplain());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<New_Version>> call, Throwable t) {

            }
        });
    }

    /**
     * 获取未读回复
     */
    public void getNewMessage() {
        String tocken = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        if (tocken != null && !tocken.equals("")) {
            LogUtils.i("获取到tocken--------------------------------》");
            RetrofitManager.getInstance().createReq(BlogService.class).getNewMessage(tocken).enqueue(new Callback<WrapperRspEntity<NewMessage>>() {
                @Override
                public void onResponse(Call<WrapperRspEntity<NewMessage>> call, Response<WrapperRspEntity<NewMessage>> response) {
                    if (response.body().getStatus() == RetrofitManager.CODE) {
                        if (Integer.valueOf(response.body().getData().getNum()) > 0) {
                            tvMainMessgNumber.setVisibility(View.VISIBLE);
                        } else {
                            tvMainMessgNumber.setVisibility(View.INVISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<WrapperRspEntity<NewMessage>> call, Throwable t) {

                }
            });
        } else {
            tvMainShoppNumber.setVisibility(View.INVISIBLE);
            tvMainMessgNumber.setVisibility(View.INVISIBLE);
        }
    }

    // 实现回调 IUiListener
    //调用SDK已经封装好的接口时，例如：登录、快速支付登录、应用分享、应用邀请等接口，需传入该回调的实例。
    public class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            getNews_share();
        }

        @Override
        public void onError(UiError e) {
//            Toast.makeText(getApplicationContext(), "onError:code:" + e.errorCode + ", msg:"
//                    + e.errorMessage + ", detail:" + e.errorDetail, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_SHORT).show();
        }
    }

    public static BaseUiListener listener;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
    }

    /**
     * 分享次数加一
     */
    public void getNews_share() {
        String id = sharedPreferencesHelper.getString(SharedPreferencesHelper.FXID, "");
        if (id != null && !id.equals("")) {
            RetrofitManager.getInstance().createReq(BlogService.class).getnews_share(id).enqueue(new Callback<WrapperRspEntity<Share>>() {
                @Override
                public void onResponse(Call<WrapperRspEntity<Share>> call, Response<WrapperRspEntity<Share>> response) {
                    if (response.body().getStatus() == 200) {
                        //将分享过后的id置为空
                        sharedPreferencesHelper.putString(SharedPreferencesHelper.FXID, "");
                    }
                }
                @Override
                public void onFailure(Call<WrapperRspEntity<Share>> call, Throwable t) {

                }
            });
        }
    }
}
