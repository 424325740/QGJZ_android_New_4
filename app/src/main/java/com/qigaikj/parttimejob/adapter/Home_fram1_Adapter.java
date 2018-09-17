package com.qigaikj.parttimejob.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.PartTime;

import java.util.List;

/**
 * Created by Administrator on 2017/11/3/003.
 * 主页列表适配器
 */

public class Home_fram1_Adapter extends BaseAdapter {
    private List<PartTime> partTimeList;
    private boolean isJuli = true;//默认显示

    public Home_fram1_Adapter(List<PartTime> titleImageList) {
        this.partTimeList = titleImageList;
    }

    public void setJuli(boolean juli) {
        this.isJuli = juli;
    }

    @Override
    public int getCount() {
        return partTimeList.size();
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_fram1, null);
            viewHolder.tv_frm1_title = (TextView) convertView.findViewById(R.id.tv_frm1_title);
            viewHolder.tv_frm1_money = (TextView) convertView.findViewById(R.id.tv_frm1_money);
            viewHolder.tv_frm1_address = (TextView) convertView.findViewById(R.id.tv_frm1_address);
            viewHolder.tv_frm1_timer = (TextView) convertView.findViewById(R.id.tv_frm1_timer);
            viewHolder.tv_frm1_paystale = (TextView) convertView.findViewById(R.id.tv_frm1_paystale);
            viewHolder.tv_frm1_juli = (TextView) convertView.findViewById(R.id.tv_frm1_juli);
            viewHolder.tv_frm1_paystale_3 = (TextView) convertView.findViewById(R.id.tv_frm1_paystale_3);
            viewHolder.tv_frm1_paystale_2 = (TextView) convertView.findViewById(R.id.tv_frm1_paystale_2);
            viewHolder.iv_item_home_fram1_image= (ImageView) convertView.findViewById(R.id.iv_item_home_fram1_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String salary = partTimeList.get(position).getSalary();
        if (salary != null && salary.equals("1")) {//押金
            viewHolder.tv_frm1_paystale_2.setVisibility(View.VISIBLE);
        } else {//线上
            viewHolder.tv_frm1_paystale_2.setVisibility(View.GONE);
        }
        if (partTimeList.get(position).getDistance() == null || partTimeList.get(position).getDistance().equals("")) {
            viewHolder.tv_frm1_juli.setVisibility(View.GONE);
        } else {
            if (isJuli) {//设置为不显示
                viewHolder.tv_frm1_juli.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_frm1_juli.setVisibility(View.GONE);
            }
            viewHolder.tv_frm1_juli.setText(partTimeList.get(position).getDistance() + "m");
        }
        viewHolder.tv_frm1_title.setText(partTimeList.get(position).getTitle());
        viewHolder.tv_frm1_money.setText(partTimeList.get(position).getMoney() + "/" + "天");
        viewHolder.tv_frm1_address.setText(partTimeList.get(position).getAddress());
        viewHolder.tv_frm1_timer.setText(partTimeList.get(position).getBegindate() + "日-" + partTimeList.get(position).getEnddate() + "日");
        switch (partTimeList.get(position).getPay_style()) {
            case "0":
                viewHolder.tv_frm1_paystale.setText("不限");
                break;
            case "1":
                viewHolder.tv_frm1_paystale.setText("日结");
                break;
            case "2":
                viewHolder.tv_frm1_paystale.setText("周结");
                break;
            case "3":
                viewHolder.tv_frm1_paystale.setText("月结");
                break;
            case "4":
                viewHolder.tv_frm1_paystale.setText("完工结");
                break;

        }
        //是否为企业保障
        if (partTimeList.get(position).getTag().equals("2")) {
            viewHolder.tv_frm1_paystale_3.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tv_frm1_paystale_3.setVisibility(View.GONE);
        }
//        placeholder(R.mipmap.icon_moren).error(R.mipmap.icon_moren)
        Glide.with(parent.getContext()).load(partTimeList.get(position).getPic()).into(viewHolder.iv_item_home_fram1_image);
//        ImageUtils.setImage(parent.getContext(),partTimeList.get(position).getPic(),viewHolder.iv_item_home_fram1_image);
        return convertView;
    }

    class ViewHolder {
        TextView tv_frm1_title;//标题
        TextView tv_frm1_money;//价格
        TextView tv_frm1_address;//地址
        TextView tv_frm1_timer;//时间
        TextView tv_frm1_paystale;//类型
        TextView tv_frm1_juli;//距离
        TextView tv_frm1_paystale_2;
        TextView tv_frm1_paystale_3;
        ImageView iv_item_home_fram1_image;
    }
}
