package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.NearListBean;

import java.util.List;

/**
 * author:created by liangliang on 2018/6/11/011
 * function:
 */

public class NearListViewAdapter extends BaseAdapter {
    private List<NearListBean> dataList;
    private Context context;
    private TextView textview;

    public NearListViewAdapter(List<NearListBean> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(android.R.layout.test_list_item, null, false);
            viewHolder=new ViewHolder();
            textview = (TextView) convertView.findViewById(android.R.id.text1);
            textview.setPadding(15, 4, 15, 4);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NearListBean dataBean = dataList.get(position);
        String tname = dataBean.getTname();
        textview.setText(tname);
        return convertView;
    }
    public class ViewHolder{
        public TextView textview;
    }

}
