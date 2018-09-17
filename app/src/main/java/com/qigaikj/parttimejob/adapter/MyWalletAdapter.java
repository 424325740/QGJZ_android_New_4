package com.qigaikj.parttimejob.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.Money;

import java.util.List;

/**
 * Created by Administrator on 2017/11/18/018.
 * 我的钱包适配器页面
 */

public class MyWalletAdapter extends BaseAdapter {
    private List<Money.DetailBean> myWalletList;

    public MyWalletAdapter(List<Money.DetailBean> myWalletList) {
        this.myWalletList = myWalletList;
    }

    @Override
    public int getCount() {
        return myWalletList.size();
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_wallet, null);
            viewHolder.tv_my_wallet_title = (TextView) convertView.findViewById(R.id.tv_my_wallet_title);
            viewHolder.tv_my_wallet_timer = (TextView) convertView.findViewById(R.id.tv_my_wallet_timer);
            viewHolder.tv_my_wallet_money = (TextView) convertView.findViewById(R.id.tv_my_wallet_money);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.tv_my_wallet_money.setText("+" + myWalletList.get(position).getMoney() + "元");
//        viewHolder.tv_my_wallet_money.setTextColor(parent.getContext().getResources().getColor(R.color.mytext_1));
//        viewHolder.tv_my_wallet_title.setText(myWalletList.get(position).getTitle());
//            if (myWalletList.get(position).getPay().equals("1")){//支付宝
//                viewHolder.tv_my_wallet_title.setText("通过支付宝向钱包充值");
//            }else{
//                viewHolder.tv_my_wallet_title.setText("通过微信向钱包充值");
//        viewHolder.tv_my_wallet_money.setTextColor(parent.getContext().getResources().getColor(R.color.pink));
//            if (myWalletList.get(position).getPay().equals("1")){
//                viewHolder.tv_my_wallet_title.setText("从钱包提现到支付宝");
//            }else{
//                viewHolder.tv_my_wallet_title.setText("从钱包提现到微信");
//            }
        viewHolder.tv_my_wallet_title.setText(myWalletList.get(position).getTitle());
        viewHolder.tv_my_wallet_money.setText("-" + myWalletList.get(position).getMoney() + "元");
        viewHolder.tv_my_wallet_timer.setText(myWalletList.get(position).getTime());
        return convertView;
    }

    class ViewHolder {
        TextView tv_my_wallet_title;
        TextView tv_my_wallet_timer;
        TextView tv_my_wallet_money;
    }
}
