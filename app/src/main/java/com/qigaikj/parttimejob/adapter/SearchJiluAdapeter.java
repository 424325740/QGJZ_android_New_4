package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.ClassiFyActivity;
import com.qigaikj.parttimejob.activity.ClassiFyInfoActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.HomeGrideViewBean;
import com.qigaikj.parttimejob.bean.SearchJiluBean;
import com.qigaikj.parttimejob.bean.SkillCloseState;
import com.qigaikj.parttimejob.bean.SkillData;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.SwitchButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SearchJiluAdapeter extends BaseAdapter {
    private List<SearchJiluBean.DataBean> grideViewBeanList;
    private Context context;
    private LinearLayout checked;
    private String tname;

    public SearchJiluAdapeter(List<SearchJiluBean.DataBean> grideViewBeanList, Context context) {
        this.grideViewBeanList = grideViewBeanList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return grideViewBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return grideViewBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchjuliitem, null);
        TextView text = view.findViewById(R.id.text);
        text.setText(grideViewBeanList.get(position).getHistory().get(position));
        return view;
    }

}