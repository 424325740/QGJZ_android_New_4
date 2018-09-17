package com.qigaikj.parttimejob.activity.dingdan;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.DingdanYiYaoqingXQAdapter;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.DingDanYueTaBean;
import com.qigaikj.parttimejob.bean.DingdanYiYaoqingBean;
import com.qigaikj.parttimejob.bean.QuerenYuetaBean;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DingdanXQActivity extends BaseActivity {

    @BindView(R.id.xingbie)
    LinearLayout xingbie;
    @BindView(R.id.jineng)
    LinearLayout jineng;
    @BindView(R.id.show_lin)
    LinearLayout showLin;
    @BindView(R.id.xiala)
    ImageView xiala;
    @BindView(R.id.rv)
    RecyclerView rv;
    private boolean flag = false;
    private View view1;
    private TextView jixu;
    private boolean islin1 = true;
    private boolean islin2 = false;
    private boolean islin3 = false;
    private String message;
    private String did;
    private List<List<DingdanYiYaoqingBean.DataBean>> yiYaoqingBeans = new ArrayList<List<DingdanYiYaoqingBean.DataBean>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        did = intent.getStringExtra("uid");
        DingDanYueTaBean data = (DingDanYueTaBean) intent.getSerializableExtra("data");
        getDingdanYiyaoqing();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_dingdan_xq;
    }

    @Override
    protected void initDetail() {
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("订单详情");
    }

    @Override
    protected void initTitleView() {

    }

    @OnClick({R.id.show_lin, R.id.chengyijin, R.id.wuchengyijin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.show_lin:
                if (flag == false) {
                    xiala.setBackground(getResources().getDrawable(R.mipmap.btn_shangla));
                    jineng.setVisibility(View.VISIBLE);
                    xingbie.setVisibility(View.VISIBLE);
                    flag = true;
                } else {
                    jineng.setVisibility(View.GONE);
                    xingbie.setVisibility(View.GONE);
                    xiala.setBackground(getResources().getDrawable(R.mipmap.icon_xialala));
                    flag = false;
                }
                break;
            case R.id.chengyijin:
                showChengyijin();
                break;
            case R.id.wuchengyijin:
                getisYueta();
                break;
        }
    }

    /**
     * 已邀请数据请求
     */
    public void getDingdanYiyaoqing() {
        String tocken = SharedPreferencesHelper.getInstance(DingdanXQActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getDingdanYiyyaoqing(tocken, did).enqueue(new Callback<DingdanYiYaoqingBean>() {
            @Override
            public void onResponse(Call<DingdanYiYaoqingBean> call, Response<DingdanYiYaoqingBean> response) {
                if (yiYaoqingBeans != null) {
                    yiYaoqingBeans.clear();
                }
                for (int i = 0; i < response.body().getData().size(); i++) {
                    yiYaoqingBeans.add(response.body().getData());
                }
                DingdanYiYaoqingXQAdapter dingdanYiYaoqingXQAdapter=new DingdanYiYaoqingXQAdapter(yiYaoqingBeans,DingdanXQActivity.this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(DingdanXQActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(dingdanYiYaoqingXQAdapter);
            }

            @Override
            public void onFailure(Call<DingdanYiYaoqingBean> call, Throwable t) {

            }
        });
    }

    private void showChengyijin() {
        View inflate = View.inflate(DingdanXQActivity.this, R.layout.chengyijin, null);
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        //设置点击对话框内容之外对话框消失
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        LinearLayout lin1 = inflate.findViewById(R.id.linzf1);
        LinearLayout lin2 = inflate.findViewById(R.id.linzf2);
        LinearLayout lin3 = inflate.findViewById(R.id.linzf3);
        ImageView weixin = inflate.findViewById(R.id.weixin);
        ImageView zhifubao = inflate.findViewById(R.id.zhifubao);
        ImageView qianbao = inflate.findViewById(R.id.qianbao);
        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weixin.setBackground(getResources().getDrawable(R.mipmap.btn_select_pre));
                zhifubao.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
                qianbao.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
            }
        });
        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (islin2 == false) {
                    zhifubao.setBackground(getResources().getDrawable(R.mipmap.btn_select_pre));
                    weixin.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
                    qianbao.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
                }
            }
        });
        lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (islin3 == false) {
                    qianbao.setBackground(getResources().getDrawable(R.mipmap.btn_select_pre));
                    weixin.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
                    zhifubao.setBackground(getResources().getDrawable(R.mipmap.btn_select_nor));
                }
            }
        });
        inflate.findViewById(R.id.finsh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void showTanKuang() {
        view1 = View.inflate(DingdanXQActivity.this, R.layout.tanchukuang, null);
        ImageView image = view1.findViewById(R.id.finsh);
        TextView jixu = view1.findViewById(R.id.jixu);
        TextView chakna = view1.findViewById(R.id.chakan);
        TextView isyueta = view1.findViewById(R.id.isyueta);
        isyueta.setText(message);
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view1);
        //设置点击对话框内容之外对话框消失
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.width = 800;
        params.height = 600;
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
                dialog.dismiss();
            }
        });
        chakna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
    }

    /**
     * 是否约她请求
     */
    public void getisYueta() {
        String toekn = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getQuerenYueta(toekn, did, "").enqueue(new Callback<QuerenYuetaBean>() {
            @Override
            public void onResponse(Call<QuerenYuetaBean> call, Response<QuerenYuetaBean> response) {
                message = response.body().getMessage();
                showTanKuang();
            }

            @Override
            public void onFailure(Call<QuerenYuetaBean> call, Throwable t) {

            }
        });
    }
}
