package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.HomeAllYueTaBean;
import com.qigaikj.parttimejob.bean.HomeSearchYuetaBean;
import com.qigaikj.parttimejob.menuList.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchDataAdapter extends RecyclerView.Adapter<HomeSearchDataAdapter.ViewHolder> {

    private List<String> picList = new ArrayList<>();

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack{
        void onItemClick(int pos);
    }

    public List<HomeSearchYuetaBean.DataBean> datas = new ArrayList<HomeSearchYuetaBean.DataBean>();
    public Context context;
    public int PicPosition;
    private ItemClickCallBack clickCallBack;

    public HomeSearchDataAdapter(Context context, List<HomeSearchYuetaBean.DataBean> datas, int PicPosition) {
        this.context = context;
        this.datas = datas;
        this.PicPosition = PicPosition;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_yueta_user_pic_list_item,viewGroup,false);
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        if (datas.get(PicPosition).getSkill().get(PicPosition).getSkill_pic() != null && datas.get(PicPosition).getSkill().get(PicPosition).getSkill_pic().size() > 0) {
            picList.addAll(datas.get(PicPosition).getSkill().get(PicPosition).getSkill_pic());
            Glide.with(context).load(picList.get(position)).transform(new GlideRoundTransform(context)).into((ImageView) viewHolder.ivUserPic);
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

        public ImageView ivUserPic;

        public ViewHolder(View view){
            super(view);
            ivUserPic = (ImageView) view.findViewById(R.id.ivUserPic);
        }
    }
}
