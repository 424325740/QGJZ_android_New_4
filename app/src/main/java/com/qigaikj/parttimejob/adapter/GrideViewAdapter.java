package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.HomeGridViewWelfare;
import com.qigaikj.parttimejob.bean.NearListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author:created by liangliang on 2018/6/5/005
 * function:
 */

public class GrideViewAdapter extends BaseAdapter {
    private List<NearListBean> grideViewBeanList;
    private List<Boolean> isChecks;
    private Context context;
    private ItemClickInterface clickInterface;

    public GrideViewAdapter() {
    }

    public GrideViewAdapter(List<NearListBean> grideViewBeanList, Context context) {
        this.grideViewBeanList = grideViewBeanList;
        this.context = context;
        isChecks = new ArrayList<>();
        for (int i = 0; i < grideViewBeanList.size(); i++) {
            if (i == 0) {
                isChecks.add(true);
            } else {
                isChecks.add(false);
            }
        }
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
        View view = LayoutInflater.from(context).inflate(R.layout.itemgride, null);
        TextView text = view.findViewById(R.id.gridItemText);
        text.setText(grideViewBeanList.get(position).getTname());
        Drawable checked = context.getResources().getDrawable(R.drawable.yuanjiaobut);
        Drawable unChecked = context.getResources().getDrawable(R.drawable.yuanjiaohome);
        text.setOnClickListener(v -> {
            for (int i = 0; i < isChecks.size(); i++) {
                if(i==position){
                    isChecks.set(i,true);
                }else{
                    isChecks.set(i,false);
                }
            }
            clickInterface.ItemClick(grideViewBeanList.get(position));
            notifyDataSetChanged();
        });
        text.setBackground(isChecks.get(position)?checked:unChecked);
        text.setTextColor(isChecks.get(position)?context.getResources().getColor(R.color.white):context.getResources().getColor(R.color.mytext_2));
        return view;
    }
    public void itemClick(ItemClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    public interface ItemClickInterface {
        void ItemClick(NearListBean nearListBean);
    }
}
