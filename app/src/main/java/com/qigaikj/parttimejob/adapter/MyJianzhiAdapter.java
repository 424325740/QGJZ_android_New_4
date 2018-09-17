package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.MyJianzhiBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyJianzhiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<List<MyJianzhiBean.DataBean>> lists;
    private Context context;

    public MyJianzhiAdapter(List<List<MyJianzhiBean.DataBean>> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.myjianzhiallitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder= (MyViewHolder) holder;
        myViewHolder.address.setText(lists.get(position).get(position).getAddress());//地址
        myViewHolder.money.setText(lists.get(position).get(position).getMoney());//价钱
        Glide.with(context).load(lists.get(position).get(position).getLogo()).into(myViewHolder.logo);
        myViewHolder.title.setText(lists.get(position).get(position).getTitle());//名称
        String flag = lists.get(position).get(position).getFlag();
        if (flag.equals("1")){
            myViewHolder.flag.setText("已报名");
        }else if (flag.equals("2")){
            myViewHolder.flag.setText("已录用");
        }else if (flag.equals("3")){
            myViewHolder.flag.setText("进行中");
        }else if (flag.equals("4")){
            myViewHolder.flag.setText("已完成");
        }
        if (lists.get(position).get(position).getState().equals("1")){
            myViewHolder.state.setVisibility(View.VISIBLE);
        }
        String unit = lists.get(position).get(position).getUnit();
        if (unit!=null){
            if (unit.equals("1")){
                myViewHolder.unit.setText("/天");
            }else {
                myViewHolder.unit.setText("/小时");
            }
        }


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView title;
        private final TextView money;
        private final TextView unit;
        private final TextView address;
        private final CircleImageView logo;
        private final ImageView state;
        private final TextView flag;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_UserName);
            money = itemView.findViewById(R.id.money);
            unit = itemView.findViewById(R.id.unit);
            address = itemView.findViewById(R.id.address);
            logo = itemView.findViewById(R.id.logo);
            state = itemView.findViewById(R.id.state);
            flag = itemView.findViewById(R.id.flag);
        }
    }
}
