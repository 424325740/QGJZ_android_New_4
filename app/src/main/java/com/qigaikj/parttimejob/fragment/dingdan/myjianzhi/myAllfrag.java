package com.qigaikj.parttimejob.fragment.dingdan.myjianzhi;

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
import com.qigaikj.parttimejob.adapter.MyJianzhiAdapter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.MyJianzhiBean;
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

public class myAllfrag extends Fragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private List<List<MyJianzhiBean.DataBean>> dataBeans = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myall, container, false);
        unbinder = ButterKnife.bind(this, view);
        getJianzhiData();
        return view;
    }

    /**
     * 获取我的兼职数据
     */
    public void getJianzhiData() {
        String token = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getMyjianzhi(token, "4").enqueue(new Callback<MyJianzhiBean>() {
            @Override
            public void onResponse(Call<MyJianzhiBean> call, Response<MyJianzhiBean> response) {
                if (response.body().getData() != null) {
                    if (dataBeans != null) {
                        dataBeans.clear();
                    }
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        dataBeans.add(response.body().getData());
                    }
                    MyJianzhiAdapter myJianzhiAdapter=new MyJianzhiAdapter(dataBeans,getActivity());
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rv.setLayoutManager(linearLayoutManager);
                    rv.setAdapter(myJianzhiAdapter);
                }
            }

            @Override
            public void onFailure(Call<MyJianzhiBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
