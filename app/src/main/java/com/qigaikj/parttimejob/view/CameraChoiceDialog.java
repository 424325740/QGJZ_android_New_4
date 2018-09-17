package com.qigaikj.parttimejob.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.qigaikj.parttimejob.R;

/**
 * 弹出相机选择框
 * Created by Android on 2016/4/29.
 */
public class CameraChoiceDialog extends Dialog implements View.OnClickListener {
    private Context context;
    /**
     * 用于回掉选择状态的接口
     */

    /**
     * 相机
     */
    private Button btn_take_photo;
    public View.OnClickListener TakeonClickListener;

    /**
     * 本地
     */
    private Button btn_pick_photo;
    public View.OnClickListener PikeonClickListener;
    /**
     * 取消
     */
    private Button btn_cancel;
    private RelativeLayout rl_dismiss;

    public CameraChoiceDialog(Context context, View.OnClickListener TakeonClickListener, View.OnClickListener PikeonClickListener) {
        super(context);
        this.context = context;
        this.TakeonClickListener = TakeonClickListener;
        this.PikeonClickListener = PikeonClickListener;

    }

    public CameraChoiceDialog(Context context, int themeResId, View.OnClickListener TakeonClickListener, View.OnClickListener PikeonClickListener) {
        super(context, themeResId);
        this.context = context;
        this.TakeonClickListener = TakeonClickListener;
        this.PikeonClickListener = PikeonClickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        btn_take_photo = (Button) findViewById(R.id.btn_take_photo);
        btn_take_photo.setOnClickListener(TakeonClickListener);
        btn_pick_photo = (Button) findViewById(R.id.btn_pick_photo);
        btn_pick_photo.setOnClickListener(PikeonClickListener);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        rl_dismiss = (RelativeLayout) findViewById(R.id.rl_dismiss);
        rl_dismiss.setOnClickListener(this);
    }


    /**
     * 按钮点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.rl_dismiss:
                dismiss();
                break;
        }

    }
}
