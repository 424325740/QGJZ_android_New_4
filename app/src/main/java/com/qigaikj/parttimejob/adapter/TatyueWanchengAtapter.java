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
import com.qigaikj.parttimejob.bean.TaYuewoBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TatyueWanchengAtapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<List<TaYuewoBean.DataBean>> lists;
    private Context context;

    public TatyueWanchengAtapter(List<List<TaYuewoBean.DataBean>> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.tayuejinxingtiem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder= (MyViewHolder) holder;
        Glide.with(context).load(lists.get(position).get(position).getLogo()).into(myViewHolder.logo);//头像
        myViewHolder.tv_UserName.setText(lists.get(position).get(position).getUname());//昵称
        myViewHolder.jinengname.setText(lists.get(position).get(position).getName());//技能名称
        myViewHolder.distance.setText(lists.get(position).get(position).getDistance()+"m");//距离
        String state = lists.get(position).get(position).getState();//实名认证
        String wx_state = lists.get(position).get(position).getWx_state();//微信认证
        String phone_state = lists.get(position).get(position).getPhone_state();//手机认证
        if (phone_state.equals("1")){
            myViewHolder.phonestate.setVisibility(View.VISIBLE);
        }
        if (wx_state.equals("1")){
            myViewHolder.wxstate.setVisibility(View.VISIBLE);
        }
        if (state.equals("1")){
            myViewHolder.state.setVisibility(View.VISIBLE);
        }
        String flag = lists.get(position).get(position).getFlag();
        if (flag.equals("0")){
            myViewHolder.flag.setText("待应邀");
        }else if (flag.equals("1")){
            myViewHolder.flag.setText("已应邀 ");
//            myViewHolder.show.setVisibility(View.GONE);
        }else if (flag.equals("2")){
            myViewHolder.flag.setText("进行中 ");
//            myViewHolder.show.setVisibility(View.GONE);
        }else {
            myViewHolder.flag.setText("已完成 ");
//            myViewHolder.show.setVisibility(View.GONE);
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
        private final TextView distance;
        private final TextView jinengname;
//        private final LinearLayout show;
        private final ImageView wxstate;
        private final ImageView state;
        private final ImageView phonestate;

        public MyViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.iv_UserLogo);
            tv_UserName = itemView.findViewById(R.id.tv_UserName);
            flag = itemView.findViewById(R.id.flag);
            distance = itemView.findViewById(R.id.distance);
            jinengname = itemView.findViewById(R.id.jinengname);
//            show = itemView.findViewById(R.id.isshow);
            wxstate = itemView.findViewById(R.id.wxstate);
            state = itemView.findViewById(R.id.state);
            phonestate = itemView.findViewById(R.id.phonestate);
        }
    }
}
