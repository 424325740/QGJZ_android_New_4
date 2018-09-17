package com.qigaikj.parttimejob.fragment.dingdan.jianzhi;

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

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.dingdan.fabu.FBaomingActivity;
import com.qigaikj.parttimejob.adapter.FabuBaomingAtapter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.FabuJianzhiBean;
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

public class fabubaomingfrag extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<List<FabuJianzhiBean.DataBean>> dataBeans = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fabubaoming, container, false);
        unbinder = ButterKnife.bind(this, view);
        getFabuData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 发布订单数据请求
     */
    private void getFabuData() {
        String toekn = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getFabujianzhi(toekn, "1").enqueue(new Callback<FabuJianzhiBean>() {
            @Override
            public void onResponse(Call<FabuJianzhiBean> call, Response<FabuJianzhiBean> response) {
                if (dataBeans!=null){
                    dataBeans.clear();
                }
                for (int i = 0; i < response.body().getData().size(); i++) {
                    dataBeans.add(response.body().getData());
                }
                FabuBaomingAtapter fabuAllAtapter = new FabuBaomingAtapter( dataBeans,getActivity());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(fabuAllAtapter);
                fabuAllAtapter.setItemClickCallBack(new FabuBaomingAtapter.ItemClick() {
                    @Override
                    public void onIemcilbck(String wid) {
                        Intent intent=new Intent(getActivity(),FBaomingActivity.class);
                        intent.putExtra("wid",wid);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<FabuJianzhiBean> call, Throwable t) {

            }
        });
    }
/*
    @OnClick(R.id.fabubaoming)
    public void onViewClicked() {
        ActivityUtils.startActivity(getActivity(), FBaomingActivity.class);
    }*/
}
