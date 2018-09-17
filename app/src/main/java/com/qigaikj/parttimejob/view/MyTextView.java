package com.qigaikj.parttimejob.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.qigaikj.parttimejob.util.DisplayUtil;

/**
 * @author KBTS
 * @date 2018/5/25
 * @description
 */
public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    
    public float fSelectTextSize = 40;
    public float fUnSelectTextSize = 32;
    
    public static final int SELECT_TEXT_COLOR = Color.parseColor("#333333");
    public static final int UNSELECT_TEXT_COLOR = Color.parseColor("#999999");
    
    private boolean isSelect = false;
    
    public MyTextView(Context context) {
        this(context, null);
    }
    
    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        fSelectTextSize = DisplayUtil.dip2px(context, 6);
        fUnSelectTextSize = DisplayUtil.dip2px(context, 6);
        setTextSize(fUnSelectTextSize);
        setTextColor(UNSELECT_TEXT_COLOR);
    }
    
    public void setSelect(boolean isSelect) {
        if (isSelect) {
            setTextSize(fSelectTextSize);
            setTextColor(SELECT_TEXT_COLOR);
        } else {
            setTextSize(fUnSelectTextSize);
            setTextColor(UNSELECT_TEXT_COLOR);
        }
    }
}
