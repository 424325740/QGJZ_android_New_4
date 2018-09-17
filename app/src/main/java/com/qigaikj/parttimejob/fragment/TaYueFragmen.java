package com.qigaikj.parttimejob.fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.HomeSearchBean;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaYueFragmen extends Fragment {
    private String title;
    private List<HomeSearchBean.DataBean> homeSearchBeans = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tayeufrag, container, false);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if(bundle!=null){
            title = bundle.getString("title");
        }
        getData(title);
        return view;
    }
    /**
     * 搜索数据请求
     */
    public void getData(String title) {
        String token = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeSearch(token, title, "2").enqueue(new Callback<HomeSearchBean>() {
            @Override
            public void onResponse(Call<HomeSearchBean> call, Response<HomeSearchBean> response) {
                homeSearchBeans.add(response.body().getData());
                HomeSearchBean.DataBean data = response.body().getData();
                Bundle bundle = new Bundle();
                bundle.putString("title",title);
            }

            @Override
            public void onFailure(Call<HomeSearchBean> call, Throwable t) {

            }
        });
    }
}
