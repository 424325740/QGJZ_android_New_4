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
import com.qigaikj.parttimejob.bean.DingDanYueTaBean;
import com.qigaikj.parttimejob.menuList.GlideRoundTransform;

import java.util.List;

public class DingdanWanchengAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private List<List<DingDanYueTaBean.DataBean>> dingDanYueTaBeans;
    private Context context;
    private ItemClickCallBack clickCallBack;
    public interface ItemClickCallBack{
        void onItemClick(int pos, String did);
    }
    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }


    public DingdanWanchengAdapter(List<List<DingDanYueTaBean.DataBean>> dingDanYueTaBeans, Context context) {
        this.dingDanYueTaBeans = dingDanYueTaBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.dingdanwanchengitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder= (MyViewHolder) holder;
        Glide.with(context).load(dingDanYueTaBeans.get(position).get(position).getPic()).transform(new GlideRoundTransform(context)).into((ImageView) myViewHolder.pic);
        myViewHolder.addtime.setText(dingDanYueTaBeans.get(position).get(position).getAddtime());
//        myViewHolder.days.setText(dingDanYueTaBeans.get(position).get(position).getDays()+"天后过期");
        myViewHolder.name.setText(dingDanYueTaBeans.get(position).get(position).getName()+"");
        String serve = (String) dingDanYueTaBeans.get(position).get(position).getServe();
        String did = dingDanYueTaBeans.get(position).get(position).getDid();
        if (serve=="1"){
            myViewHolder.serve.setText("线上服务");
        }else if (serve=="2"){
            myViewHolder.serve.setText("线下服务");
        }else {
            myViewHolder.serve.setText("线下服务");
        }
        myViewHolder.dingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickCallBack != null){
                    clickCallBack.onItemClick(position,did);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dingDanYueTaBeans.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{


        private final ImageView pic;
        private final TextView addtime;
        private final TextView days;
        private final TextView serve;
        private final TextView state;
        private final TextView name;
        private final LinearLayout dingdan;

        public MyViewHolder(View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.logo);
            addtime = itemView.findViewById(R.id.addtime);
            days = itemView.findViewById(R.id.days);
            serve = itemView.findViewById(R.id.serve);
            state = itemView.findViewById(R.id.state);
            name = itemView.findViewById(R.id.name);
            dingdan = itemView.findViewById(R.id.dingdan);

        }
    }
}
