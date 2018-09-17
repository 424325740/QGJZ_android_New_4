package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.Address;
import com.qigaikj.parttimejob.bean.HomeYueTa;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 设置首页约TA适配器
 */

public class HomeYueTaListAdapter extends BaseAdapter {
    public Context context;
    public List<HomeYueTa> HomemYueTaList;

    public HomeYueTaListAdapter(Context context, List<HomeYueTa> HomemYueTaList) {
        this.context = context;
        this.HomemYueTaList = HomemYueTaList;
    }
    @Override
    public int getCount() {
        return HomemYueTaList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_yueta_list_item, null);
            viewHolder.iv_UserLogo = (CircleImageView) convertView.findViewById(R.id.iv_UserLogo);
            viewHolder.tv_UserName = (TextView) convertView.findViewById(R.id.tv_UserName);
            viewHolder.tv_User_Occupation = (TextView) convertView.findViewById(R.id.tv_User_Occupation);
            viewHolder.tv_User_info = (TextView) convertView.findViewById(R.id.tv_User_info);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(HomemYueTaList.get(position).logo).into(viewHolder.iv_UserLogo);
        viewHolder.tv_UserName.setText(HomemYueTaList.get(position).uname);
        for (int i = 0; i < HomemYueTaList.get(position).user_skill.size(); i++) {
            viewHolder.tv_User_Occupation.setText(HomemYueTaList.get(position).user_skill.get(i));
        }
        viewHolder.tv_User_info.setText(HomemYueTaList.get(position).introduce);
        return convertView;
    }

    class ViewHolder {
        CircleImageView iv_UserLogo;//头像
        TextView tv_UserName;//用户姓名
        TextView tv_User_Occupation;//用户职业
        ImageView icon_User_1;//用户小图标
        ImageView icon_User_2;//用户小图标
        ImageView icon_User_3;//用户小图标
        ImageView icon_User_4;//用户小图标
        TextView tv_User_info;//用户信息
    }
}
