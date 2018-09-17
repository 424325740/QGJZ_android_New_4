package com.qigaikj.parttimejob.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.PinglunActivity;
import com.qigaikj.parttimejob.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/3/003.
 * 直播fragment
 */

public class FragmentTwo extends Fragment {


    @BindView(R.id.dianzan)
    ImageView dianzan;
    @BindView(R.id.lin_dianzan)
    LinearLayout linDianzan;
    Unbinder unbinder;
    boolean flag = false;
    @BindView(R.id.lin_pinglun)
    LinearLayout linPinglun;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_two, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.lin_dianzan, R.id.lin_pinglun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_dianzan:
                if (flag == false) {
                    dianzan.setBackground(getResources().getDrawable(R.mipmap.icon_dianzan_pre));
                    flag = true;
                } else {
                    dianzan.setBackground(getResources().getDrawable(R.mipmap.icon_dianzan_nor));
                    flag = false;
                }
                break;
            case R.id.lin_pinglun:
                ActivityUtils.startActivity(getActivity(), PinglunActivity.class);
                break;
        }
    }
}
