package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.BaomingGuanliBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class LuyongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<BaomingGuanliBean.DataBean> dataBeans;
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> isCheck = new HashMap<Integer, Boolean>();
    private ItemClickCallBack clickCallBack;
    public interface ItemClickCallBack{
        void onItemClick(String uid, TextView textView);
    }
    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }
    public LuyongAdapter(Context context, List<BaomingGuanliBean.DataBean> dataBeans) {
        this.context = context;
        this.dataBeans = dataBeans;
        // 默认为不选中
        initCheck(false);
    }
    // 初始化map集合
    public void initCheck(boolean flag) {
        // map集合的数量和list的数量是一致的
        for (int i = 0; i < dataBeans.size(); i++) {
            // 设置默认的显示
            isCheck.put(i, flag);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.luyongitem, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        String logo = dataBeans.get(position).getUsers().get(position).getLogo();
        Glide.with(context).load(logo).into(myViewHolder.logo);//头像
        myViewHolder.name.setText(dataBeans.get(position).getUsers().get(position).getUname());//名字
        String uid = dataBeans.get(position).getUsers().get(position).getUid();//id
        myViewHolder.cbCheckBox.setChecked(isCheck.get(position));
        myViewHolder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheck.put(position,b);
            }
        });
        myViewHolder.yongta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallBack.onItemClick(uid,myViewHolder.yongta);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView logo;
        private final TextView name;
        private final CheckBox cbCheckBox;
        private final TextView yongta;

        public MyViewHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            name = itemView.findViewById(R.id.name);
            cbCheckBox = itemView.findViewById(R.id.cbCheckBox);
            yongta = itemView.findViewById(R.id.yongta);
        }
    }

    // 全选按钮获取状态
    public Map<Integer, Boolean> getMap() {
        // 返回状态
        return isCheck;
    }
}
