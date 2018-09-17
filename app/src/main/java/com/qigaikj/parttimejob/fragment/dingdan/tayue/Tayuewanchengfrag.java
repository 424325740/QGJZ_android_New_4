package com.qigaikj.parttimejob.fragment.dingdan.tayue;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.dingdan.tayue.WanchengQXActivity;
import com.qigaikj.parttimejob.adapter.TatyueDaiyaoAtapter;
import com.qigaikj.parttimejob.adapter.TatyueWanchengAtapter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.TaYuewoBean;
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

public class Tayuewanchengfrag extends Fragment {
    @BindView(R.id.tayuewancheng)
    LinearLayout tayuewancheng;
    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<List<TaYuewoBean.DataBean>> dataBeans = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tayuewancheng, container, false);
        unbinder = ButterKnife.bind(this, view);
        getTaYue();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.icon_User_4, R.id.tayuewancheng})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon_User_4:
                break;
            case R.id.tayuewancheng:
                ActivityUtils.startActivity(getActivity(), WanchengQXActivity.class);
                break;
        }
    }

    /**
     * TA约我数据请求
     */
    public void getTaYue() {
        String token = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getTayuewo(token, "3").enqueue(new Callback<TaYuewoBean>() {
            @Override
            public void onResponse(Call<TaYuewoBean> call, Response<TaYuewoBean> response) {
                if (dataBeans != null) {
                    dataBeans.clear();
                }
                for (int i = 0; i < response.body().getData().size(); i++) {
                    dataBeans.add(response.body().getData());
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(layoutManager);
                TatyueWanchengAtapter atapter = new TatyueWanchengAtapter(dataBeans, getActivity());
                rv.setAdapter(atapter);
            }

            @Override
            public void onFailure(Call<TaYuewoBean> call, Throwable t) {

            }
        });
    }
}
