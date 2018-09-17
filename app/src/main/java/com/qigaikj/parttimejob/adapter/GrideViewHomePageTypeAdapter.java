package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.ClassiFyActivity;
import com.qigaikj.parttimejob.activity.ClassiFyInfoActivity;
import com.qigaikj.parttimejob.bean.HomeGrideViewBean;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * author:created by liangliang on 2018/6/5/005
 * function:
 */

public class GrideViewHomePageTypeAdapter extends BaseAdapter {
    private List<HomeGrideViewBean> grideViewBeanList;
    private Context context;
    private LinearLayout checked;
    private String tname;

    public GrideViewHomePageTypeAdapter(List<HomeGrideViewBean> grideViewBeanList, Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_mygridview, null);
        ImageView img = view.findViewById(R.id.img);
        LinearLayout checked = view.findViewById(R.id.lincheked);
        Log.e(TAG, "getView: "+img );
        TextView text = view.findViewById(R.id.text);
        text.setVisibility(View.VISIBLE);
        HomeGrideViewBean bean = grideViewBeanList.get(position);
        String pic = bean.getPic();
        tname = bean.getTname();
        text.setText(tname);
        Glide.with(context).load(bean.getPic()).into(img);
        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item(bean);
            }
        });

        return view;
    }
    public void item(HomeGrideViewBean bean){
        if (bean.getTid().equals("100")){
            Intent intent=new Intent(context,ClassiFyActivity.class);
            context.startActivity(intent);
        }else if (bean.getTid().equals("97")){
            Intent intent=new Intent(context,ClassiFyInfoActivity.class);
            intent.putExtra("Title","家具生活");
            context.startActivity(intent);
        }else if (bean.getTid().equals("96")){
            Intent intent=new Intent(context,ClassiFyInfoActivity.class);
            intent.putExtra("Title","休闲娱乐");
            context.startActivity(intent);
        }else if (bean.getTid().equals("95")){
            Intent intent=new Intent(context,ClassiFyInfoActivity.class);
            intent.putExtra("Title","丽人时尚");
            context.startActivity(intent);
        }else if (bean.getTid().equals("94")){
            Intent intent=new Intent(context,ClassiFyInfoActivity.class);
            intent.putExtra("Title","美丽约");
            context.startActivity(intent);
        }else if (bean.getTid().equals("93")){
            Intent intent=new Intent(context,ClassiFyInfoActivity.class);
            intent.putExtra("Title","学生兼职");
            context.startActivity(intent);
        }else if (bean.getTid().equals("92")){
            Intent intent=new Intent(context,ClassiFyInfoActivity.class);
            intent.putExtra("Title","蓝领服务");
            context.startActivity(intent);
        }else if (bean.getTid().equals("91")){
            Intent intent=new Intent(context,ClassiFyInfoActivity.class);
            intent.putExtra("Title","白领兼职");
            context.startActivity(intent);
        }else if (bean.getTid().equals("90")){
            Intent intent=new Intent(context,ClassiFyInfoActivity.class);
            intent.putExtra("Title","人脉服务");
            context.startActivity(intent);
        }else if (bean.getTid().equals("89")){
            Intent intent=new Intent(context,ClassiFyInfoActivity.class);
            intent.putExtra("Title","城市名片");
            context.startActivity(intent);
        }
    }
}
