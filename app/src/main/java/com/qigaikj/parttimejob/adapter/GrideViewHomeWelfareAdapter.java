package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.HomeGridViewWelfare;

import java.util.List;

/**
 * author:created by liangliang on 2018/6/5/005
 * function:
 */

public class GrideViewHomeWelfareAdapter extends BaseAdapter {
    private List<List<HomeGridViewWelfare>> grideViewBeanList;
    private Context context;
    private LinearLayout lincheked;

    public void setItemClick(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack{
        void onItemClick(int pos);
    }
    private ItemClickCallBack clickCallBack;
    public GrideViewHomeWelfareAdapter(List<List<HomeGridViewWelfare>> grideViewBeanList, Context context) {
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
        View view = View.inflate(context, R.layout.item_tupian, null);
        ImageView img2 = view.findViewById(R.id.img2);
        lincheked = view.findViewById(R.id.lincheked);
        String pic = grideViewBeanList.get(position).get(position).pic;
        Glide.with(context).load(pic).into(img2);
        lincheked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallBack.onItemClick(position);
            }
        });
        return view;
    }
}
