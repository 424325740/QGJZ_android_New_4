package com.qigaikj.parttimejob.fragment.dingdan.yueta;

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
import com.qigaikj.parttimejob.adapter.DingdanAdapter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.DingDanYueTaBean;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YuetaAllfrag extends Fragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private List<List<DingDanYueTaBean.DataBean>> dataBeans=new ArrayList<List<DingDanYueTaBean.DataBean>>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ddyuetaall, container, false);
        unbinder = ButterKnife.bind(this, view);
        getYueTaData();
        return view;
    }
    public void getYueTaData(){
        String token = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getDingdanYueta(token,"4").enqueue(new Callback<DingDanYueTaBean>() {
            @Override
            public void onResponse(Call<DingDanYueTaBean> call, Response<DingDanYueTaBean> response) {
                if (dataBeans!=null){
                    dataBeans.clear();
                }
                for (int i = 0; i < response.body().getData().size(); i++) {
                    dataBeans.add(response.body().getData());
                }
                DingdanAdapter dingdanAdapter=new DingdanAdapter(dataBeans,getActivity());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(dingdanAdapter);
            }

            @Override
            public void onFailure(Call<DingDanYueTaBean> call, Throwable t) {

            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
