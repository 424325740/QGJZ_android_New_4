package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.DingDanYueTaBean;
import com.qigaikj.parttimejob.bean.DingdanYiYaoqingBean;
import com.qigaikj.parttimejob.menuList.GlideRoundTransform;

import java.util.List;

public class DingdanYiYaoqingXQAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<List<DingdanYiYaoqingBean.DataBean>> dingDanYueTaBeans;
    private Context context;
    private ItemClickCallBack clickCallBack;

    public interface ItemClickCallBack {
        void onItemClick(int pos, String uid);
    }

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }


    public DingdanYiYaoqingXQAdapter(List<List<DingdanYiYaoqingBean.DataBean>> dingDanYueTaBeans, Context context) {
        this.dingDanYueTaBeans = dingDanYueTaBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.dingdanyaoqingxqitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Glide.with(context).load(dingDanYueTaBeans.get(position).get(position).getLogo()).transform(new GlideRoundTransform(context)).into((ImageView) myViewHolder.logo);
        myViewHolder.addtime.setText(dingDanYueTaBeans.get(position).get(position).getAddtime());//应邀时间
        myViewHolder.name.setText(dingDanYueTaBeans.get(position).get(position).getUname() + "");//名字
        String serve = (String) dingDanYueTaBeans.get(position).get(position).getServe();//服务类型
        String uid = dingDanYueTaBeans.get(position).get(position).getUid();//用户id
        String gender = dingDanYueTaBeans.get(position).get(position).getGender();//性别
        String phone_state = dingDanYueTaBeans.get(position).get(position).getPhone_state();//手机号认证
        String wx_state = dingDanYueTaBeans.get(position).get(position).getWx_state();//微信认证状态
        myViewHolder.sinceritygold.setText(dingDanYueTaBeans.get(position).get(position).getSincerity_gold()+"");//诚意金
        //微信认证状态
        if (wx_state.equals("1")) {

        } else {

        }
        //手机号认证状态
        if (phone_state.equals("1")) {

        } else {

        }
        //性别
        if (gender.equals("1")) {
            myViewHolder.gender.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.male));
        } else {
            myViewHolder.gender.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.icon_girl));
        }
        //服务类型
        if (serve.equals("1")) {
            myViewHolder.serve.setText("线上服务");
        } else if (serve.equals("2")) {
            myViewHolder.serve.setText("线下服务");
        } else {
            myViewHolder.serve.setText("线下服务");
        }

        myViewHolder.dingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCallBack != null) {
                    clickCallBack.onItemClick(position, uid);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dingDanYueTaBeans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final ImageView logo;
        private final TextView addtime;
        private final TextView serve;
        private final ImageView state;
        private final TextView name;
        private final LinearLayout dingdan;
        private final ImageView gender;
        private final ImageView phone_state;
        private final ImageView wxstate;
        private final TextView sinceritygold;

        public MyViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);//头像
            addtime = itemView.findViewById(R.id.addtime);//应邀时间
            serve = itemView.findViewById(R.id.serve);//服务类型
            state = itemView.findViewById(R.id.state);//微信认证状态
            name = itemView.findViewById(R.id.name);//名字
            dingdan = itemView.findViewById(R.id.dingdan);//点击事件
            gender = itemView.findViewById(R.id.gender);//性别
            phone_state = itemView.findViewById(R.id.phonestate); //手机号认证
            wxstate = itemView.findViewById(R.id.wxstate);//支付宝认证
            sinceritygold = itemView.findViewById(R.id.sinceritygold); //诚意金
        }
    }
}
