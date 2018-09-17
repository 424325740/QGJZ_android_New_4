package com.qigaikj.parttimejob.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.FragmentAdapter;
import com.qigaikj.parttimejob.adapter.FragmentFabuAdapter;
import com.qigaikj.parttimejob.adapter.FragmentMyeAdapter;
import com.qigaikj.parttimejob.adapter.FragmentTayueAdapter;
import com.qigaikj.parttimejob.adapter.SpinerAdapter;
import com.qigaikj.parttimejob.fragment.dingdan.jianzhi.fabuAllfrag;
import com.qigaikj.parttimejob.fragment.dingdan.jianzhi.fabubaomingfrag;
import com.qigaikj.parttimejob.fragment.dingdan.jianzhi.fabujinxingfrag;
import com.qigaikj.parttimejob.fragment.dingdan.jianzhi.fabuwanchengfrag;
import com.qigaikj.parttimejob.fragment.dingdan.myjianzhi.myAllfrag;
import com.qigaikj.parttimejob.fragment.dingdan.myjianzhi.mybaomingfrag;
import com.qigaikj.parttimejob.fragment.dingdan.myjianzhi.myjinxingfrag;
import com.qigaikj.parttimejob.fragment.dingdan.myjianzhi.mywanchengfrag;
import com.qigaikj.parttimejob.fragment.dingdan.tayue.TayueAllfrag;
import com.qigaikj.parttimejob.fragment.dingdan.tayue.Tayuedaiyaofrag;
import com.qigaikj.parttimejob.fragment.dingdan.tayue.Tayuejinxingfrag;
import com.qigaikj.parttimejob.fragment.dingdan.tayue.Tayuewanchengfrag;
import com.qigaikj.parttimejob.fragment.dingdan.yueta.YuetaAllfrag;
import com.qigaikj.parttimejob.fragment.dingdan.yueta.YuetaYaoqingfrag;
import com.qigaikj.parttimejob.fragment.dingdan.yueta.Yuetajinxingfrag;
import com.qigaikj.parttimejob.fragment.dingdan.yueta.Yuetawanchengfrag;
import com.qigaikj.parttimejob.util.SpinerPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/3/003.
 * 订单
 */

public class Fragment4 extends Fragment implements SpinerAdapter.IOnItemSelectListener {


    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.lin)
    LinearLayout lin;
    @BindView(R.id.yueTaView)
    LinearLayout yueTaView;
    @BindView(R.id.yueTaTab)
    TabLayout yueTaTab;
    @BindView(R.id.yueTaVP)
    ViewPager yueTaVP;
    @BindView(R.id.taYueView)
    LinearLayout taYueView;
    @BindView(R.id.taYueTab)
    TabLayout taYueTab;
    @BindView(R.id.taYueVP)
    ViewPager taYueVP;
    @BindView(R.id.jianZhiView)
    LinearLayout jianZhiView;
    @BindView(R.id.jianZhiTab)
    TabLayout jianZhiTab;
    @BindView(R.id.jianZhiVP)
    ViewPager jianZhiVP;
    @BindView(R.id.fabuTab)
    TabLayout fabuTab;
    @BindView(R.id.fabuVP)
    ViewPager fabuVP;
    @BindView(R.id.fabujianzhi)
    LinearLayout fabujianzhi;

    private List<String> mListType = new ArrayList<String>();  //类型列表
    private SpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<Fragment> yueTaFragments;
    private ArrayList<Fragment> taYueFragments;
    private ArrayList<Fragment> jianZhiFragments;
    private ArrayList<Fragment> fabuJianzhiFragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment4, container, false);
        ButterKnife.bind(this, view);
        init();
        yueTa();
        //初始化数据
        mListType.add("约TA");
        mListType.add("TA约我");
        mListType.add("我做的兼职");
        mListType.add("发布的兼职");
        mAdapter = new SpinerAdapter(getActivity(), mListType);
        mAdapter.refreshData(mListType, 0);
        //初始化PopWindow
        mSpinerPopWindow = new SpinerPopWindow(getActivity());
        mSpinerPopWindow.setAdatper(mAdapter);
        mSpinerPopWindow.setItemListener(this);


        return view;
    }

    @OnClick(R.id.lin)
    public void onViewClicked() {
        showSpinWindow();//显示SpinerPopWindow
    }

    //设置PopWindow
    private void showSpinWindow() {
        //设置mSpinerPopWindow显示的宽度
        mSpinerPopWindow.setWidth(lin.getWidth());
        //设置显示的位置在哪个控件的下方
        mSpinerPopWindow.showAsDropDown(lin);
    }

    /**
     * SpinerPopWindow中的条目点击监听
     *
     * @param pos
     */
    @Override
    public void onItemClick(int pos) {

        String value = mListType.get(pos);
        tvValue.setText(value.toString());
        if (pos == 0) {
            yueTa();
        } else if (pos == 1) {
            TaYueData();
        } else if (pos == 2) {
            jianZhiData();
        } else {
            fabuData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 约TATablayout
     */
    private void yueTa() {
        title.clear();
        yueTaView.setVisibility(View.VISIBLE);
        taYueView.setVisibility(View.GONE);
        jianZhiView.setVisibility(View.GONE);
        fabujianzhi.setVisibility(View.GONE);
        //为tablayout添加内容
        title.add("全部订单");
        title.add("已邀请");
        title.add("进行中");
        title.add("已完成");
        //tablayout中的标题下划线的颜色
        yueTaTab.setSelectedTabIndicatorColor((Color.parseColor("#fd6a6a")));
        FragmentAdapter myPagerAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), yueTaFragments, title);
        if (null == yueTaVP.getAdapter()) {
            yueTaVP.setAdapter(myPagerAdapter);
            yueTaTab.setTabsFromPagerAdapter(myPagerAdapter);
            yueTaTab.setupWithViewPager(yueTaVP);
        }
        myPagerAdapter.notifyDataSetChanged();
    }

    /**
     * TA约我Tablayout
     */
    private void TaYueData() {
        title.clear();
        yueTaView.setVisibility(View.GONE);
        taYueView.setVisibility(View.VISIBLE);
        jianZhiView.setVisibility(View.GONE);
        fabujianzhi.setVisibility(View.GONE);
        //为tablayout添加内容
        title.add("全部订单");
        title.add("待邀请");
        title.add("进行中");
        title.add("已完成");
        //tablayout中的标题下划线的颜色
        taYueTab.setSelectedTabIndicatorColor((Color.parseColor("#fd6a6a")));
        FragmentTayueAdapter myPagerAdapter = new FragmentTayueAdapter(getActivity().getSupportFragmentManager(), taYueFragments, title);
        if (null == taYueVP.getAdapter()) {
            taYueVP.setAdapter(myPagerAdapter);
            taYueTab.setTabsFromPagerAdapter(myPagerAdapter);
            taYueTab.setupWithViewPager(taYueVP);
        }
        myPagerAdapter.notifyDataSetChanged();
    }

    /**
     * 我做的兼职Tablayout
     */
    private void jianZhiData() {
        title.clear();
        yueTaView.setVisibility(View.GONE);
        taYueView.setVisibility(View.GONE);
        jianZhiView.setVisibility(View.VISIBLE);
        fabujianzhi.setVisibility(View.GONE);
        //为tablayout添加内容
        title.add("全部订单");
        title.add("已报名");
        title.add("进行中");
        title.add("已完成");
        //tablayout中的标题下划线的颜色
        jianZhiTab.setSelectedTabIndicatorColor((Color.parseColor("#fd6a6a")));
        FragmentMyeAdapter myPagerAdapter = new FragmentMyeAdapter(getActivity().getSupportFragmentManager(), jianZhiFragments, title);
        if (null == jianZhiVP.getAdapter()) {
            jianZhiVP.setAdapter(myPagerAdapter);
            jianZhiTab.setTabsFromPagerAdapter(myPagerAdapter);
            jianZhiTab.setupWithViewPager(jianZhiVP);
        }
        myPagerAdapter.notifyDataSetChanged();
    }
    /**
     * 发布的兼职Tablayout
     */
    private void fabuData() {
        title.clear();
        yueTaView.setVisibility(View.GONE);
        taYueView.setVisibility(View.GONE);
        jianZhiView.setVisibility(View.GONE);
        fabujianzhi.setVisibility(View.VISIBLE);
        //为tablayout添加内容
        title.add("全部订单");
        title.add("已报名");
        title.add("进行中");
        title.add("已完成");
        //tablayout中的标题下划线的颜色
        fabuTab.setSelectedTabIndicatorColor((Color.parseColor("#fd6a6a")));
        FragmentFabuAdapter myPagerAdapter = new FragmentFabuAdapter(getActivity().getSupportFragmentManager(), fabuJianzhiFragments, title);
        if (null == fabuVP.getAdapter()) {
            fabuVP.setAdapter(myPagerAdapter);
            fabuTab.setTabsFromPagerAdapter(myPagerAdapter);
            fabuTab.setupWithViewPager(fabuVP);
        }
        myPagerAdapter.notifyDataSetChanged();
    }
    /**
     * 初始化Fragment
     */
    private void init() {
        yueTaFragments = new ArrayList<>();
        yueTaFragments.add(new YuetaAllfrag());
        yueTaFragments.add(new YuetaYaoqingfrag());
        yueTaFragments.add(new Yuetajinxingfrag());
        yueTaFragments.add(new Yuetawanchengfrag());
        taYueFragments = new ArrayList<>();
        taYueFragments.add(new TayueAllfrag());
        taYueFragments.add(new Tayuedaiyaofrag());
        taYueFragments.add(new Tayuejinxingfrag());
        taYueFragments.add(new Tayuewanchengfrag());
        jianZhiFragments = new ArrayList<>();
        jianZhiFragments.add(new myAllfrag());
        jianZhiFragments.add(new mybaomingfrag());
        jianZhiFragments.add(new myjinxingfrag());
        jianZhiFragments.add(new mywanchengfrag());
        fabuJianzhiFragments = new ArrayList<>();
        fabuJianzhiFragments.add(new fabuAllfrag());
        fabuJianzhiFragments.add(new fabubaomingfrag());
        fabuJianzhiFragments.add(new fabujinxingfrag());
        fabuJianzhiFragments.add(new fabuwanchengfrag());
    }
}