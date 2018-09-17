package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.FabuJianzhiBean;
import com.qigaikj.parttimejob.bean.TaYuewoBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FabuAllAtapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<List<FabuJianzhiBean.DataBean>> lists;
    private Context context;

    public FabuAllAtapter(List<List<FabuJianzhiBean.DataBean>> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.fabuallitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder= (MyViewHolder) holder;
        Glide.with(context).load(lists.get(position).get(position).getLogo()).into(myViewHolder.logo);//头像
        myViewHolder.tv_UserName.setText(lists.get(position).get(position).getTitle());//昵称
        String state = lists.get(position).get(position).getState();//实名认证
        if (state.equals("1")){
            myViewHolder.state.setVisibility(View.VISIBLE);
        }
        String flag = lists.get(position).get(position).getFlag();
        if (flag.equals("1")){
            myViewHolder.flag.setText("招聘中");
        }else if (flag.equals("2")){
            myViewHolder.flag.setText("进行中");
        }else {
            myViewHolder.flag.setText("已完成 ");
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final CircleImageView logo;
        private final TextView tv_UserName;
        private final TextView flag;
        private final ImageView state;

        public MyViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.iv_UserLogo);
            tv_UserName = itemView.findViewById(R.id.tv_UserName);
            flag = itemView.findViewById(R.id.flag);
            state = itemView.findViewById(R.id.state);
        }
    }
}
