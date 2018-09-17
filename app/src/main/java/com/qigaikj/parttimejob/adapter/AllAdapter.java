package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;

import java.util.List;

public class AllAdapter extends BaseAdapter {
    private List<String> grideViewBeanList;
    private Context context;

    public AllAdapter(List<String> grideViewBeanList, Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_all, null);
        TextView text = view.findViewById(R.id.text);
        text.setVisibility(View.VISIBLE);
        String bean = grideViewBeanList.get(position);
        text.setText(bean);
        return view;
    }
}
