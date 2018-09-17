package com.qigaikj.parttimejob.activity.my;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.view.LabelLayout;

import butterknife.BindView;

public class LabelActivity extends BaseActivity implements View.OnClickListener {


    private String lableName[] = {"游戏", "玩游戏",
            "吃饭", "物联世界", "恒生期货", "恒生现金宝"
    };
    private String lableName1[] = {"恒生价值精选",
            "恒生混合平衡", "恒生债券", "广发证券"
    };
    private TextView tv_size;
    private int length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_label;
    }

    @Override
    protected void initDetail() {
    }

    @Override
    protected void initTitleView() {
        setTvTextTitle("标签编辑");
        setTvRightText("保存");
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initData() {
    }

    LabelLayout labelLayout, labelLayoutall;
    ViewGroup.MarginLayoutParams lp;

    private void initView() {
        lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        tv_size = findViewById(R.id.top_size);
        labelLayout = (LabelLayout) findViewById(R.id.top_rv);
        labelLayoutall = (LabelLayout) findViewById(R.id.but_rv);
        for (String aLableName : lableName) {
            length = lableName.length;
            tv_size.setText(length +"");
            TextView view = new TextView(this);
            view.setText(aLableName);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.yuanjiaobut));
            view.setTextColor(Color.WHITE);
            view.setPadding(10, 10, 10, 10);
            view.setTag("up");
            labelLayout.addView(view, lp);
            view.setOnClickListener(this);
        }
        for (String aLableName : lableName1) {
            TextView view = new TextView(this);
            view.setText(aLableName);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.yuanjiaohome));
            view.setTextColor(Color.parseColor("#999999"));
            view.setPadding(10, 10, 10, 10);
            view.setTag("down");
            labelLayoutall.addView(view, lp);
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        String txt = ((TextView) view).getText().toString();
        if (view.getTag().equals("up")) {
            labelLayout.removeView(view);
            labelLayout.invalidate();
            TextView view1 = new TextView(this);
            view1.setText(txt);
            view1.setBackgroundDrawable(getResources().getDrawable(R.drawable.yuanjiaohome));
            view1.setTextColor(Color.parseColor("#999999"));
            view1.setPadding(10, 10, 10, 10);
            view1.setTag("down");
            view1.setOnClickListener(this);
            labelLayoutall.addView(view1, lp);
            labelLayoutall.invalidate();
        } else if (view.getTag().equals("down")) {
            labelLayoutall.removeView(view);
            labelLayoutall.invalidate();
            TextView view1 = new TextView(this);
            view1.setText(txt);
            view1.setBackgroundDrawable(getResources().getDrawable(R.drawable.yuanjiaobut));
            view1.setTextColor(Color.WHITE);
            view1.setPadding(10, 10, 10, 10);
            view1.setTag("up");
            view1.setOnClickListener(this);
            labelLayout.addView(view1, lp);
            labelLayout.invalidate();
        }
    }

}
