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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.PipeiActivity;
import com.qigaikj.parttimejob.activity.dingdan.DingdanXQActivity;
import com.qigaikj.parttimejob.activity.dingdan.FukuanXQActivity;
import com.qigaikj.parttimejob.adapter.DingdanYiYaoqingAdapter;
import com.qigaikj.parttimejob.adapter.DingdanjixingAdapter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.DingDanYueTaBean;
import com.qigaikj.parttimejob.bean.DingdanYiYaoqingBean;
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

public class YuetaYaoqingfrag extends Fragment {
    @BindView(R.id.pipei)
    TextView pipei;
    Unbinder unbinder;
    @BindView(R.id.yyrv)
    RecyclerView rv;
    @BindView(R.id.yiyaoqing)
    LinearLayout yiyaoqing;

    private List<List<DingDanYueTaBean.DataBean>> dataBeans=new ArrayList<List<DingDanYueTaBean.DataBean>>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yuetayaoqingfrag, container, false);
        unbinder = ButterKnife.bind(this, view);
        getYueTaData();
//        getDingdanYiyaoqing();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.pipei})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//                ActivityUtils.startActivity(getActivity(), DingdanXQActivity.class);
            case R.id.pipei:
                ActivityUtils.startActivity(getActivity(), PipeiActivity.class);
                break;
        }
    }
    public void getYueTaData(){
        String token = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getDingdanYueta(token,"1").enqueue(new Callback<DingDanYueTaBean>() {
            @Override
            public void onResponse(Call<DingDanYueTaBean> call, Response<DingDanYueTaBean> response) {
                if (dataBeans!=null){
                    dataBeans.clear();
                }
                for (int i = 0; i < response.body().getData().size(); i++) {
                    dataBeans.add(response.body().getData());
                }
                DingdanYiYaoqingAdapter dingdanAdapter=new DingdanYiYaoqingAdapter(dataBeans,getActivity());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(dingdanAdapter);
                dingdanAdapter.setClickCallBack(new DingdanYiYaoqingAdapter.ItemClickCallBack() {
                    @Override
                    public void onItemClick(int pos, String did) {
                        DingDanYueTaBean dingdanYiYaoqingBean=new DingDanYueTaBean();
                        dingdanYiYaoqingBean.setData(response.body().getData());
                        Intent intent=new Intent(getActivity(),DingdanXQActivity.class);
                        intent.putExtra("uid",did);
                        intent.putExtra("data",dingdanYiYaoqingBean);
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
