package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.HomeJianzhiBean;
import com.qigaikj.parttimejob.bean.HomeTaYue;
import com.qigaikj.parttimejob.menuList.GlideRoundTransform;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeListJianZhiDataAdapter extends RecyclerView.Adapter<HomeListJianZhiDataAdapter.ViewHolder> {

    private String userSkill = "";
    public List<HomeJianzhiBean.DataBean> datas = null;
    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack{
        void onItemClick(int pos,String wid);
    }

    public Context context;
    private ItemClickCallBack clickCallBack;

    public HomeListJianZhiDataAdapter(Context context, List<HomeJianzhiBean.DataBean> datas) {
        this.context = context;
        this.datas = datas;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_jianzhi_list_item,viewGroup,false);
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        Glide.with(context).load(datas.get(position).getWork().get(position).getLogo()).transform(new GlideRoundTransform(context)).into((ImageView) viewHolder.iv_UserLogo);
       String uname = datas.get(position).getWork().get(position).getUname();
        viewHolder.tv_UserName.setText(uname);
        viewHolder.adders.setText(datas.get(position).getWork().get(position).getAddress());
        viewHolder.price.setText(datas.get(position).getWork().get(position).getUnit()+"天");
        viewHolder.llYueTa.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(clickCallBack != null){
                            clickCallBack.onItemClick(position,datas.get(position).getWork().get(position).getWid());
                        }
                    }
                }
        );

        if (!TextUtils.isEmpty(userSkill)) {
            userSkill = "";
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llYueTa;
        public CircleImageView iv_UserLogo;
        public TextView tv_UserName;
        public ImageView icon_User_1;
        public ImageView icon_User_2;
        public ImageView icon_User_3;
        public ImageView icon_User_4;

        public XRecyclerView rvUserPic;
        public ImageView ivUserPic_1;
        public ImageView ivUserPic_2;
        public ImageView ivUserPic_3;

        public TextView tv_User_Occupation;
        public TextView tv_User_info;
        private final TextView adders;
        private final TextView price;

        public ViewHolder(View view){
            super(view);
            llYueTa = (LinearLayout) view.findViewById(R.id.llYueTa);
            iv_UserLogo = (CircleImageView) view.findViewById(R.id.iv_UserLogo);
            tv_UserName = (TextView) view.findViewById(R.id.tv_UserName);
            icon_User_1 = (ImageView) view.findViewById(R.id.icon_User_1);
            icon_User_2 = (ImageView) view.findViewById(R.id.icon_User_2);
            icon_User_3 = (ImageView) view.findViewById(R.id.icon_User_3);
            icon_User_4 = (ImageView) view.findViewById(R.id.icon_User_4);
            adders = view.findViewById(R.id.adders);
            rvUserPic = (XRecyclerView) view.findViewById(R.id.rvUserPic);
            ivUserPic_1 = (ImageView) view.findViewById(R.id.ivUserPic_1);
            ivUserPic_2 = (ImageView) view.findViewById(R.id.ivUserPic_2);
            ivUserPic_3 = (ImageView) view.findViewById(R.id.ivUserPic_3);
            price = view.findViewById(R.id.price);
            tv_User_Occupation = (TextView) view.findViewById(R.id.tv_User_Occupation);
            tv_User_info = (TextView) view.findViewById(R.id.tv_User_info);
        }
    }
}
