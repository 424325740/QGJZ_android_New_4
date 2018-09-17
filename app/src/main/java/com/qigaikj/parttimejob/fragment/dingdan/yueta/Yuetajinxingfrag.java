package com.qigaikj.parttimejob.fragment.dingdan.yueta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.dingdan.FukuanActivity;
import com.qigaikj.parttimejob.activity.dingdan.FukuanXQActivity;
import com.qigaikj.parttimejob.adapter.DingdanAdapter;
import com.qigaikj.parttimejob.adapter.DingdanjixingAdapter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.DingDanYueTaBean;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Yuetajinxingfrag extends Fragment {
    @BindView(R.id.fukuan)
    TextView fukuan;
    @BindView(R.id.zhijiefu)
    TextView zhijiefu;
    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<List<DingDanYueTaBean.DataBean>> dataBeans=new ArrayList<List<DingDanYueTaBean.DataBean>>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yuetajinxingfrag, container, false);
        unbinder = ButterKnife.bind(this, view);
        getYueTaData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fukuan, R.id.zhijiefu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fukuan:
                ActivityUtils.startActivity(getActivity(), FukuanXQActivity.class);
                break;
            case R.id.zhijiefu:
                ActivityUtils.startActivity(getActivity(), FukuanActivity.class);
                break;
        }
    }
    public void getYueTaData(){
        String token = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getDingdanYueta(token,"2").enqueue(new Callback<DingDanYueTaBean>() {
            @Override
            public void onResponse(Call<DingDanYueTaBean> call, Response<DingDanYueTaBean> response) {
                if (dataBeans!=null){
                    dataBeans.clear();
                }
                for (int i = 0; i < response.body().getData().size(); i++) {
                    dataBeans.add(response.body().getData());
                }
                DingdanjixingAdapter dingdanAdapter=new DingdanjixingAdapter(dataBeans,getActivity());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(dingdanAdapter);
                dingdanAdapter.setItemCablack(new DingdanjixingAdapter.itemCablack() {
                    @Override
                    public void onItemClick(int pos) {
                        Intent intent=new Intent(getActivity(),FukuanXQActivity.class);
                        getActivity().startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<DingDanYueTaBean> call, Throwable t) {

            }
        });
    }
}
