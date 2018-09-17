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

public class FollowAdapter extends BaseQuickAdapter<FollowBean, BaseViewHolder> {

    Context context;

    public FollowAdapter(Context context, int layoutResId, List data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    public void convert(BaseViewHolder baseViewHolder, FollowBean bean) {
        String unit;
        Glide.with(mContext).load(bean.logo).transform(new GlideRoundTransform(context)).into((ImageView) baseViewHolder.getView(R.id.iv_UserLogo));
        baseViewHolder.setText(R.id.tvName, bean.name);
        if ("1".equals(bean.unit)) {
            unit = "天";
        } else {
            unit = "小时";
        }
        baseViewHolder.setText(R.id.tvTime, bean.money + "/" + unit);
        baseViewHolder.setText(R.id.tvAddress, bean.address);
    }
}
