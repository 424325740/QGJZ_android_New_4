package com.qigaikj.parttimejob.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.dingdan.DingdanXQActivity;
import com.qigaikj.parttimejob.adapter.SkillAdapter;
import com.qigaikj.parttimejob.adapter.XuqiuAdapter;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.DeleteBean;
import com.qigaikj.parttimejob.bean.MyXuqiuBean;
import com.qigaikj.parttimejob.bean.SkillCloseState;
import com.qigaikj.parttimejob.bean.SkillData;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的-技能页面
 */
public class SkillActivity extends BaseActivity {

    @BindView(R.id.lvSkill)
    ListView lvSkill;
    @BindView(R.id.tvReleaseSkill)
    TextView tvReleaseSkill;
    @BindView(R.id.rv)
    RecyclerView rv;

    private SkillAdapter adapter;
    private List<SkillData> SkillList = new ArrayList<>();
    private List<List<MyXuqiuBean.DataBean>> myXuqiuBeans = new ArrayList<>();
    private String state;
    private String token;
    private XuqiuAdapter xuqiuAdapter;
    private SkillAdapter skillAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_skill;
    }

    @Override
    protected void initDetail() {
        if ("0".equals(state)) {
            tvReleaseSkill.setText("发布技能");
            getSkillList();
        } else {
            tvReleaseSkill.setText("发布需求");
            getXuqiu();
        }

    }

    @Override
    protected void initTitleView() {
        token = SharedPreferencesHelper.getInstance(SkillActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        state = getIntent().getStringExtra("state");
        if ("0".equals(state)) {
            getTvTextTitle().setText("技能");
        } else {
            getTvTextTitle().setText("需求");
        }
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.tvReleaseSkill})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvReleaseSkill://发布技能 or 需求
                if ("0".equals(state)) {
                    ActivityUtils.startActivity(SkillActivity.this, ReleaseSkillActivity.class);
                } else {
                    ActivityUtils.startActivity(SkillActivity.this, ReleasePartTimeJobActivity.class);
                }
                break;
        }
    }

    /**
     * 需求接口
     */
    public void getXuqiu(){
        RetrofitManager.getInstance().createReq(BlogService.class).getXuqiu(token,"1","10").enqueue(new Callback<MyXuqiuBean>() {
            @Override
            public void onResponse(Call<MyXuqiuBean> call, Response<MyXuqiuBean> response) {
                    if (response.body().getData().size() > 0) {
                        if (myXuqiuBeans!=null){
                            myXuqiuBeans.clear();
                        }
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            myXuqiuBeans.add(response.body().getData());
                        }
                        xuqiuAdapter = new XuqiuAdapter(myXuqiuBeans,SkillActivity.this);
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SkillActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        rv.setLayoutManager(linearLayoutManager);
                        rv.setAdapter(xuqiuAdapter);
                        /*skillAdapter.setClickCallBack(new SkillAdapter.ItemClickCallBack() {
                            @Override
                            public void onItemClick(int pos, SkillData skillData,String kid) {
                                Intent intent=new Intent(SkillActivity.this,ReleaseSkillActivity.class);
                                intent.putExtra("zhuangtai","1");
                                intent.putExtra("bean",skillData);
                                intent.putExtra("kid",kid);
                                startActivity(intent);
                            }

                        });*/
                        xuqiuAdapter.setOnItemLongClickListener(new XuqiuAdapter.OnItemLongClickListener() {
                            @Override
                            public void onItemLongClick(View view, int position, String kid) {
                                showTanKuang(kid);
                            }
                        });
                }
            }

            @Override
            public void onFailure(Call<MyXuqiuBean> call, Throwable t) {

            }
        });
    }

    /**
     * 技能接口
     */
    public void getSkillList() {
        RetrofitManager.getInstance().createReq(BlogService.class).getSkillList(token, "1", "10").enqueue(new Callback<WrapperRspEntity<List<SkillData>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<SkillData>>> call, Response<WrapperRspEntity<List<SkillData>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (response.body().getData().size() > 0) {
                        SkillList.addAll(response.body().getData());
                        skillAdapter = new SkillAdapter(SkillList,SkillActivity.this);
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SkillActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        rv.setLayoutManager(linearLayoutManager);
                        rv.setAdapter(skillAdapter);
                        skillAdapter.setClickCallBack(new SkillAdapter.ItemClickCallBack() {
                            @Override
                            public void onItemClick(int pos, SkillData skillData,String kid) {
                                Intent intent=new Intent(SkillActivity.this,ReleaseSkillActivity.class);
                                intent.putExtra("zhuangtai","1");
                                intent.putExtra("bean",skillData);
                                intent.putExtra("kid",kid);
                                startActivity(intent);
                            }

                        });
                        skillAdapter.setOnItemLongClickListener(new SkillAdapter.OnItemLongClickListener() {
                            @Override
                            public void onItemLongClick(View view, int position,String kid) {
                                showTanKuang(kid);
                            }
                        });
                    } else {
                        showToastShort("暂无数据");
                    }
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<List<SkillData>>> call, Throwable t) {
                Log.i("0000000", t.toString());
            }
        });
    }
    private void showTanKuang(String kid) {
        View view1 = View.inflate(SkillActivity.this, R.layout.deletekuang, null);
        ImageView image = view1.findViewById(R.id.finsh);
        TextView jixu = view1.findViewById(R.id.jixu);
        TextView chakna = view1.findViewById(R.id.chakan);
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view1);
        //设置点击对话框内容之外对话框消失
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.width = 800;
        params.height = 600 ;
        dialog.getWindow().setAttributes(params);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        jixu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("0".equals(state)){
                    getDelete(kid);
                }else {
                    getXuqiuDelete(kid);
                }
                dialog.dismiss();
            }
        });
        chakna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 删除需求接口
     * @param kid
     */
    public void getXuqiuDelete(String kid){
        RetrofitManager.getInstance().createReq(BlogService.class).isxuqiudelete(token,kid).enqueue(new Callback<WrapperRspEntity<DeleteBean>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<DeleteBean>> call, Response<WrapperRspEntity<DeleteBean>> response) {
                String msg = response.body().getMsg();
                xuqiuAdapter.notifyDataSetChanged();
                Toast.makeText(SkillActivity.this,msg,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<DeleteBean>> call, Throwable t) {

            }
        });
    }

    /**
     * 删除技能
     * @param kid
     */
    public void getDelete(String kid){
        RetrofitManager.getInstance().createReq(BlogService.class).isdelete(token,kid).enqueue(new Callback<WrapperRspEntity<DeleteBean>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<DeleteBean>> call, Response<WrapperRspEntity<DeleteBean>> response) {
                String msg = response.body().getMsg();
                skillAdapter.notifyDataSetChanged();
                Toast.makeText(SkillActivity.this,msg,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<DeleteBean>> call, Throwable t) {

            }
        });
    }
}
