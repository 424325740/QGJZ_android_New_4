package com.qigaikj.parttimejob.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;


/**
 * 作者：庞宇锋
 * 功能：通用提示框
 * 时间：2017/9/7.
 */

public class PromptDialog extends Dialog {
    private TextView left_textview;
    private TextView right_textview;
    private TextView title_textview;
    private TextView message_textview;
    private LinearLayout ll_dialog_layout;
    private View.OnClickListener onClickListener_left, onClickListener_right;

    private String msg;


    public PromptDialog(@NonNull Context context) {
        super(context);
    }

    public PromptDialog(@NonNull Context context, @StyleRes int themeResId, View.OnClickListener onClickListener_left, View.OnClickListener onClickListener_right) {
        super(context, themeResId);
        this.onClickListener_left = onClickListener_left;
        this.onClickListener_right = onClickListener_right;
    }

    public PromptDialog(@NonNull Context context, @StyleRes int themeResId, String msg, View.OnClickListener onClickListener_left, View.OnClickListener onClickListener_right) {
        super(context, themeResId);
        this.onClickListener_left = onClickListener_left;
        this.onClickListener_right = onClickListener_right;
        this.msg = msg;
    }

    protected PromptDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prompt);
        left_textview = (TextView) findViewById(R.id.tv_dlg_left_text);
        right_textview = (TextView) findViewById(R.id.tv_dlg_right_text);
        title_textview = (TextView) findViewById(R.id.tv_dlg_title_text);
        message_textview = (TextView) findViewById(R.id.tv_dlg_message_text);
        ll_dialog_layout = (LinearLayout) findViewById(R.id.ll_dialog_layout);
        left_textview.setOnClickListener(onClickListener_left);
        right_textview.setOnClickListener(onClickListener_right);
        ll_dialog_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (msg != null) {
            message_textview.setText(msg);
        }
    }

    public void dismessLeft() {
        left_textview.setVisibility(View.GONE);
    }
    public void serLeft_text(String lefttext){
        left_textview.setText(lefttext);
    }
    public void setRight_text(String righttext){
        right_textview.setText(righttext);
    }
}
