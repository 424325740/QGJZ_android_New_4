package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.util.DiffuseView;
import com.qigaikj.parttimejob.util.PipeiPopWindow;

import butterknife.BindView;
import butterknife.OnClick;

public class PipeiActivity extends BaseActivity {

    @BindView(R.id.diffuseView)
    DiffuseView diffuseView;
    @BindView(R.id.donghua)
    TextView donghua;
    @BindView(R.id.finsh)
    ImageView finsh;
    private PipeiPopWindow pipeiPopWindow;
    private TextView shaixuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shaixuan = findViewById(R.id.lin_shaixuan);
        shaixuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pipeiPopWindow.showPopupWindow(view);
            }
        });
    }

    @Override
    protected int getContentViewId() {
        pipeiPopWindow = new PipeiPopWindow(PipeiActivity.this);
        return R.layout.activity_pipei;
    }

    @Override
    protected void initDetail() {
        hidetitle();
    }

    @Override
    protected void initTitleView() {

    }

    @OnClick({R.id.finsh, R.id.donghua})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finsh:
                finish();
                break;
            case R.id.donghua:
                diffuseView.start();
                break;
        }
    }
}
