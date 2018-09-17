package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.SearchJiluBean;

import java.util.List;

public class SearchremenAdapeter extends BaseAdapter {
    private List<SearchJiluBean.DataBean> grideViewBeanList;
    private Context context;
    private LinearLayout checked;
    private String tname;

    public SearchremenAdapeter(List<SearchJiluBean.DataBean> grideViewBeanList, Context context) {
        this.grideViewBeanList = grideViewBeanList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return grideViewBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return grideViewBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchremenitem, null);
        TextView text = view.findViewById(R.id.text);
        text.setText(grideViewBeanList.get(position).getYueta().get(position));
        return view;
    }

}