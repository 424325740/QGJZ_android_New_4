package com.qigaikj.parttimejob.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentFabuAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> list;
    List<String> title;

    public FragmentFabuAdapter(FragmentManager fm, ArrayList<Fragment> fragmentsList, ArrayList<String> titles) {
        super(fm);
        this.list = fragmentsList;
        this.title = titles;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {

        return list.get(position);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    //tablayout的title标题内容设置
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}