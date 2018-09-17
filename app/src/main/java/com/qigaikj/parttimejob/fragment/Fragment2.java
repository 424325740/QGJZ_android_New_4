package com.qigaikj.parttimejob.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.DetailsActivity;
import com.qigaikj.parttimejob.adapter.GrideViewAdapter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.FJmessageBean;
import com.qigaikj.parttimejob.bean.NearListBean;
import com.qigaikj.parttimejob.bean.NearUserList;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.menuList.GlideRoundTransform;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.PopWindow;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.util.Util;
import com.qigaikj.parttimejob.view.HorizontalListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/11/3/003.
 * 附近fragment
 */

public class Fragment2 extends Fragment implements LocationSource, AMapLocationListener {

    @BindView(R.id.tv_Address)
    TextView tvAddress;
    @BindView(R.id.mscrollList)
    HorizontalListView mscrollList;
    @BindView(R.id.ll_User)
    LinearLayout llUser;
    public View view;
    @BindView(R.id.ll_Address)
    LinearLayout llAddress;
    @BindView(R.id.mapview)
    MapView mapview;
    @BindView(R.id.logo)
    CircleImageView ivUserLogo;
    @BindView(R.id.tv_UserName)
    TextView tvUserName;
    @BindView(R.id.tv_User_info)
    TextView tvUserInfo;
    @BindView(R.id.button)
    ImageButton button;
    @BindView(R.id.button1)
    ImageView button1;
    @BindView(R.id.button2)
    ImageView button2;
    @BindView(R.id.button3)
    ImageView button3;
    @BindView(R.id.yueta)
    Button yueta;
    @BindView(R.id.jineng)
    TextView jineng;
    @BindView(R.id.miaoshu)
    TextView miaoshu;
    @BindView(R.id.gender)
    ImageView gender;
    @BindView(R.id.wx_state)
    ImageView wxState;
    @BindView(R.id.state)
    ImageView state;
    @BindView(R.id.user_skill)
    TextView userSkill;
    private AMap aMap;
    private MapView mapView;
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private OnLocationChangedListener mListener = null;
    private boolean isFirstLoc = true;
    ObjectAnimator objAnimatorX;
    ObjectAnimator objAnimatorY;
    ObjectAnimator objAnimatorRotate;
    private PopupWindow popupwindow;
    // 子按钮列表
    private List<ImageView> buttonItems = new ArrayList<ImageView>(2);
    // 标识当前按钮弹出与否，1代表已经未弹出，-1代表已弹出
    private int flag = 1;
    private boolean flager = false;
    private boolean flagr = false;
    private boolean flage = false;
    private GrideViewAdapter nearListViewAdapter;
    private String s;
    private Intent intent;
    private PopWindow popWindow;
    private LatLng latLng;
    private List<Boolean> checkeds;
    private double latitude;
    private double longitude;
    private List<List<NearUserList.DataBean>> userList = new ArrayList<>();
    private ImageView iv_head;
    private View inflate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment2, container, false);
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.marker_layout, null);
        iv_head = inflate.findViewById(R.id.iv_head);
        ButterKnife.bind(this, view);
        checkeds = new ArrayList<>();
        checkeds.add(false);
        checkeds.add(true);
        checkeds.add(false);
        /**用户信息*/
        tvUserName = view.findViewById(R.id.tv_UserName);
        tvUserInfo = view.findViewById(R.id.tv_User_info);
        TextView tuijian = view.findViewById(R.id.tuijian);
        tvAddress = view.findViewById(R.id.tv_Address);
        mscrollList = view.findViewById(R.id.mscrollList);
        mapView = view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        popWindow = new PopWindow(getActivity());
        if (aMap == null) {
            aMap = mapView.getMap();
            getMap();
            obtionListviewData();
        }
        tuijian.setOnClickListener(view ->
                popWindow.showPopupWindow(view, nearListViewAdapter)
        );

        buttonItems.add(button1);
        buttonItems.add(button2);
        buttonItems.add(button3);
        getUserInfo();//获取用户数据
        return view;
    }

    @OnClick({R.id.tv_Address, R.id.et_frm1_search, R.id.ll_User, R.id.button, R.id.tuijian, R.id.yueta, R.id.button1, R.id.button2, R.id.button3, R.id.dingwei})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_Address:
                Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_LONG).show();
                break;
            case R.id.et_frm1_search:
//                ActivityUtils.startActivity(getActivity(), SearchActivity.class);
                break;
            case R.id.ll_User:
                s = yueta.getText().toString();
                intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("zhuangtai", s);
                startActivity(intent);
                break;
            case R.id.button:
                if (flag == 1) {
                    buttonAnimation(buttonItems, 190);
                    flag = -1;
                } else {
                    buttonAnimation(buttonItems, 190);
                    flag = 1;
                }
                break;
            case R.id.yueta:
                s = yueta.getText().toString();
                intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("zhuangtai", s);
                startActivity(intent);
                break;
            case R.id.button1:
                if (flager == false) {
                    button1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_jianzhi_pre));
                    button3.setImageDrawable(getResources().getDrawable(R.mipmap.btn_xuqiu));
                    button2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_jineng));
                    if (flag == 1) {
                        buttonAnimation(buttonItems, 190);
                        flag = -1;
                    } else {
                        buttonAnimation(buttonItems, 190);
                        flag = 1;
                    }
                    yueta.setText("去报名");
                    jineng.setText("兼职介绍");
                    miaoshu.setText("职责介绍");
                    flager = false;
                } else {
                    button1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_jianzhi));
                    flager = false;
                }
                break;
            case R.id.button2:
                if (flagr == false) {
                    button2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_jineng_pre));
                    button1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_jianzhi));
                    button3.setImageDrawable(getResources().getDrawable(R.mipmap.btn_xuqiu));
                    if (flag == 1) {
                        buttonAnimation(buttonItems, 190);
                        flag = -1;
                    } else {
                        buttonAnimation(buttonItems, 190);
                        flag = 1;
                    }
                    yueta.setText("去约TA");
                    jineng.setText("预约技能");
                    miaoshu.setText("技能描述");
                    flager = false;
                } else {
                    button2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_jineng));
                    flag = -1;
                    buttonAnimation(buttonItems, 190);
                    flager = false;
                }
                break;
            case R.id.button3:
                if (flage == false) {
                    button3.setImageDrawable(getResources().getDrawable(R.mipmap.btn_xuqiu_pre));
                    button1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_jianzhi));
                    button2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_jineng));
                    if (flag == 1) {
                        buttonAnimation(buttonItems, 190);
                        flag = -1;
                    } else {
                        buttonAnimation(buttonItems, 190);
                        flag = 1;
                    }
                    yueta.setText("去应邀");
                    jineng.setText("预约技能");
                    miaoshu.setText("技能要求");
                    flager = false;
                } else {
                    button3.setImageDrawable(getResources().getDrawable(R.mipmap.btn_xuqiu));
                    flager = false;
                }
                break;
            case R.id.dingwei:
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {//不在前端显示
        } else {//在前端显示
            LogUtils.i("刷新数据了");
//            getNewList();
        }
    }

    public void getMap() {
        UiSettings settings = aMap.getUiSettings();
        aMap.setLocationSource(this);//设置了定位的监听,这里要实现LocationSource接口
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(false);
        // 是否显示放大缩小按钮
        settings.setZoomControlsEnabled(false);
        aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(Color.argb(10, 20, 30, 40));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(10, 20, 30, 40));// 设置圆形的填充颜色
        aMap.setMyLocationStyle(myLocationStyle);
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity());
        //设置定位回调监听，这里要实现AMapLocationListener接口，AMapLocationListener接口只有onLocationChanged方法可以实现，用于接收异步返回的定位结果，参数是AMapLocation类型。
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                //获取纬度
                latitude = aMapLocation.getLatitude();
                //获取经度
                longitude = aMapLocation.getLongitude();
                aMapLocation.getAccuracy();//获取经度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                getUserInfo();//获取用户数据
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getCountry() + ""
                            + aMapLocation.getCity() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getDistrict() + ""
                            + aMapLocation.getStreet() + ""
                            + aMapLocation.getStreetNum());
                    Log.i("asd", "数据信息 = = " + aMapLocation.getCountry());
                    Log.i("asd", "数据信息 = = " + aMapLocation.getProvince());
                    Log.i("asd", "数据信息 = = " + aMapLocation.getCity());
                    Log.i("asd", "数据信息 = = " + aMapLocation.getDistrict());
                    Log.i("asd", "数据信息 = = " + aMapLocation.getStreet());
                    Log.i("asd", "数据信息 = = " + aMapLocation.getStreetNum());
                    tvAddress.setText(aMapLocation.getDistrict() + " " + aMapLocation.getStreet() + " " + aMapLocation.getStreetNum());
                    isFirstLoc = false;
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(getActivity(), "定位失败", Toast.LENGTH_LONG).show();
            }
            tvAddress.setText(aMapLocation.getDistrict() + " " + aMapLocation.getStreet() + " " + aMapLocation.getStreetNum());
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    /**
     * 获取附近用户数据
     */
    public void getUserInfo() {
        //lng=longitude+             lat=latitude+
        String token = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getNearUserList(token, "116.476677", "39.907382", "", "").enqueue(new Callback<NearUserList>() {

            private Bitmap bitmap;

            public void onResponse(Call<NearUserList> call, Response<NearUserList> response) {
                userList.add(response.body().getData());
                for (int i = 0; i < response.body().getData().size(); i++) {
                    String lat = response.body().getData().get(i).getLat();
                    String lng = response.body().getData().get(i).getLng();
                    String logo = response.body().getData().get(i).getLogo();
                    String uid = response.body().getData().get(i).getUid();
                    double v = Double.parseDouble(lat);
                    double v1 = Double.parseDouble(lng);
                    /**
                     *  绘制点遮盖物
                     */
                    LatLng latLng = new LatLng(v, v1);
                    aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
                    /**
                     *  设置自定义遮盖物
                     */
                    MarkerOptions markerOption = new MarkerOptions();
                    markerOption.position(latLng);
                    markerOption.title("北京市").snippet("首都");
                    markerOption.draggable(true);//设置Marker可拖动
                    Glide.with(getActivity())
                            .load(logo)
                            .into(new GlideDrawableImageViewTarget(iv_head) {
                                @Override
                                public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                                    super.onResourceReady(drawable, anim);
                                    bitmap = getViewBitmap(inflate);

                                }
                            });
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                   /* markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.mipmap.icon_yueta)));*/
                    // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                    markerOption.setFlat(true);//设置marker平贴地图效果
                    aMap.addMarker(markerOption);
                    aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            getFujinrenMessage(uid, lng, lat);
                            return true;
                        }
                    });
                }


//                }
            }

            @Override
            public void onFailure(Call<NearUserList> call, Throwable t) {
                //设置停止刷新
                LogUtils.i("返回错误" + t.toString());
            }
        });
    }

    /**
     * 附近人信息
     * @param uid
     * @param lng
     * @param lat
     */
    public void getFujinrenMessage(String uid, String lng, String lat) {
        String token = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getFujinren(token, uid, lng, lat).enqueue(new Callback<FJmessageBean>() {
            @Override
            public void onResponse(Call<FJmessageBean> call, Response<FJmessageBean> response) {
                llUser.setVisibility(View.VISIBLE);
                response.body().getData();
                Glide.with(getActivity()).load(response.body().getData().getLogo()).transform(new GlideRoundTransform(getActivity())).into(ivUserLogo);
                tvUserName.setText(response.body().getData().getUname());
                userSkill.setText(response.body().getData().getUser_skill().get(1));
                tvUserInfo.setText(response.body().getData().getIntroduce());
                String states = response.body().getData().getState();
                String wx_states = response.body().getData().getWx_state();
                if (states.equals("1")){
                    state.setVisibility(View.VISIBLE);
                }
                if (wx_states.equals("1")){
                    wxState.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<FJmessageBean> call, Throwable t) {

            }
        });
    }

    public Bitmap getViewBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return Bitmap.createBitmap(view.getDrawingCache());
    }

    /**
     * 获取推荐分类数据
     */
    public void obtionListviewData() {
        RetrofitManager.getInstance().createReq(BlogService.class).getNearList().enqueue(new Callback<WrapperRspEntity<List<NearListBean>>>() {
            public void onResponse(Call<WrapperRspEntity<List<NearListBean>>> call, Response<WrapperRspEntity<List<NearListBean>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (response.body().getData().size() > 0) {
                        nearListViewAdapter = new GrideViewAdapter(response.body().getData(), getContext());
                        nearListViewAdapter.itemClick(nearListBean -> {
                        });
                        nearListViewAdapter.notifyDataSetChanged();
//                        mscrollList.setAdapter(nearListViewAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<List<NearListBean>>> call, Throwable t) {
                //设置停止刷新
                LogUtils.i("返回错误" + t.toString());
            }

        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();//释放地图
        mLocationClient.onDestroy();//销毁定位客户端。
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("sys", "mf onSaveInstanceState");
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 按钮移动动画
     *
     * @params 子按钮列表
     * @params 弹出时圆形半径radius
     */
    public void buttonAnimation(List<ImageView> buttonList, int radius) {

        for (int i = 0; i < buttonList.size(); i++) {


            // 将按钮设为可见
            buttonList.get(i).setVisibility(View.VISIBLE);

            // 按钮在X、Y方向的移动距离
            float distanceX = (float) (flag * radius * (Math.cos(Util.getAngle(buttonList.size(), i))));
            float distanceY = -(float) (flag * radius * (Math.sin(Util.getAngle(buttonList.size(), i))));

            // X方向移动
            objAnimatorX = ObjectAnimator.ofFloat(buttonList.get(i), "x", buttonList.get(i).getX(), buttonList.get(i).getX() + distanceX);
            objAnimatorX.setDuration(200);
            objAnimatorX.setStartDelay(100);
            objAnimatorX.start();

            // Y方向移动
            objAnimatorY = ObjectAnimator.ofFloat(buttonList.get(i), "y", buttonList.get(i).getY(), buttonList.get(i).getY() + distanceY);
            objAnimatorY.setDuration(200);
            objAnimatorY.setStartDelay(100);
            objAnimatorY.start();

            // 按钮旋转
            objAnimatorRotate = ObjectAnimator.ofFloat(buttonList.get(i), "rotation", 0, 360);
            objAnimatorRotate.setDuration(200);
            objAnimatorY.setStartDelay(100);
            objAnimatorRotate.start();

            if (flag == -1) {
                objAnimatorX.addListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // TODO Auto-generated method stub
                        // 将按钮设为可见
                        for (int i = 0; i < buttonItems.size(); i++) {
                            buttonItems.get(i).setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                });
            }

        }
    }

}
