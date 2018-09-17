package com.qigaikj.parttimejob.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.FragmentAdapter;
import com.qigaikj.parttimejob.adapter.HomeSearchAdapter;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.HomeSearchBean;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.eventbus.MessageEvent;
import com.qigaikj.parttimejob.fragment.FragmentOne;
import com.qigaikj.parttimejob.fragment.FragmentThree;
import com.qigaikj.parttimejob.fragment.FragmentTwo;
import com.qigaikj.parttimejob.fragment.JianzhiFragmen;
import com.qigaikj.parttimejob.fragment.TaYueFragmen;
import com.qigaikj.parttimejob.fragment.YueTaFragmen;
import com.qigaikj.parttimejob.ropdownmenu.DropdownButton;
import com.qigaikj.parttimejob.ropdownmenu.DropdownItemObject;
import com.qigaikj.parttimejob.ropdownmenu.DropdownListView;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeSearchActivity extends BaseActivity implements DropdownListView.Container {

    @BindView(R.id.iv_finsh)
    ImageView ivFinsh;
    @BindView(R.id.ed_search)
    EditText edSearch;
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
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private boolean state = false;
    Animation dropdown_in, dropdown_out, dropdown_mask_out;
    private DropdownListView currentDropdownList;
    private List<DropdownItemObject> chooseOneData = new ArrayList<>();//选择项1
    private List<DropdownItemObject> chooseTwoData = new ArrayList<>();//选择项2
    private List<DropdownItemObject> chooseThreeData = new ArrayList<>();//选择项3
    private View view1;
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<Fragment> fragmentList;
    private List<HomeSearchBean.DataBean> homeSearchBeans = new ArrayList<>();
    InputMethodManager manager;//输入法管理器
    private YueTaFragmen fragmentOne;
    private TaYueFragmen fragmentTwo;
    private JianzhiFragmen fragmentThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        date();
        search();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edSearch, 0);
        getHomeListAll();
        view1 = View.inflate(HomeSearchActivity.this, R.layout.shaixuan, null);
        dropdownThree.button.setText("筛选");
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });
        //Tablayout点击事件
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * EdText点击回车能搜索
     */
    private void search() {
        edSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //先隐藏键盘
                    if (manager.isActive()) {
                        manager.hideSoftInputFromWindow(edSearch.getApplicationWindowToken(), 0);
                    }
                    //自己需要的操作
                    String title = edSearch.getText().toString().trim();
//                    getData(key);
                    Intent intent=new Intent(HomeSearchActivity.this,SearchtoActivity.class);
                    intent.putExtra("title",title);
                    startActivity(intent);
                }
                //记得返回false
                return false;
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initDetail() {

    }

    @Override
    protected void initTitleView() {
        hidetitle();
    }

    @OnClick(R.id.iv_finsh)
    public void onViewClicked() {
        finish();
    }

    /**
     * 搜索数据请求
     */
    public void getData(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        fragmentOne.setArguments(bundle);
        fragmentTwo.setArguments(bundle);
        fragmentThree.setArguments(bundle);
      /*  String token = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeSearch(token, title, "3").enqueue(new Callback<HomeSearchBean>() {
            @Override
            public void onResponse(Call<HomeSearchBean> call, Response<HomeSearchBean> response) {
                homeSearchBeans.add(response.body().getData());
                HomeSearchBean.DataBean data = response.body().getData();

            }

            @Override
            public void onFailure(Call<HomeSearchBean> call, Throwable t) {

            }
        });*/
    }
    /**
     * Tablayout
     */
    private void date() {
        //为tablayout添加内容
        title.add("约TA");
        title.add("TA约我");
        title.add("兼职");

        //tablayout中的标题下划线的颜色
        tab.setSelectedTabIndicatorColor((Color.parseColor("#fd6a6a")));
        fragmentList = new ArrayList<>();
        fragmentOne = new YueTaFragmen();
        fragmentTwo = new TaYueFragmen();
        fragmentThree = new JianzhiFragmen();
        fragmentList.add(fragmentOne);
        fragmentList.add(fragmentTwo);
        fragmentList.add(fragmentThree);


        FragmentAdapter myPagerAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList, title);
        tab.setTabsFromPagerAdapter(myPagerAdapter);
        viewpager.setAdapter(myPagerAdapter);
        tab.setupWithViewPager(viewpager);


    }

    /**
     * 给下拉菜单赋值
     */
    private void getHomeListAll() {
        dropdown_in = AnimationUtils.loadAnimation(HomeSearchActivity.this, R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(HomeSearchActivity.this, R.anim.dropdown_out);
        dropdown_mask_out = AnimationUtils.loadAnimation(HomeSearchActivity.this, R.anim.dropdown_mask_out);
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
//                getHomeYueTa();//获取首页约TA全部列表
            } else if (id == 2) {
//                getHomeTaYue();//获取首页TA约我数据
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
