package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.HomeBannerList;
import com.qigaikj.parttimejob.menuList.GlideRoundTransform;

import java.util.List;

public class BroadcastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeBannerList> list;
    private Context context;

    public BroadcastAdapter(List<HomeBannerList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myholder= (MyViewHolder) holder;
        Glide.with(context).load(list.get(position).pic).transform(new GlideRoundTransform(context)).into(myholder.images);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{


        public ImageView images;

        public MyViewHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.tv_title);
        }
    }
}
