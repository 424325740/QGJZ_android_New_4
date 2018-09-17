package com.qigaikj.parttimejob.menuList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.ClassiFyInfoActivity;
import com.qigaikj.parttimejob.bean.ClassifyForAll_Item;

import java.util.List;

/**
 * author:created by liangliang on 2018/6/7/007
 * function: 右侧grideview适配器。
 */

public class ClassiFyErjiMenuChildViewAdapter extends BaseAdapter {

    private Context context;
    private List<ClassifyForAll_Item> foodDatas;

    public ClassiFyErjiMenuChildViewAdapter(Context context, List<ClassifyForAll_Item> foodDatas) {
        this.context = context;
        this.foodDatas = foodDatas;
    }


    @Override
    public int getCount() {
        if (foodDatas != null) {
            return foodDatas.size();
        } else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return foodDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ClassifyForAll_Item subcategory = foodDatas.get(position);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_classifyerjimenuchild, null);
            viewHold = new ViewHold();
            viewHold.tv_name = (TextView) convertView.findViewById(R.id.item_home_name);
            viewHold.iv_icon = (ImageView) convertView.findViewById(R.id.item_album);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }

        viewHold.iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("asd", "点击了 = " + subcategory.getTname());
                Intent intent = new Intent(context, ClassiFyInfoActivity.class);
                intent.putExtra("tid", subcategory.getTid());
                intent.putExtra("Title", subcategory.getTname());
                context.startActivity(intent);
            }
        });

        viewHold.tv_name.setText(subcategory.getTname());
        Glide.with(context).load(subcategory.getPic()).into(viewHold.iv_icon);

        return convertView;
    }

    private static class ViewHold {
        private TextView tv_name;
        private ImageView iv_icon;
    }
}
