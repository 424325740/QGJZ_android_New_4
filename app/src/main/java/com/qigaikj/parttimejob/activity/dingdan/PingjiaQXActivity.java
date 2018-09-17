package com.qigaikj.parttimejob.activity.dingdan;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.util.StarView;

import butterknife.BindView;
import butterknife.OnClick;

public class PingjiaQXActivity extends BaseActivity {
    @BindView(R.id.xiala)
    ImageView xiala;
    @BindView(R.id.xingbie)
    LinearLayout xingbie;
    @BindView(R.id.jineng)
    LinearLayout jineng;
    private PopupWindow popupWindow;
    private boolean mIsShowing = false;
    private View pop;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_pingjia_qx;
    }

    @Override
    protected void initDetail() {
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("订单详情");
    }

    @Override
    protected void initTitleView() {

    }

    @OnClick({R.id.show_lin, R.id.zhijiepingjia})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.show_lin:
                if (flag == false) {
                    jineng.setVisibility(View.VISIBLE);
                    xingbie.setVisibility(View.VISIBLE);
                    xiala.setBackground(getResources().getDrawable(R.mipmap.btn_shangla));
                    flag = true;
                } else {
                    jineng.setVisibility(View.GONE);
                    xingbie.setVisibility(View.GONE);
                    xiala.setBackground(getResources().getDrawable(R.mipmap.btn_lalalalaal));
                    flag = false;
                }
                break;
            case R.id.zhijiepingjia:
                initPopup();
                if (!mIsShowing) {
//                    params.alpha= 0.3f;
//                    getActivity().getWindow().setAttributes(params);
                    popupWindow.showAtLocation(pop.findViewById(R.id.layout_main), Gravity.BOTTOM, 0, 0);
                    mIsShowing = true;
                }
                break;
        }
    }

    private void initPopup() {
        int w = PingjiaQXActivity.this.getWindowManager().getDefaultDisplay().getWidth();
        pop = View.inflate(PingjiaQXActivity.this, R.layout.pingjiatanchu, null);
        popupWindow = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(w);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mIsShowing = false;
        StarView mStarView = pop.findViewById(R.id.star_view);
        mStarView.setLevel(5);
        mStarView.setCanSelected(true);
        ImageView finsh = pop.findViewById(R.id.finsh);
        finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void dismiss() {
        if (popupWindow != null && mIsShowing) {
            popupWindow.dismiss();
            mIsShowing = false;
//            params.alpha= 1f;
//            getActivity().getWindow().setAttributes(params);
        }
    }


}
