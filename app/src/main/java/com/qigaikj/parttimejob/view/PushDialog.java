package com.qigaikj.parttimejob.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.util.LogUtils;

/**
 * Created by Administrator on 2017/12/7/007.
 * 同一个账号重复登陆
 */

public class PushDialog extends Dialog {
    private static PushDialog pushdialog;
    private static TextView tv_push_title_text;
    private static TextView tv_push_message_text;
    private static TextView tv_push_left_text;
    private static TextView tv_push_right_text;
    private static View.OnClickListener onClickListener;

    public PushDialog(@NonNull Context context) {
        super(context);
    }

    public PushDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected PushDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    /**
     * 显示弹出框
     *
     * @param context
     * @param message
     * @param iscanCancel
     */
    public static void showLoadDialog(Context context, String message, String leftext, String righttext, boolean iscanCancel, View.OnClickListener onClickListener) {
        if (pushdialog == null) {
            pushdialog = new PushDialog(context, R.style.DialogTheme);
            pushdialog.setCanceledOnTouchOutside(false);

            pushdialog.setTitle("");
            pushdialog.setContentView(R.layout.pushdialog);
            tv_push_title_text = (TextView) pushdialog.findViewById(R.id.tv_push_title_text);
            tv_push_message_text = (TextView) pushdialog.findViewById(R.id.tv_push_message_text);
            if (message != null) {
                tv_push_message_text.setText(message);
            }

            tv_push_left_text = (TextView) pushdialog.findViewById(R.id.tv_push_left_text);
            if (leftext != null) {
                tv_push_left_text.setText(leftext);
            }
            tv_push_left_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissLoadDialog();
                }
            });

            tv_push_right_text = (TextView) pushdialog.findViewById(R.id.tv_push_right_text);

            tv_push_right_text.setOnClickListener(onClickListener);
            if (righttext != null) {
                tv_push_right_text.setText(righttext);
            }
            //按返回键响应是否取消等待框的显示
            pushdialog.setCancelable(iscanCancel);
            pushdialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            pushdialog.show();
            LogUtils.i("设备登陆创建弹出框");
        } else {
            pushdialog.show();
            LogUtils.i("设备登陆显示弹出框");

        }

    }

    /**
     * 设置错误提示
     *
     * @param erray
     */
    public static void setErray(String erray) {


    }

    /**
     * 隐藏弹出框
     */
    public static void dismissLoadDialog() {
        if (pushdialog != null) {
            pushdialog.dismiss();
        }
    }
}
