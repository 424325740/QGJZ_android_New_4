package com.qigaikj.parttimejob.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.SkillActivity;
import com.qigaikj.parttimejob.bean.BlogService;
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

public class SkillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SkillData> skillData;
    private Context context;
    private ItemClickCallBack clickCallBack;
    private OnItemLongClickListener mOnItemLongClickListener;
    public interface ItemClickCallBack{
        void onItemClick(int pos,SkillData skillData,String kid);
    }
    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position,String kid);
    }
    public SkillAdapter(List<SkillData> skillData, Context context) {
        this.skillData = skillData;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.activity_skill_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder= (MyViewHolder) holder;
        Glide.with(context).load(skillData.get(position).pic).into(myViewHolder.ivSkillPicture);
        myViewHolder.tvSkillTitle.setText(skillData.get(position).name);
        myViewHolder.tvSkillTime.setText(skillData.get(position).addtime);
        myViewHolder.tvSkillIntroduce.setText(skillData.get(position).introduce);
        if ("1".equals(skillData.get(position).serve)) {
            myViewHolder.tvServiceType.setText("线上服务");
        } else {
            myViewHolder.tvServiceType.setText("线下服务");
        }
        if ("1".equals(skillData.get(position).unit)) {
            myViewHolder.tvSkillMoney.setText(skillData.get(position).price + "/" + "天");
        } else {
            myViewHolder.tvSkillMoney.setText(skillData.get(position).price + "/" + "小时");
        }
        if ("1".equals(skillData.get(position).state)) {
            myViewHolder.switch_button.setChecked(true);
        } else {
            myViewHolder.switch_button.setChecked(false);
        }
        myViewHolder.switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    PostCloseSkill(skillData.get(position).kid);
                    myViewHolder.tv_switchButton.setText("已开启接单");
                } else {
                    PostCloseSkill(skillData.get(position).kid);
                    myViewHolder.tv_switchButton.setText("已关闭接单");
                }
            }
        });
        myViewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickCallBack != null){
                    clickCallBack.onItemClick(position,skillData.get(position),skillData.get(position).kid);
                }
            }
        });
        myViewHolder.delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnItemLongClickListener.onItemLongClick(view,position,skillData.get(position).kid);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return skillData.size();
    }
    public void PostCloseSkill(String kid) {
        String token = SharedPreferencesHelper.getInstance(context).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).postCloseSkill(token, kid).enqueue(new Callback<WrapperRspEntity<SkillCloseState>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<SkillCloseState>> call, Response<WrapperRspEntity<SkillCloseState>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (response.body().getData() != null) {
                        if ("1".equals(response.body().getData().state)) {
                        } else {
                        }
                        Toast.makeText(context,response.body().getMsg(),Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context,response.body().getMsg(),Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<WrapperRspEntity<SkillCloseState>> call, Throwable t) {

            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView ivSkillPicture;
        private final TextView tvSkillTitle;
        private final TextView tvSkillTime;
        private final TextView tvSkillIntroduce;
        private final TextView tvServiceType;
        private final TextView tvSkillMoney;
        private final SwitchButton switch_button;
        private final TextView tv_switchButton;
        private final TextView tvEdit;
        private final LinearLayout delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivSkillPicture = itemView.findViewById(R.id.ivSkillPicture);
            tvSkillTitle = itemView.findViewById(R.id.tvSkillTitle);
            tvSkillTime = itemView.findViewById(R.id.tvSkillTime);
            tvSkillIntroduce = itemView.findViewById(R.id.tvSkillIntroduce);
            tvServiceType = itemView.findViewById(R.id.tvServiceType);
            tvSkillMoney = itemView.findViewById(R.id.tvSkillMoney);
            switch_button = itemView.findViewById(R.id.switch_button);
            tv_switchButton = itemView.findViewById(R.id.tv_SwitchButton);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
