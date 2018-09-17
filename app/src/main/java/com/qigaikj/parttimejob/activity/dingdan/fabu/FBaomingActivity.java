package com.qigaikj.parttimejob.activity.dingdan.fabu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.BaomingAdapter;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.BaomingGuanliBean;
import com.qigaikj.parttimejob.bean.LuyongBeans;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBaomingActivity extends BaseActivity {

    @BindView(R.id.xiala)
    ImageView xiala;
    @BindView(R.id.show_lin)
    LinearLayout showLin;
    @BindView(R.id.select_goin)
    ImageView selectGoin;
    @BindView(R.id.yongta)
    TextView yongta;
    @BindView(R.id.show)
    LinearLayout show;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.count)
    TextView count;
    @BindView(R.id.start_date)
    TextView startDate;
    @BindView(R.id.end_date)
    TextView endDate;
    @BindView(R.id.way)
    TextView way;
    private boolean isshow = false;
    private boolean ischecked = false;
    private String wid;
    private List<BaomingGuanliBean.DataBean> dataBeans = new ArrayList<>();
    private String string;
    private BaomingAdapter baomingAdapter;
    private boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getLuyong();
        getBaoming();
    }

    @Override
    protected int getContentViewId() {
        Intent intent = getIntent();
        wid = intent.getStringExtra("wid");
        return R.layout.activity_fbaoming;
    }

    @Override
    protected void initDetail() {
        string = SharedPreferencesHelper.getInstance(FBaomingActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("报名管理");
    }

    @Override
    protected void initTitleView() {

    }

    @OnClick({R.id.show_lin, R.id.yongta,R.id.cbCheckBox})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.show_lin:
                if (isshow == false) {
                    show.setVisibility(View.VISIBLE);
                    isshow = true;
                } else {
                    show.setVisibility(View.GONE);
                    isshow = false;
                }
                break;
            case R.id.yongta:
                if (ischecked == false) {
                    yongta.setBackground(getResources().getDrawable(R.drawable.yuanjiaohome));
                    yongta.setText("已录用");
                    yongta.setTextColor(getResources().getColor(R.color.mytext_2));
                }
                break;
            case R.id.cbCheckBox:
                Map<Integer, Boolean> map = baomingAdapter.getMap();
                if (flag==false){
                    baomingAdapter.initCheck(true);
                    baomingAdapter.notifyDataSetChanged();
                    flag=true;
                }else {
                    baomingAdapter.initCheck(false);
                    baomingAdapter.notifyDataSetChanged();
                    flag=false;
                }
                break;
        }
    }

    /**
     * 报名管理
     */
    public void getBaoming() {
        RetrofitManager.getInstance().createReq(BlogService.class).getBaoming(string, wid).enqueue(new Callback<BaomingGuanliBean>() {
            @Override
            public void onResponse(Call<BaomingGuanliBean> call, Response<BaomingGuanliBean> response) {
                if (response.body().getData().getUsers()!=null){
                    if (dataBeans != null) {
                        dataBeans.clear();
                    }
                    int size = response.body().getData().getUsers().size();
                    for (int i = 0; i <size; i++) {
                        dataBeans.add(response.body().getData());
                    }
                    baomingAdapter = new BaomingAdapter(FBaomingActivity.this,dataBeans);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FBaomingActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rv.setLayoutManager(linearLayoutManager);
                    rv.setAdapter(baomingAdapter);
                    for (int i = 0; i < dataBeans.size(); i++) {
                        title.setText(dataBeans.get(i).getWork().getTitle());
                        count.setText(dataBeans.get(i).getWork().getCount()+"人已报名");
                        startDate.setText(dataBeans.get(i).getWork().getStart_date());
                        endDate.setText(dataBeans.get(i).getWork().getEnd_date());
                        String way = dataBeans.get(i).getWork().getWay();
                        if (way.equals("1")){
                            FBaomingActivity.this.way.setText("日结");
                        }else {
                            FBaomingActivity.this.way.setText("完工结");
                        }
                    }
                    baomingAdapter.setClickCallBack(new BaomingAdapter.ItemClickCallBack() {
                        @Override
                        public void onItemClick(String uid, TextView textView) {
                            getIsLuyong(uid,textView);
                        }

                    });
                }

            }

            @Override
            public void onFailure(Call<BaomingGuanliBean> call, Throwable t) {

            }
        });
    }
    /**
     * 录用接口
     */
    public void getIsLuyong(String uid,TextView textView){
        RetrofitManager.getInstance().createReq(BlogService.class).getluyongren(string,wid,uid).enqueue(new Callback<LuyongBeans>() {
            @Override
            public void onResponse(Call<LuyongBeans> call, Response<LuyongBeans> response) {
                Toast.makeText(FBaomingActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                baomingAdapter.notifyDataSetChanged();
                textView.setBackground(getResources().getDrawable(R.drawable.yuanjiaohome));
                textView.setText("已录用");
                textView.setTextColor(getResources().getColor(R.color.mytext_2));
            }

            @Override
            public void onFailure(Call<LuyongBeans> call, Throwable t) {

            }
        });
    }

}
