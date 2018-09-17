package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.HomeSearchBean;

import java.util.List;

public class HomeSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeSearchBean.DataBean> dataBeans;
    private Context context;

    public HomeSearchAdapter(List<HomeSearchBean.DataBean> dataBeans, Context context) {
        this.dataBeans = dataBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.searchitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
