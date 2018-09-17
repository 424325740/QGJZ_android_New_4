package com.qigaikj.parttimejob.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.util.LogUtils;

/**
 * Created by Administrator on 2017/11/22/022.
 * 网络正在加载弹出框
 */

public class LoadDlialog extends Dialog {
    private static LoadDlialog loadDlialog;
    private static TextView tverray;
    private static ImageView iv_gif_load;
    private static AnimationDrawable frameAnimation;
    private static Animation operatingAnim;
    private static ProgressBar progressBar;

    public LoadDlialog(@NonNull Context context) {
        super(context);
    }

    public LoadDlialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected LoadDlialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    /**
     * 显示弹出框
     *
     * @param context
     * @param message
     * @param iscanCancel
     */
    public static void showLoadDialog(Context context, String message, boolean iscanCancel) {
        if (loadDlialog == null) {
            loadDlialog = new LoadDlialog(context, R.style.DialogTheme);
            loadDlialog.setCanceledOnTouchOutside(false);

            loadDlialog.setTitle("");
            loadDlialog.setContentView(R.layout.dialog_load);
            tverray = (TextView) loadDlialog.findViewById(R.id.erray);
            iv_gif_load = (ImageView) loadDlialog.findViewById(R.id.iv_gif_load);
            //给图片设置加载动画
//            iv_gif_load.setBackgroundResource(R.drawable.load_animation);
//            frameAnimation = (AnimationDrawable) iv_gif_load.getBackground();
//            //启动动画
//            frameAnimation.start();
//            progressBar = (ProgressBar) loadDlialog.findViewById(R.id.pb_jiazai);
//            operatingAnim = AnimationUtils.loadAnimation(context, R.anim.tip);
//            LinearInterpolator lin = new LinearInterpolator();
//            operatingAnim.setInterpolator(lin);
//            iv_gif_load.setAnimation(operatingAnim);
            //按返回键响应是否取消等待框的显示
            loadDlialog.setCancelable(iscanCancel);
            LogUtils.i("创建了一个dialog--------------------------------------》");
            loadDlialog.show();
        } else {
            LogUtils.i("复用了一个dialog--------------------------------------》");
//            frameAnimation.start();
//            if (operatingAnim != null) {
//                iv_gif_load.setAnimation(operatingAnim);
//                operatingAnim.start();
//            }
            loadDlialog.show();
        }

    }

    /**
     * 设置错误提示
     *
     * @param erray
     */
    public static void setErray(String erray) {
        if (tverray != null) {
            tverray.setVisibility(View.VISIBLE);
            tverray.setText(erray);
        }

    }

    /**
     * 隐藏弹出框
     */
    public static void dismissLoadDialog() {
        if (loadDlialog != null) {
            loadDlialog.dismiss();
            LogUtils.i("dialog消失");
//            frameAnimation.stop();
            loadDlialog=null;
        }
    }

    /**
     * 将dialog设为空
     */
    public static void onDestroy() {
        loadDlialog = null;
    }
}
