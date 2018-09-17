package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.FollowBean;
import com.qigaikj.parttimejob.menuList.GlideRoundTransform;

import java.util.List;

public class UserInfoDetailOneAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    Context context;

    public UserInfoDetailOneAdapter(Context context, int layoutResId, List data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    public void convert(BaseViewHolder baseViewHolder, String bean) {
        baseViewHolder.setText(R.id.tvName, bean);
        baseViewHolder.setText(R.id.tvTime, "666/天");
        baseViewHolder.setText(R.id.tvState, "线上");
    }
}
