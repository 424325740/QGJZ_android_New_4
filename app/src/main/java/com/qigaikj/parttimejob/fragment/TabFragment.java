package com.qigaikj.parttimejob.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.SimpleFragmentAdapter;
import com.qigaikj.parttimejob.view.MyTextView;

import java.util.ArrayList;

/**
 * author:created by liangliang on 2018/5/25/025
 * function:
 */

public class TabFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final String KEY_TAB = "TAB";
    public static final String KEY_STATUE = "STATUES";

    private ArrayList<String> mlistStatues;
    private MyTextView myStatue01;
    private MyTextView myStatue02;
    private MyTextView myStatue03;
    private MyTextView myStatue04;

    private MyTextView myStatueLast;

    private ViewPager myViewPager;

    private SimpleFragmentAdapter madptFragments;
    private ArrayList<Fragment> mlistFragments;
    private StatueFragment mFragment01;
    private StatueFragment mFragment02;
    private StatueFragment mFragment03;
    private StatueFragment mFragment04;

    private String mTab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_for_tab, container, false);
        initData();
        initView(root);
        setListener();
        return root;
    }

    private void initData() {
        Bundle bundle;
        if ((bundle = getArguments()) != null) {
            if (bundle.containsKey(KEY_TAB)) {
                mTab = bundle.getString(KEY_TAB);
            }
            if (bundle.containsKey(KEY_STATUE)) {
                mlistStatues = bundle.getStringArrayList(KEY_STATUE);
            }
        }
        mlistFragments = new ArrayList<>();
        Bundle bundle1 = new Bundle();
        bundle1.putString(StatueFragment.KEY_TITLE, mTab + ((mlistStatues != null && mlistStatues.size() > 0) ? mlistStatues.get(0) : "NULL"));
        mFragment01 = StatueFragment.getInstance(bundle1);
        mlistFragments.add(mFragment01);
        Bundle bundle2 = new Bundle();
        bundle2.putString(StatueFragment.KEY_TITLE, mTab + ((mlistStatues != null && mlistStatues.size() > 1) ? mlistStatues.get(1) : "NULL"));
        mFragment02 = StatueFragment.getInstance(bundle2);
        mlistFragments.add(mFragment02);
        Bundle bundle3 = new Bundle();
        bundle3.putString(StatueFragment.KEY_TITLE, mTab + ((mlistStatues != null && mlistStatues.size() > 2) ? mlistStatues.get(2) : "NULL"));
        mFragment03 = StatueFragment.getInstance(bundle3);
        mlistFragments.add(mFragment03);
        Bundle bundle4 = new Bundle();
        bundle4.putString(StatueFragment.KEY_TITLE, mTab + ((mlistStatues != null && mlistStatues.size() > 3) ? mlistStatues.get(3) : "NULL"));
        mFragment04 = StatueFragment.getInstance(bundle4);
        mlistFragments.add(mFragment04);

        madptFragments = new SimpleFragmentAdapter(getChildFragmentManager(), mlistFragments);
    }

    private void initView(View view) {
        myStatue01 = view.findViewById(R.id.id_statue_01);
        myStatue01.setText((mlistStatues != null && mlistStatues.size() > 0) ? mlistStatues.get(0) : "NULL");
        myStatue01.setSelect(true);
        myStatue02 = view.findViewById(R.id.id_statue_02);
        myStatue02.setText((mlistStatues != null && mlistStatues.size() > 1) ? mlistStatues.get(1) : "NULL");
        myStatue03 = view.findViewById(R.id.id_statue_03);
        myStatue03.setText((mlistStatues != null && mlistStatues.size() > 2) ? mlistStatues.get(2) : "NULL");
        myStatue04 = view.findViewById(R.id.id_statue_04);
        myStatue04.setText((mlistStatues != null && mlistStatues.size() > 3) ? mlistStatues.get(3) : "NULL");

        myStatueLast = myStatue01;

        myViewPager = view.findViewById(R.id.id_statue_vp);
        myViewPager.setAdapter(madptFragments);
    }

    private void setListener() {
        myStatue01.setOnClickListener(TabFragment.this);
        myStatue02.setOnClickListener(TabFragment.this);
        myStatue03.setOnClickListener(TabFragment.this);
        myStatue04.setOnClickListener(TabFragment.this);
        myViewPager.addOnPageChangeListener(TabFragment.this);
    }

    public static TabFragment getInstance(Bundle bundle) {
        TabFragment fragment = new TabFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                if (myStatueLast == myStatue01) {
                    break;
                }
                myStatueLast.setSelect(false);
                myStatue01.setSelect(true);
                myStatueLast = myStatue01;
                break;
            case 1:
                if (myStatueLast == myStatue02) {
                    break;
                }
                myStatueLast.setSelect(false);
                myStatue02.setSelect(true);
                myStatueLast = myStatue02;
                break;
            case 2:
                if (myStatueLast == myStatue03) {
                    break;
                }
                myStatueLast.setSelect(false);
                myStatue03.setSelect(true);
                myStatueLast = myStatue03;
                break;
            case 3:
                if (myStatueLast == myStatue04) {
                    break;
                }
                myStatueLast.setSelect(false);
                myStatue04.setSelect(true);
                myStatueLast = myStatue04;
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_statue_01:
                myViewPager.setCurrentItem(0);
                break;
            case R.id.id_statue_02:
                myViewPager.setCurrentItem(1);
                break;
            case R.id.id_statue_03:
                myViewPager.setCurrentItem(2);
                break;
            case R.id.id_statue_04:
                myViewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }
}


