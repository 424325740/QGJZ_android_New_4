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
import com.qigaikj.parttimejob.bean.HomeSearchTayeuBean;
import com.qigaikj.parttimejob.bean.HomeTaYueAllBean;
import com.qigaikj.parttimejob.menuList.GlideRoundTransform;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeSearchTayueDataAdapter extends RecyclerView.Adapter<HomeSearchTayueDataAdapter.ViewHolder> {

    private String userSkill = "";

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack{
        void onItemClick(int pos);
    }

    public List<HomeSearchTayeuBean.DataBean> datas = null;
    public Context context;
    private ItemClickCallBack clickCallBack;

    public HomeSearchTayueDataAdapter(Context context, List<HomeSearchTayeuBean.DataBean> datas) {
        this.context = context;
        this.datas = datas;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_searchtayue_item,viewGroup,false);
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        Glide.with(context).load(datas.get(position).getDemand().get(position).getLogo()).transform(new GlideRoundTransform(context)).into((ImageView) viewHolder.iv_UserLogo);
        String uname = datas.get(position).getDemand().get(position).getUname();
        viewHolder.tv_UserName.setText(uname);
        viewHolder.tv_User_Occupation.setText(datas.get(position).getDemand().get(position).getSkill());
        viewHolder.tv_User_info.setText(datas.get(position).getDemand().get(position).getRequire());
        viewHolder.distance.setText(datas.get(position).getDemand().get(position).getDistance()+"m");
        String sex = datas.get(position).getDemand().get(position).getSex();
        String state = datas.get(position).getDemand().get(position).getState();
        String phone_state = datas.get(position).getDemand().get(position).getPhone_state();
        String wx_state = datas.get(position).getDemand().get(position).getWx_state();
        if (wx_state.equals("1")){
            viewHolder.icon_User_3.setVisibility(View.VISIBLE);
        }
        if (phone_state.equals("1")){
            viewHolder.phone_state.setVisibility(View.VISIBLE);
        }
        if (state.equals("1")){
            viewHolder.state.setVisibility(View.VISIBLE);
        }
        if (sex.equals("1")){
            viewHolder.sex.setText("男");
        }else if (sex.equals("2")){
            viewHolder.sex.setText("女");
        }else {
            viewHolder.sex.setText("无");
        }
        if ("1".equals(datas.get(position).getDemand().get(position).getGender())) {
            viewHolder.icon_User_1.setVisibility(View.VISIBLE);
            viewHolder.icon_User_1.setImageResource(R.mipmap.male);
        } else {
            viewHolder.icon_User_1.setVisibility(View.VISIBLE);
            viewHolder.icon_User_1.setImageResource(R.mipmap.famel);
        }
        if ("1".equals(datas.get(position).getDemand().get(position).getState())) {
            viewHolder.icon_User_2.setVisibility(View.VISIBLE);
            viewHolder.icon_User_2.setImageResource(R.mipmap.icon_authentication_real_name);
        }
        if ("1".equals(datas.get(position).getDemand().get(position).getPhone_state())) {
            viewHolder.icon_User_3.setVisibility(View.VISIBLE);
            viewHolder.icon_User_3.setImageResource(R.mipmap.icon_authentication_phone);
        }
        if ("1".equals(datas.get(position).getDemand().get(position).getWx_state())) {
            viewHolder.icon_User_4.setVisibility(View.VISIBLE);
            viewHolder.icon_User_4.setImageResource(R.mipmap.icon_authentication_wx);
        }

        if (datas.get(position).getAdvert() != null && datas.get(position).getAdvert().size() > 0) {
            viewHolder.rvUserPic.setVisibility(View.VISIBLE);
            GridLayoutManager layoutManager = new GridLayoutManager(context,3);
            viewHolder.rvUserPic.setLayoutManager(layoutManager);
            viewHolder.rvUserPic.setAdapter(new HomeSearchTayuepicDataAdapter(context, datas, position));
        } else {
            viewHolder.rvUserPic.setVisibility(View.GONE);
        }

        viewHolder.llYueTa.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(clickCallBack != null){
                            clickCallBack.onItemClick(position);
                        }
                    }
                }
        );
       /* for (int i = 0; i < datas.get(position).user_skill.size(); i++) {
            userSkill += datas.get(position).user_skill.get(i) + "    ";
        }*/
//        viewHolder.tv_User_info.setText(datas.get(position).introduce);
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
        private final TextView sex;
        private final TextView distance;
        private final ImageView state;
        private final ImageView phone_state;

        public ViewHolder(View view){
            super(view);
            llYueTa = (LinearLayout) view.findViewById(R.id.llYueTa);
            iv_UserLogo = (CircleImageView) view.findViewById(R.id.iv_UserLogo);
            tv_UserName = (TextView) view.findViewById(R.id.tv_UserName);
            icon_User_1 = (ImageView) view.findViewById(R.id.icon_User_1);
            icon_User_2 = (ImageView) view.findViewById(R.id.icon_User_2);
            icon_User_3 = (ImageView) view.findViewById(R.id.icon_User_3);
            icon_User_4 = (ImageView) view.findViewById(R.id.icon_User_4);
            distance = view.findViewById(R.id.distance);
            rvUserPic = (XRecyclerView) view.findViewById(R.id.rvUserPic);
            ivUserPic_1 = (ImageView) view.findViewById(R.id.ivUserPic_1);
            ivUserPic_2 = (ImageView) view.findViewById(R.id.ivUserPic_2);
            ivUserPic_3 = (ImageView) view.findViewById(R.id.ivUserPic_3);
            phone_state = view.findViewById(R.id.phone_state);
            state = view.findViewById(R.id.state);
            sex = view.findViewById(R.id.tv_User_Sex);
            tv_User_Occupation = (TextView) view.findViewById(R.id.tv_User_Occupation);
            tv_User_info = (TextView) view.findViewById(R.id.tv_User_info);
        }
    }
}
