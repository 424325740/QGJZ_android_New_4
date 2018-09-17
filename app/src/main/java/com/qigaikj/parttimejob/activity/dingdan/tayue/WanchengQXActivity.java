package com.qigaikj.parttimejob.activity.dingdan.tayue;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.util.StarView;

import butterknife.OnClick;

public class WanchengQXActivity extends BaseActivity {
    private boolean mIsShowing = false;
    private PopupWindow popupWindow;
    private View pop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wancheng_qx;
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

    @OnClick({R.id.xingbie, R.id.tayuepingjia})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xingbie:
                break;
            case R.id.tayuepingjia:
                initPopup();
                if (!mIsShowing) {
//                    params.alpha= 0.3f;
//                    getActivity().getWindow().setAttributes(params);
                    popupWindow.showAtLocation(pop.findViewById(R.id.layout_main), Gravity.BOTTOM, 0, 0);
                    mIsShowing = true;
                    break;
                }
        }
    }
    private void initPopup() {
        int w =WanchengQXActivity.this.getWindowManager().getDefaultDisplay().getWidth();
        pop = View.inflate(WanchengQXActivity.this, R.layout.pingjiatanchu, null);
        popupWindow = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
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
    public void dismiss(){
        if(popupWindow != null &&mIsShowing){
            popupWindow.dismiss();
            mIsShowing = false;
//            params.alpha= 1f;
//            getActivity().getWindow().setAttributes(params);
        }
    }
}