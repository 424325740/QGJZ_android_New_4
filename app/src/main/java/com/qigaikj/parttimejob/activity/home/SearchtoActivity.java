package com.qigaikj.parttimejob.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.HomeSearchTayueDataAdapter;
import com.qigaikj.parttimejob.adapter.SearchAdapter;
import com.qigaikj.parttimejob.adapter.SearchJianZhiDataAdapter;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.HomeSearchBean;
import com.qigaikj.parttimejob.bean.HomeSearchTayeuBean;
import com.qigaikj.parttimejob.bean.HomeSearchYuetaBean;
import com.qigaikj.parttimejob.ropdownmenu.DropdownButton;
import com.qigaikj.parttimejob.ropdownmenu.DropdownItemObject;
import com.qigaikj.parttimejob.ropdownmenu.DropdownListView;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchtoActivity extends BaseActivity implements DropdownListView.Container {


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
    @BindView(R.id.iv_finsh)
    ImageView ivFinsh;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.lv_YueTA)
    RecyclerView lvYueTA;
    @BindView(R.id.mask)
    View mask;

    private View view1;
    private boolean state = false;
    private List<DropdownItemObject> chooseOneData = new ArrayList<>();//选择项1
    private List<DropdownItemObject> chooseTwoData = new ArrayList<>();//选择项2
    private List<DropdownItemObject> chooseThreeData = new ArrayList<>();//选择项3
    Animation dropdown_in, dropdown_out, dropdown_mask_out;
    private List<HomeSearchBean.DataBean> homeSearchBeans = new ArrayList<>();
    private List<HomeSearchYuetaBean.DataBean> dataBeans = new ArrayList<>();
    private List<HomeSearchTayeuBean.DataBean> list = new ArrayList<>();
    private DropdownListView currentDropdownList;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getHomeListAll();
        view1 = View.inflate(SearchtoActivity.this, R.layout.shaixuan, null);
        dropdownThree.button.setText("筛选");
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
//        getData(title);
        getyuetaData(title);
//        gettayueData(title);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_searchto;
    }

    @Override
    protected void initDetail() {
        hidetitle();
    }

    @Override
    protected void initTitleView() {

    }
    @OnClick({R.id.iv_finsh, R.id.mask})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finsh:
                finish();
                break;
            case R.id.mask:
                hide();
                break;
        }
    }
    /**
     * 兼职搜索数据请求
     */
    public void getData(String title) {
        String token = SharedPreferencesHelper.getInstance(SearchtoActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeSearch(token, title, "3").enqueue(new Callback<HomeSearchBean>() {
            @Override
            public void onResponse(Call<HomeSearchBean> call, Response<HomeSearchBean> response) {
                for (int i = 0; i < response.body().getData().getWork().size(); i++) {
                    homeSearchBeans.add(response.body().getData());
                }
                SearchJianZhiDataAdapter dataAdapter= new SearchJianZhiDataAdapter(SearchtoActivity.this,homeSearchBeans);
                LinearLayoutManager manager=new LinearLayoutManager(SearchtoActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                lvYueTA.setLayoutManager(manager);
                lvYueTA.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<HomeSearchBean> call, Throwable t) {

            }
        });
    }
    /**
     * 约TA搜索数据请求
     */
    public void getyuetaData(String title) {
        String token = SharedPreferencesHelper.getInstance(SearchtoActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeSearchYueta(token, title, "1").enqueue(new Callback<HomeSearchYuetaBean>() {
            @Override
            public void onResponse(Call<HomeSearchYuetaBean> call, Response<HomeSearchYuetaBean> response) {
                for (int i = 0; i < response.body().getData().getSkill().size(); i++) {
                    dataBeans.add(response.body().getData());
                }
                SearchAdapter homeListAllDataAdapter = new SearchAdapter(SearchtoActivity.this, dataBeans);
                LinearLayoutManager manager=new LinearLayoutManager(SearchtoActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                lvYueTA.setLayoutManager(manager);
                lvYueTA.setAdapter(homeListAllDataAdapter);
            }

            @Override
            public void onFailure(Call<HomeSearchYuetaBean> call, Throwable t) {

            }
        });
    }
    /**
     * Ta约搜索数据请求
     */
    public void gettayueData(String title) {
        String token = SharedPreferencesHelper.getInstance(SearchtoActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeSearchTayue(token, title, "2").enqueue(new Callback<HomeSearchTayeuBean>() {
            @Override
            public void onResponse(Call<HomeSearchTayeuBean> call, Response<HomeSearchTayeuBean> response) {
                for (int i = 0; i < response.body().getData().getDemand().size(); i++) {
                    list.add(response.body().getData());
                }
                HomeSearchTayueDataAdapter homeListAllDataAdapter = new HomeSearchTayueDataAdapter(SearchtoActivity.this, list);
                LinearLayoutManager manager=new LinearLayoutManager(SearchtoActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                lvYueTA.setLayoutManager(manager);
                lvYueTA.setAdapter(homeListAllDataAdapter);
            }

            @Override
            public void onFailure(Call<HomeSearchTayeuBean> call, Throwable t) {

            }
        });
    }
    /**
     * 给下拉菜单赋值
     */
    private void getHomeListAll() {
        dropdown_in = AnimationUtils.loadAnimation(SearchtoActivity.this, R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(SearchtoActivity.this, R.anim.dropdown_out);
        dropdown_mask_out = AnimationUtils.loadAnimation(SearchtoActivity.this, R.anim.dropdown_mask_out);
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
                getyuetaData(title);
            } else if (id == 2) {
                gettayueData(title);
            } else {
                getData(title);
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


}
