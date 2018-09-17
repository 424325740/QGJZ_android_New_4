package com.qigaikj.parttimejob.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by KBTS on 2017/6/19.
 */

public class SimpleFragmentAdapter extends FragmentPagerAdapter {
    
    private ArrayList<Fragment> lists;
    private ArrayList<String> titles;
    
    public SimpleFragmentAdapter(FragmentManager fm, ArrayList<Fragment> lists) {
        super(fm);
        this.lists = lists;
    }
    
    public SimpleFragmentAdapter(FragmentManager fm, ArrayList<Fragment> lists, ArrayList<String> titles) {
        super(fm);
        this.lists = lists;
        this.titles = titles;
    }
    
    public void updataLists(ArrayList<Fragment> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }
    
    @Override
    public Fragment getItem(int position) {
        if (lists == null || position > lists.size()) {
            return null;
        }
        return lists.get(position);
    }
    
    @Override
    public int getCount() {
        return lists == null ? 0 : lists.size();
    }
    
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (titles != null && titles.size() > position) ? titles.get(position) : "NULL";
    }
}
