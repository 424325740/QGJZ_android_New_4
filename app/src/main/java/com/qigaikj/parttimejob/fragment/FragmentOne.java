package com.qigaikj.parttimejob.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.AuthenticationActivity;
import com.qigaikj.parttimejob.activity.JuaoActivity;
import com.qigaikj.parttimejob.activity.my.CompanyActivity;
import com.qigaikj.parttimejob.activity.my.RealnameActivity;
import com.qigaikj.parttimejob.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/3/003.
 * 直播fragment
 */

public class FragmentOne extends Fragment {


    @BindView(R.id.xiala1)
    ImageView xiala1;
    @BindView(R.id.xiala2)
    ImageView xiala2;
    @BindView(R.id.xiala3)
    ImageView xiala3;
    @BindView(R.id.xiala4)
    ImageView xiala4;
    @BindView(R.id.xiala5)
    ImageView xiala5;
    @BindView(R.id.xiala6)
    ImageView xiala6;
    Unbinder unbinder;
    @BindView(R.id.xilacaidan3)
    LinearLayout xilacaidan3;
    @BindView(R.id.xilacaidan2)
    LinearLayout xilacaidan2;
    @BindView(R.id.xilacaidan1)
    LinearLayout xilacaidan1;
    @BindView(R.id.xilacaidan6)
    LinearLayout xilacaidan6;
    @BindView(R.id.xilacaidan5)
    LinearLayout xilacaidan5;
    @BindView(R.id.xilacaidan4)
    LinearLayout xilacaidan4;
    private Drawable drawable;
    private Drawable checked;
    boolean flag = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_one, container, false);
        unbinder = ButterKnife.bind(this, view);
        drawable = getResources().getDrawable(R.mipmap.btn_xia_nor);
        checked = getResources().getDrawable(R.mipmap.btn_xia_pre);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.lin, R.id.lin2, R.id.lin3, R.id.lin4, R.id.lin5, R.id.lin6, R.id.renzheng, R.id.shenfen, R.id.zhifubao, R.id.weixin, R.id.shouji, R.id.jifen, R.id.gongsi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin6:
                if (flag == false) {
                    xiala1.setBackground(checked);
                    xilacaidan1.setVisibility(View.VISIBLE);
                    flag = true;
                } else {
                    xiala1.setBackground(drawable);
                    xilacaidan1.setVisibility(View.GONE);
                    flag = false;
                }
                break;
            case R.id.lin5:
                if (flag == false) {
                    xiala2.setBackground(checked);
                    xilacaidan2.setVisibility(View.VISIBLE);
                    flag = true;
                } else {
                    xiala2.setBackground(drawable);
                    xilacaidan2.setVisibility(View.GONE);
                    flag = false;
                }
                break;
            case R.id.lin4:
                if (flag == false) {
                    xiala3.setBackground(checked);
                    xilacaidan3.setVisibility(View.VISIBLE);
                    flag = true;
                } else {
                    xiala3.setBackground(drawable);
                    xilacaidan3.setVisibility(View.GONE);
                    flag = false;
                }
                break;
            case R.id.lin3:
                if (flag == false) {
                    xiala4.setBackground(checked);
                    xilacaidan4.setVisibility(View.VISIBLE);
                    flag = true;
                } else {
                    xiala4.setBackground(drawable);
                    xilacaidan4.setVisibility(View.GONE);
                    flag = false;
                }
                break;
            case R.id.lin2:
                if (flag == false) {
                    xiala5.setBackground(checked);
                    xilacaidan5.setVisibility(View.VISIBLE);
                    flag = true;
                } else {
                    xiala5.setBackground(drawable);
                    xilacaidan5.setVisibility(View.GONE);
                    flag = false;
                }
                break;
            case R.id.lin:
                if (flag == false) {
                    xiala6.setBackground(checked);
                    xilacaidan6.setVisibility(View.VISIBLE);
                    flag = true;
                } else {
                    xiala6.setBackground(drawable);
                    xilacaidan6.setVisibility(View.GONE);
                    flag = false;
                }
                break;
            case R.id.renzheng:
//                ActivityUtils.startActivity(getActivity(), AuthenticationActivity.class);
                break;
            case R.id.weixin://微信认证
                ActivityUtils.startActivity(getActivity(), AuthenticationActivity.class);
                break;
            case R.id.zhifubao://支付宝
                ActivityUtils.startActivity(getActivity(), AuthenticationActivity.class);
                break;
            case R.id.shenfen://身份认证
                ActivityUtils.startActivity(getActivity(), RealnameActivity.class);
            break;
            case R.id.jifen://积分认证
                ActivityUtils.startActivity(getActivity(), AuthenticationActivity.class);
                break;
            case R.id.shouji://手机认证
                ActivityUtils.startActivity(getActivity(), AuthenticationActivity.class);
            break;
            case R.id.gongsi://公司认证
                ActivityUtils.startActivity(getActivity(), CompanyActivity.class);
                break;
        }
    }
}
