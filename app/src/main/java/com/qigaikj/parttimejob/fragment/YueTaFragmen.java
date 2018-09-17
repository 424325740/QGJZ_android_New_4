package com.qigaikj.parttimejob.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.SearchJiluAdapeter;
import com.qigaikj.parttimejob.adapter.SearchremenAdapeter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.HomeSearchBean;
import com.qigaikj.parttimejob.bean.SearchJiluBean;
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

public class YueTaFragmen extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.yuetagridview)
    GridView yuetagridview;
    private List<SearchJiluBean.DataBean> dataBeans = new ArrayList<>();
    private List<SearchJiluBean.DataBean> beanList=new ArrayList<>();
    private List<HomeSearchBean.DataBean> homeSearchBeans = new ArrayList<>();
    private String title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yuetagrag, container, false);
        unbinder = ButterKnife.bind(this, view);
        getSearchData();
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
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeSearch(token, title, "1").enqueue(new Callback<HomeSearchBean>() {
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
    /**
     * 搜索记录
     */
    public void getSearchData() {
        String token = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeSearchjilu(token).enqueue(new Callback<SearchJiluBean>() {
            @Override
            public void onResponse(Call<SearchJiluBean> call, Response<SearchJiluBean> response) {
                if (dataBeans!=null){
                    dataBeans.clear();
                }
                if (beanList!=null){
                    beanList.clear();
                }
                for (int i = 0; i < 10; i++) {
                    dataBeans.add(response.body().getData());
                }
                for (int i = 0; i < response.body().getData().getYueta().size(); i++) {
                    beanList.add(response.body().getData());
                }
                SearchJiluAdapeter searchJiluAdapeter = new SearchJiluAdapeter(dataBeans, getActivity());
                SearchremenAdapeter searchremenAdapeter=new SearchremenAdapeter(beanList,getActivity());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                gridview.setAdapter(searchJiluAdapeter);
                yuetagridview.setAdapter(searchremenAdapeter);
            }

            @Override
            public void onFailure(Call<SearchJiluBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
