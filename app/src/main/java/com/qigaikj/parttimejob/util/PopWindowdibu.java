package com.qigaikj.parttimejob.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;

import java.util.ArrayList;
import java.util.List;

public class PopWindowdibu extends PopupWindow {
    private View conentView;
    private final ImageView fins;
    private final TextView shi;
    private final TextView tian;
    private TextView tv1;
    private List<Boolean> checkeds;

    public PopWindowdibu(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.fabutanchu, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态  
        this.update();
        // 实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作  
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPopWindow);
        fins = conentView.findViewById(R.id.find);
        shi = conentView.findViewById(R.id.shi);
        tian = conentView.findViewById(R.id.tian);
        checkeds = new ArrayList<>(2);
        checkeds.add(false);
        checkeds.add(true);
        Drawable checked = context.getResources().getDrawable(R.drawable.yuanjiaobut);
        Drawable unChecked = context.getResources().getDrawable(R.drawable.yuanjiaohome);
        shi.setOnClickListener(view -> {
            checkeds.set(0, true);
            checkeds.set(1, false);
            tv1.setText(checkeds.get(0) ? "时" : "天");
            shi.setBackground(checkeds.get(0) ? checked : unChecked);
            //销毁弹出框
            dismiss();

        });
        shi.setBackground(checkeds.get(0) ? checked : unChecked);
        shi.setTextColor(checkeds.get(0) ? context.getResources().getColor(R.color.white) : context.getResources().getColor(R.color.mytext_2));
        tian.setOnClickListener(view -> {
            checkeds.set(0, false);
            checkeds.set(1, true);
            tv1.setText(checkeds.get(1) ? "天" : "时");
            tian.setBackground(checkeds.get(1) ? checked : unChecked);
            //销毁弹出框
            dismiss();
        });
        tian.setBackground(checkeds.get(1) ? checked : unChecked);
        tian.setTextColor(checkeds.get(1) ? context.getResources().getColor(R.color.white) : context.getResources().getColor(R.color.mytext_2));
        fins.setOnClickListener(view -> {
            //销毁弹出框
            dismiss();
        });
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent, TextView tv) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow  
            this.showAsDropDown(parent, parent.getLayoutParams().width * 4, parent.getLayoutParams().height * 10);
            tv1 = tv;
        } else {
            this.dismiss();
        }
    }
}  