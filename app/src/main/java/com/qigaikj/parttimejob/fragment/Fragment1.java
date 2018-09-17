package com.qigaikj.parttimejob.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.AddressActivity;
import com.qigaikj.parttimejob.activity.DetailsActivity;
import com.qigaikj.parttimejob.activity.WebWiewActivity;
import com.qigaikj.parttimejob.activity.home.ChongzhiActivity;
import com.qigaikj.parttimejob.activity.home.HomeSearchActivity;
import com.qigaikj.parttimejob.activity.home.JianzhiQXActivity;
import com.qigaikj.parttimejob.activity.home.MessageActivity;
import com.qigaikj.parttimejob.activity.home.QianDaoActivity;
import com.qigaikj.parttimejob.activity.home.YaoqingActivity;
import com.qigaikj.parttimejob.activity.home.ZhuanQianActivity;
import com.qigaikj.parttimejob.adapter.GrideViewHomePageTypeAdapter;
import com.qigaikj.parttimejob.adapter.GrideViewHomeWelfareAdapter;
import com.qigaikj.parttimejob.adapter.HomeListAllDataAdapter;
import com.qigaikj.parttimejob.adapter.HomeListDataAdapter;
import com.qigaikj.parttimejob.adapter.HomeListJianZhiDataAdapter;
import com.qigaikj.parttimejob.adapter.HomeListTaYueDataAdapter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.HomeAllYueTaBean;
import com.qigaikj.parttimejob.bean.HomeBannerList;
import com.qigaikj.parttimejob.bean.HomeGridViewWelfare;
import com.qigaikj.parttimejob.bean.HomeGrideViewBean;
import com.qigaikj.parttimejob.bean.HomeJianzhiBean;
import com.qigaikj.parttimejob.bean.HomeTaYue;
import com.qigaikj.parttimejob.bean.HomeTaYueAllBean;
import com.qigaikj.parttimejob.bean.HomeYueTa;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.menuList.GlideRoundTransform;
import com.qigaikj.parttimejob.ropdownmenu.DropdownButton;
import com.qigaikj.parttimejob.ropdownmenu.DropdownItemObject;
import com.qigaikj.parttimejob.ropdownmenu.DropdownListView;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/11/3/003.
 * 首页fragment
 */

public class Fragment1 extends Fragment implements DropdownListView.Container {

    @BindView(R.id.tvCity)//城市
            TextView tvCity;
    @BindView(R.id.llSearch)//搜索
            LinearLayout llSearch;
    @BindView(R.id.ivClassification)//分类
            ImageView ivClassification;


    @BindView(R.id.gridview)//获取首页分类
            GridView gridview;

    @BindView(R.id.gv_Welfare)//福利
            GridView gv_Welfare;

    @BindView(R.id.lv_YueTA)//约TA
            RecyclerView lv_YueTA;

//    @BindView(R.id.lv_YueTA)//约TA
//            MyListView lv_YueTA;

    View view;
    Unbinder unbinder;

    @BindView(R.id.dbOne)//下拉菜单
            DropdownButton chooseOne;
    @BindView(R.id.dbTwo)//下拉菜单
            DropdownButton chooseTwo;
    @BindView(R.id.dbThree)//下拉菜单
            DropdownButton chooseThree;

    @BindView(R.id.dropdownType)//
            DropdownListView dropdownOne;
    @BindView(R.id.dropdownLanguage)//
            DropdownListView dropdownTwo;
    @BindView(R.id.dropdownShaiXuan)//
            DropdownListView dropdownThree;

    @BindView(R.id.mask)//
            View mask;
    @BindView(R.id.banner)
    MZBannerView banner;
    @BindView(R.id.siren)
    ImageView siren;
    @BindView(R.id.bianwan)
    ImageView bianwan;
    @BindView(R.id.mashang)
    ImageView mashang;

    private List<DropdownItemObject> chooseOneData = new ArrayList<>();//选择项1
    private List<DropdownItemObject> chooseTwoData = new ArrayList<>();//选择项2
    private List<DropdownItemObject> chooseThreeData = new ArrayList<>();//选择项3
    Animation dropdown_in, dropdown_out, dropdown_mask_out;

    private DropdownListView currentDropdownList;

    /**
     * 首页BannerList数据列表
     */
    private List<HomeBannerList> AllbannerList = new ArrayList<>();
    private List<String> bannerList = new ArrayList<String>();
    /**
     * 首页GridView数据列表
     */
    private List<HomeGrideViewBean> grideViewData = new ArrayList<>();
    private GrideViewHomePageTypeAdapter gvHomePageTypeAdapter;
    /**
     * 首页福利数据
     */
    private List<List<HomeGridViewWelfare>> gvWelfareData = new ArrayList<>();
    private GrideViewHomeWelfareAdapter gvHomeWelfareAdapter;
    /**
     * 首页约TA数据
     */
    private List<HomeYueTa> lvYueTaDataAll = new ArrayList<>();
    private List<HomeTaYue> lvTaYueData = new ArrayList<>();
    private HomeListDataAdapter mAdapter;
    //    private DemoAdapter mAdapter;
    String userSkill = "";
    private DropdownItemObject itemObject;
    private int id;
    private View view1;
    private boolean state = false;
    List<HomeAllYueTaBean.DataBean> yueTaBeanList = new ArrayList<>();
    List<HomeTaYueAllBean.DataBean> dataBeans = new ArrayList<>();
    List<HomeJianzhiBean.DataBean> dataBeanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment1, container, false);
        unbinder = ButterKnife.bind(this, view);
        getHomeListAll();//获取首页列表数据
        view1 = View.inflate(getActivity(), R.layout.shaixuan, null);
        dropdownThree.button.setText("筛选");
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();//获取数据
        banner.setBannerPageClickListener((view, i) -> {
            Intent intent = new Intent(getActivity(), WebWiewActivity.class);
            intent.putExtra("url", AllbannerList.get(i).url);
            startActivity(intent);
        });
        llSearch.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), HomeSearchActivity.class);
            startActivity(intent);
        });
    }

    //MZBannerView适配器
    public static class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_card, null);
            mImageView = view.findViewById(R.id.iv_Images);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
//            mImageView.setImageURI(Uri.parse(data));
            Glide.with(context).load(data).transform(new GlideRoundTransform(context)).into(mImageView);

        }
    }

    @OnClick({R.id.ivClassification, R.id.tvCity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCity:
                ActivityUtils.startActivity(getActivity(), AddressActivity.class);
                break;
            case R.id.ivClassification:
                ActivityUtils.startActivity(getActivity(), MessageActivity.class);
                break;
        }
    }

    /**
     * 初始化数据
     */
    public void getData() {
        //清除数据缓存
        if (AllbannerList != null && AllbannerList.size() > 0) {
            AllbannerList.clear();
        }
        if (grideViewData != null && grideViewData.size() > 0) {
            grideViewData.clear();
        }
        getHomePageTypeData();//获取首页推荐分类
        getHomeBanner();//获取首页banner轮播图
        getHomeWelfare("1");//获取首页福利图片
        getHomeWelfare("2");//获取首页活动展示
        getHomeYueTaAll();

//        getHomeYueTa();//获取首页约TA全部列表
//        getHomeTaYue();//获取首页TA约我数据
    }

    /**
     * 给下拉菜单赋值
     */
    private void getHomeListAll() {
        dropdown_in = AnimationUtils.loadAnimation(getActivity(), R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(getActivity(), R.anim.dropdown_out);
        dropdown_mask_out = AnimationUtils.loadAnimation(getActivity(), R.anim.dropdown_mask_out);
        reset();
        chooseOneData.add(new DropdownItemObject("约TA", 1, "约TA"));
        chooseOneData.add(new DropdownItemObject("TA约我", 2, "TA约我"));
        chooseOneData.add(new DropdownItemObject("兼职", 3, "兼职"));
        dropdownOne.bind(chooseOneData, chooseOne, this, 0);

        chooseTwoData.add(new DropdownItemObject("最新发布", 0, "最新发布"));
        chooseTwoData.add(new DropdownItemObject("人气最高", 1, "人气最高"));
        chooseTwoData.add(new DropdownItemObject("距离最近", 2, "距离最近"));
        chooseTwoData.add(new DropdownItemObject("诚信值最高", 3, "诚信值最高"));
        chooseTwoData.add(new DropdownItemObject("价格最高", 4, "价格最高"));
        dropdownTwo.bind(chooseTwoData, chooseTwo, this, 0);

//        chooseThreeData.add(new DropdownItemObject("筛选", 0, ""));
        dropdownThree.bind(chooseThreeData, chooseThree, this, 2);
        dropdown_mask_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (currentDropdownList == null) {
                    reset();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 显示下拉菜单
     *
     * @param view
     */
    public void show(DropdownListView view) {
        if (currentDropdownList != null) {
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_out);
            currentDropdownList.setVisibility(View.GONE);
            currentDropdownList.button.setChecked(false);
        }
        currentDropdownList = view;
        mask.clearAnimation();
        mask.setVisibility(View.VISIBLE);
        currentDropdownList.clearAnimation();
        currentDropdownList.startAnimation(dropdown_in);
        currentDropdownList.setVisibility(View.VISIBLE);
        currentDropdownList.button.setChecked(true);
        if (view == dropdownThree) {
            if (state == false) {
                currentDropdownList.linearLayout.addView(view1);
                state = true;
            }
        }
    }

    /**
     * 隐藏
     */
    public void hide() {
        if (currentDropdownList != null) {
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_out);
            currentDropdownList.button.setChecked(false);
            mask.clearAnimation();
            mask.startAnimation(dropdown_mask_out);
        }
        currentDropdownList = null;
    }

    /**
     * 筛选下拉菜单是那个进行判断
     *
     * @param view
     */
    @SuppressLint("ResourceType")
    public void onSelectionChanged(DropdownListView view) {
        if (view == dropdownOne) {
            int id = view.current.getId();
            if (id == 1) {
                getHomeYueTaAll();
//                getHomeYueTa();//获取首页约TA全部列表
            } else if (id == 2) {
//                getHomeTaYue();//获取首页TA约我数据
                getHomeTaYueAll();
            } else {
                getJianzhi();//获取首页兼职页面
            }
        }
    }


    /**
     * 设置动画
     */
    void reset() {
        chooseOne.setChecked(false);
        chooseTwo.setChecked(false);
        chooseThree.setChecked(false);

        dropdownOne.setVisibility(View.GONE);
        dropdownTwo.setVisibility(View.GONE);
        dropdownThree.setVisibility(View.GONE);
        mask.setVisibility(View.GONE);

        dropdownOne.clearAnimation();
        dropdownTwo.clearAnimation();
        dropdownThree.clearAnimation();
        mask.clearAnimation();
    }

    /**
     * 设置城市名
     *
     * @param address\\
     */
    public String LOCATION_CITY = "location_city";//当前定位城市
    public String LONG = "Longitude";//经度
    public String LAT = "Latitude";//纬度

    public void setAddress(String address) {
        if (address != null && !address.equals("")) {
            LOCATION_CITY = address;
            tvCity.setText(address);
        }
    }

    /**
     * 刷新数据
     */
    @Override
    public void onResume() {
        super.onResume();
        banner.start();
        LOCATION_CITY = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.CITY, "");
        LAT = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.LAT, "");
        LONG = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.LONG, "");
        Log.i("asd", "LOCATION_CITY = " + LOCATION_CITY);
        if (!TextUtils.isEmpty(LOCATION_CITY)) {
            tvCity.setText("" + LOCATION_CITY);
        } else {
            tvCity.setText("定位失败");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 获取首页banner数据
     */
    private void getHomeBanner() {
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeBannerList().enqueue(new Callback<WrapperRspEntity<List<HomeBannerList>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<HomeBannerList>>> call, Response<WrapperRspEntity<List<HomeBannerList>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (response.body().getData().size() > 0) {
                        AllbannerList.addAll(response.body().getData());
                        for (int i = 0; i < AllbannerList.size(); i++) {
                            bannerList.add(AllbannerList.get(i).pic);
                        }
                        banner.setPages(bannerList, new MZHolderCreator<BannerViewHolder>() {
                            @Override
                            public BannerViewHolder createViewHolder() {
                                return new BannerViewHolder();
                            }
                        });
                        if (AllbannerList.size() < 2) {
//                            banner.isAutoPlay(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<List<HomeBannerList>>> call, Throwable t) {

            }
        });
    }

    /**
     * 获取首页分类gridview数据
     */
    public void getHomePageTypeData() {
        RetrofitManager.getInstance().createReq(BlogService.class).getHomePageType().enqueue(new Callback<WrapperRspEntity<List<HomeGrideViewBean>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<HomeGrideViewBean>>> call, Response<WrapperRspEntity<List<HomeGrideViewBean>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    grideViewData.addAll(response.body().getData());
                    gvHomePageTypeAdapter = new GrideViewHomePageTypeAdapter(grideViewData, getActivity());
                    //通知适配器刷新数据
                    gvHomePageTypeAdapter.notifyDataSetChanged();
                    gridview.setAdapter(gvHomePageTypeAdapter);
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<List<HomeGrideViewBean>>> call, Throwable t) {

            }
        });
    }

    /**
     * 获取首页福利gridview数据
     */
    public void getHomeWelfare(String state) {
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeWelfare(state).enqueue(new Callback<WrapperRspEntity<List<HomeGridViewWelfare>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<HomeGridViewWelfare>>> call, Response<WrapperRspEntity<List<HomeGridViewWelfare>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if ("1".equals(state)) {
                        if (gvWelfareData != null) {
                            gvWelfareData.clear();
                        }
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            gvWelfareData.add(response.body().getData());
                        }
                        gvHomeWelfareAdapter = new GrideViewHomeWelfareAdapter(gvWelfareData, getActivity());
                        gv_Welfare.setAdapter(gvHomeWelfareAdapter);
                        gvHomeWelfareAdapter.setItemClick(new GrideViewHomeWelfareAdapter.ItemClickCallBack() {
                            @Override
                            public void onItemClick(int pos) {
                                if (pos == 0) {
                                    ActivityUtils.startActivity(getActivity(), ChongzhiActivity.class);
                                } else if (pos == 1) {
                                    ActivityUtils.startActivity(getActivity(), QianDaoActivity.class);
                                } else if (pos == 2) {
                                    ActivityUtils.startActivity(getActivity(), YaoqingActivity.class);
                                } else {
                                    ActivityUtils.startActivity(getActivity(), ZhuanQianActivity.class);
                                }
                            }
                        });
                    } else {
                        Glide.with(getActivity()).load(response.body().getData().get(0).pic).into(siren);//头像

                        Glide.with(getActivity()).load(response.body().getData().get(1).pic).into(bianwan);//头像
                        Glide.with(getActivity()).load(response.body().getData().get(2).pic).into(mashang);//头像
                    }
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<List<HomeGridViewWelfare>>> call, Throwable t) {

            }
        });
    }

    /**
     * 获取首页底部TA约数据
     */
    public void getHomeTaYueAll() {
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeTaYueAll("", "", "").enqueue(new Callback<HomeTaYueAllBean>() {
            @Override
            public void onResponse(Call<HomeTaYueAllBean> call, Response<HomeTaYueAllBean> response) {
                if (dataBeans != null) {
                    dataBeans.clear();
                }
                for (int i = 0; i < response.body().getData().getDemand().size(); i++) {
                    dataBeans.add(response.body().getData());
                }

//                response.body().getData();
//                    lvTaYueData.addAll(response.body().getData());
                //通知适配器刷新数据
                HomeListTaYueDataAdapter mAdapter = new HomeListTaYueDataAdapter(getActivity(), dataBeans);
                initAdapter();
                lv_YueTA.setAdapter(mAdapter);
                mAdapter.setClickCallBack(new HomeListTaYueDataAdapter.ItemClickCallBack() {
                    @Override
                    public void onItemClick(int pos) {
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra("zhuangtai", "应邀");
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<HomeTaYueAllBean> call, Throwable t) {

            }
        });
    }

    /**
     * 获取首页底部约TA数据
     */
    public void getHomeYueTaAll() {
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeYueTaAll().enqueue(new Callback<HomeAllYueTaBean>() {
            @Override
            public void onResponse(Call<HomeAllYueTaBean> call, Response<HomeAllYueTaBean> response) {
                if (yueTaBeanList != null) {
                    yueTaBeanList.clear();
                }
                for (int i = 0; i < response.body().getData().getSkill().size(); i++) {
                    yueTaBeanList.add(response.body().getData());
                }

                HomeListAllDataAdapter homeListAllDataAdapter = new HomeListAllDataAdapter(getActivity(), yueTaBeanList);
                initAdapter();
                lv_YueTA.setAdapter(homeListAllDataAdapter);
                homeListAllDataAdapter.setClickCallBack(new HomeListAllDataAdapter.ItemClickCallBack() {
                    @Override
                    public void onItemClick(int pos) {
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra("zhuangtai", "约TA");
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<HomeAllYueTaBean> call, Throwable t) {

            }
        });
    }

    /**
     * 获取首页约TA数据
     */
    public void getHomeYueTa() {
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeYueTa("1", "10").enqueue(new Callback<WrapperRspEntity<List<HomeYueTa>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<HomeYueTa>>> call, Response<WrapperRspEntity<List<HomeYueTa>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (lvYueTaDataAll != null) {
                        lvYueTaDataAll.clear();
                    }
                    lvYueTaDataAll.addAll(response.body().getData());
                    //通知适配器刷新数据
                    mAdapter = new HomeListDataAdapter(getActivity(), lvYueTaDataAll);
                    initAdapter();
                    lv_YueTA.setAdapter(mAdapter);//设置adapter
                    mAdapter.setClickCallBack(new HomeListDataAdapter.ItemClickCallBack() {
                        @Override
                        public void onItemClick(int pos) {
                            Intent intent = new Intent(getActivity(), DetailsActivity.class);
                            intent.putExtra("zhuangtai", "约TA");
                            intent.putExtra("uname", lvYueTaDataAll.get(pos).uname);
                            intent.putExtra("logo", lvYueTaDataAll.get(pos).logo);
                            for (int i = 0; i < lvYueTaDataAll.get(pos).skill_pic.size(); i++) {
//                                intent.putExtra("userskill",lvYueTaDataAll.get(i).skill_pic.get(i));
                            }
                            intent.putExtra("gender", lvYueTaDataAll.get(pos).gender);
                            intent.putExtra("state", lvYueTaDataAll.get(pos).state);
                            intent.putExtra("phone", lvYueTaDataAll.get(pos).phone_state);
                            intent.putExtra("wxstate", lvYueTaDataAll.get(pos).wx_state);
                            intent.putExtra("intorduce", lvYueTaDataAll.get(pos).introduce);
                            startActivity(intent);

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<List<HomeYueTa>>> call, Throwable t) {

            }
        });
    }

    /**
     * 他约我数据
     */
    public void getHomeTaYue() {
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeTaYue("1", "10").enqueue(new Callback<WrapperRspEntity<List<HomeTaYue>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<HomeTaYue>>> call, Response<WrapperRspEntity<List<HomeTaYue>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (lvTaYueData != null) {
                        lvTaYueData.clear();
                    }
                    lvTaYueData.addAll(response.body().getData());
                    //通知适配器刷新数据
//                    HomeListTaYueDataAdapter mAdapter = new HomeListTaYueDataAdapter(getActivity(), lvTaYueData);
                    initAdapter();
//                    lv_YueTA.setAdapter(mAdapter);//设置adapter

                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<List<HomeTaYue>>> call, Throwable t) {

            }
        });
    }

    /**
     * 兼职页面
     */
    private void getJianzhi() {
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeJianzhi("", "", "").enqueue(new Callback<HomeJianzhiBean>() {
            @Override
            public void onResponse(Call<HomeJianzhiBean> call, Response<HomeJianzhiBean> response) {
                if (dataBeanList != null) {
                    dataBeanList.clear();
                }
                for (int i = 0; i < response.body().getData().getWork().size(); i++) {
                    dataBeanList.add(response.body().getData());
                }
                //通知适配器刷新数据
                HomeListJianZhiDataAdapter mAdapter = new HomeListJianZhiDataAdapter(getActivity(), dataBeanList);
                initAdapter();
                lv_YueTA.setAdapter(mAdapter);//设置adapter
                mAdapter.setClickCallBack(new HomeListJianZhiDataAdapter.ItemClickCallBack() {
                    @Override
                    public void onItemClick(int pos, String wid) {
                        Intent intent = new Intent(getActivity(), JianzhiQXActivity.class);
                        intent.putExtra("zhuangtai", "报名");
                        intent.putExtra("wid", wid);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<HomeJianzhiBean> call, Throwable t) {

            }
        });
    }

    /**
     * 设置RecyclerView属性
     */
    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv_YueTA.setLayoutManager(layoutManager);

    }

    class DemoAdapter extends BaseQuickAdapter<HomeYueTa, BaseViewHolder> {
        public DemoAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        public void convert(BaseViewHolder baseViewHolder, HomeYueTa homeYueTa) {

            if (!TextUtils.isEmpty(userSkill)) {
                userSkill = "";
            }

            Glide.with(mContext).load(homeYueTa.logo).transform(new GlideRoundTransform(getActivity())).into((ImageView) baseViewHolder.getView(R.id.iv_UserLogo));
            baseViewHolder.setText(R.id.tv_UserName, homeYueTa.uname);
            for (int i = 0; i < homeYueTa.user_skill.size(); i++) {
                userSkill += homeYueTa.user_skill.get(i) + "    ";
            }
            baseViewHolder.setText(R.id.tv_User_Occupation, userSkill);
            baseViewHolder.setText(R.id.tv_User_info, homeYueTa.introduce);
            if ("1".equals(homeYueTa.gender)) {
                baseViewHolder.setVisible(R.id.icon_User_1, true);
                baseViewHolder.setImageResource(R.id.icon_User_1, R.mipmap.male);
            } else {
                baseViewHolder.setVisible(R.id.icon_User_1, true);
                baseViewHolder.setImageResource(R.id.icon_User_1, R.mipmap.famel);
            }
            if ("1".equals(homeYueTa.state)) {
                baseViewHolder.setVisible(R.id.icon_User_2, true);
                baseViewHolder.setImageResource(R.id.icon_User_2, R.mipmap.icon_authentication_real_name);
            }
            if ("1".equals(homeYueTa.phone_state)) {
                baseViewHolder.setVisible(R.id.icon_User_3, true);
                baseViewHolder.setImageResource(R.id.icon_User_3, R.mipmap.icon_authentication_phone);
            }
            if ("1".equals(homeYueTa.wx_state)) {
                baseViewHolder.setVisible(R.id.icon_User_4, true);
                baseViewHolder.setImageResource(R.id.icon_User_4, R.mipmap.icon_authentication_wx);
            }

//            ImageViewData ivData = new ImageViewData();
            for (int i = 0; i < homeYueTa.skill_pic.size(); i++) {
                switch (i) {
                    case 0:
                        baseViewHolder.getView(R.id.ivUserPic_1).setVisibility(View.VISIBLE);
                        baseViewHolder.getView(R.id.ivUserPic_2).setVisibility(View.INVISIBLE);
                        baseViewHolder.getView(R.id.ivUserPic_3).setVisibility(View.INVISIBLE);
                        Glide.with(mContext)
                                .load(homeYueTa.skill_pic.get(0))
                                .centerCrop()
                                .transform(new GlideRoundTransform(getActivity()))
                                .into((ImageView) baseViewHolder.getView(R.id.ivUserPic_1));
                        break;

                    case 1:
                        baseViewHolder.getView(R.id.ivUserPic_1).setVisibility(View.VISIBLE);
                        baseViewHolder.getView(R.id.ivUserPic_2).setVisibility(View.VISIBLE);
                        baseViewHolder.getView(R.id.ivUserPic_3).setVisibility(View.INVISIBLE);

                        Glide.with(mContext)
                                .load(homeYueTa.skill_pic.get(0))
                                .centerCrop()
                                .transform(new GlideRoundTransform(getActivity()))
                                .into((ImageView) baseViewHolder.getView(R.id.ivUserPic_1));

                        Glide.with(mContext)
                                .load(homeYueTa.skill_pic.get(1))
                                .centerCrop()
                                .transform(new GlideRoundTransform(getActivity()))
                                .into((ImageView) baseViewHolder.getView(R.id.ivUserPic_2));
                        break;

                    case 2:
                        baseViewHolder.getView(R.id.ivUserPic_1).setVisibility(View.VISIBLE);
                        baseViewHolder.getView(R.id.ivUserPic_2).setVisibility(View.VISIBLE);
                        baseViewHolder.getView(R.id.ivUserPic_3).setVisibility(View.VISIBLE);
                        Glide.with(mContext)
                                .load(homeYueTa.skill_pic.get(0))
                                .centerCrop()
                                .into((ImageView) baseViewHolder.getView(R.id.ivUserPic_1));
                        Glide.with(mContext)
                                .load(homeYueTa.skill_pic.get(1))
                                .centerCrop()
                                .into((ImageView) baseViewHolder.getView(R.id.ivUserPic_2));
                        Glide.with(mContext)
                                .load(homeYueTa.skill_pic.get(2))
                                .centerCrop()
                                .into((ImageView) baseViewHolder.getView(R.id.ivUserPic_3));
                        break;

                    default:
                        baseViewHolder.getView(R.id.ivUserPic_1).setVisibility(View.GONE);
                        baseViewHolder.getView(R.id.ivUserPic_2).setVisibility(View.GONE);
                        baseViewHolder.getView(R.id.ivUserPic_3).setVisibility(View.GONE);
                        break;
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
//        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
//        banner.stopAutoPlay();
    }
}