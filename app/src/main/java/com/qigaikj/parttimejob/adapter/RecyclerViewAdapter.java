package com.qigaikj.parttimejob.adapter;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.ImageViewData;

import java.util.List;

public class RecyclerViewAdapter extends BaseQuickAdapter<ImageViewData, BaseViewHolder> {
    public RecyclerViewAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    public void convert(BaseViewHolder baseViewHolder, ImageViewData data) {
    }
}