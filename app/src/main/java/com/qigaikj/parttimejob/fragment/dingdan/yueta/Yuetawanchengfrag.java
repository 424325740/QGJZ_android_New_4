package com.qigaikj.parttimejob.fragment.dingdan.yueta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.dingdan.DingdanXQActivity;
import com.qigaikj.parttimejob.activity.dingdan.PingjiaQXActivity;
import com.qigaikj.parttimejob.adapter.DingdanWanchengAdapter;
import com.qigaikj.parttimejob.adapter.DingdanYiYaoqingAdapter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.DingDanYueTaBean;
import com.qigaikj.parttimejob.bean.PingjiaBean;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.util.StarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Yuetawanchengfrag extends Fragment {
    @BindView(R.id.kefu)
    TextView kefu;
    @BindView(R.id.yuetapingjia)
    TextView pingjia;
    @BindView(R.id.zhijieping)
    TextView zhijieping;
    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView rv;
    private PopupWindow popupWindow;
    private WindowManager.LayoutParams params;
    private boolean mIsShowing = false;
    private View view;
    private View pop;
    private List<List<DingDanYueTaBean.DataBean>> dataBeans = new ArrayList<List<DingDanYueTaBean.DataBean>>();
    private String token;
    private String id;
    private int level;
    private Editable text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.yeutawancheng, container, false);
        unbinder = ButterKnife.bind(this, view);
        params = getActivity().getWindow().getAttributes();
        getYueTaData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.yuetapingjia, R.id.zhijieping})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.yuetapingjia:
                ActivityUtils.startActivity(getActivity(), PingjiaQXActivity.class);
                break;
            case R.id.zhijieping:
                initPopup();
                if (!mIsShowing) {
                    params.alpha = 0.3f;
                    getActivity().getWindow().setAttributes(params);
                    popupWindow.showAtLocation(pop.findViewById(R.id.layout_main), Gravity.BOTTOM, 0, 0);
                    mIsShowing = true;
                }
                break;
        }
    }

    /**
     * 约TA全部状态完成
     */
    public void getYueTaData() {
        token = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getDingdanYueta(token, "3").enqueue(new Callback<DingDanYueTaBean>() {
            @Override
            public void onResponse(Call<DingDanYueTaBean> call, Response<DingDanYueTaBean> response) {
                if (dataBeans != null) {
                    dataBeans.clear();
                }
                for (int i = 0; i < response.body().getData().size(); i++) {
                    dataBeans.add(response.body().getData());
                }
                DingdanWanchengAdapter dingdanAdapter = new DingdanWanchengAdapter(dataBeans, getActivity());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(dingdanAdapter);
                dingdanAdapter.setClickCallBack(new DingdanWanchengAdapter.ItemClickCallBack() {
                    @Override
                    public void onItemClick(int pos, String did) {
                        id = did;
                        Intent intent=new Intent(getActivity(),PingjiaQXActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<DingDanYueTaBean> call, Throwable t) {

            }
        });
    }

    private void initPopup() {
        int w = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        pop = View.inflate(getActivity(), R.layout.pingjiatanchu, null);
        popupWindow = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        /* *//**背景阴影*//*
        ColorDrawable dw = new ColorDrawable(0x80000000);
        popupWindow.setBackgroundDrawable(dw);*/
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismiss();
            }
        });
        popupWindow.setWidth(w);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mIsShowing = false;
        StarView mStarView = pop.findViewById(R.id.star_view);
        EditText neirong = pop.findViewById(R.id.neirong);
        TextView pingjia = pop.findViewById(R.id.pingjia);
        mStarView.setLevel(5);
        mStarView.setCanSelected(true);
        level = mStarView.getLevel();
        ImageView finsh = pop.findViewById(R.id.finsh);
        text = neirong.getText();
        finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text ==null){
                    Toast.makeText(getActivity(),"请输入评价内容",Toast.LENGTH_LONG).show();
                    return;
                }
                getPingjia();
                dismiss();
            }
        });
    }
    public void getPingjia(){
        RetrofitManager.getInstance().createReq(BlogService.class).pingjia(token,id,level+"",text+"").enqueue(new Callback<PingjiaBean>() {
            @Override
            public void onResponse(Call<PingjiaBean> call, Response<PingjiaBean> response) {
                if (response.body()!=null){
                    String message = response.body().getMessage();
                    Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PingjiaBean> call, Throwable t) {

            }
        });
    }
    public void dismiss() {
        if (popupWindow != null && mIsShowing) {
            popupWindow.dismiss();
            mIsShowing = false;
            params.alpha = 1f;
            getActivity().getWindow().setAttributes(params);
        }
    }
}
