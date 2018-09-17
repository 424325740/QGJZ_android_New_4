package com.qigaikj.parttimejob.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.Address;

import java.util.List;

/**
 * Created by Administrator on 2017/11/10/010.
 * 设置地址适配器
 */

public class AddressListAdapter extends BaseAdapter {
    public List<Address> addressList;

    public AddressListAdapter(List<Address> addressList) {
        this.addressList = addressList;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
        notifyDataSetChanged();
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, null);
            viewHolder.tv_address_city_name = (TextView) convertView.findViewById(R.id.tv_address_city_name);
            viewHolder.tv_addressbook_item_title = (TextView) convertView.findViewById(R.id.tv_addressbook_item_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tv_addressbook_item_title.setVisibility(View.VISIBLE);
            viewHolder.tv_addressbook_item_title.setText(addressList.get(position).sortLetters);
        } else {
            viewHolder.tv_addressbook_item_title.setVisibility(View.GONE);
        }
        viewHolder.tv_address_city_name.setText(addressList.get(position).addressname);
        return convertView;
    }

    class ViewHolder {
        TextView tv_address_city_name;
        TextView tv_addressbook_item_title;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return addressList.get(position).sortLetters.charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = addressList.get(i).sortLetters;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}
