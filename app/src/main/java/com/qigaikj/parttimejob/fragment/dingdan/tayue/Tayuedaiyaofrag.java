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
import android.widget.TextView;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.dingdan.tayue.DaiyingyaoQXActivity;
import com.qigaikj.parttimejob.adapter.TatyueAllAtapter;
import com.qigaikj.parttimejob.adapter.TatyueDaiyaoAtapter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.PingjiaBean;
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

public class Tayuedaiyaofrag extends Fragment {
    @BindView(R.id.yingyao)
    TextView yingyao;
    @BindView(R.id.tayuelin)
    LinearLayout tayuelin;
    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<List<TaYuewoBean.DataBean>> dataBeans = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tayuedaiyao, container, false);
        unbinder = ButterKnife.bind(this, view);
        getTaYue();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.yingyao, R.id.tayuelin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yingyao:
                break;
            case R.id.tayuelin:
                ActivityUtils.startActivity(getActivity(), DaiyingyaoQXActivity.class);
                break;
        }
    }
    /**
     * TA约我数据请求
     */
    public void getTaYue() {
        String token = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getTayuewo(token, "1").enqueue(new Callback<TaYuewoBean>() {
            @Override
            public void onResponse(Call<TaYuewoBean> call, Response<TaYuewoBean> response) {
                if (dataBeans!=null){
                    dataBeans.clear();
                }
                for (int i = 0; i < response.body().getData().size(); i++) {
                    dataBeans.add(response.body().getData());
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(layoutManager);
                TatyueDaiyaoAtapter atapter=new TatyueDaiyaoAtapter(dataBeans,getActivity());
                rv.setAdapter(atapter);
                atapter.setClickCallBack(new TatyueDaiyaoAtapter.Oncklickitem() {
                    @Override
                    public void ItemCaoblack(String did) {
                    getIsYingyao(did);
                    }
                });
            }

            @Override
            public void onFailure(Call<TaYuewoBean> call, Throwable t) {

            }
        });
    }
    /**
     * 应邀
     */
    public void getIsYingyao(String did) {
        String token = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getYingyao(token, did).enqueue(new Callback<PingjiaBean>() {
            @Override
            public void onResponse(Call<PingjiaBean> call, Response<PingjiaBean> response) {
                String message = response.body().getMessage();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<PingjiaBean> call, Throwable t) {

            }
        });
    }
}
