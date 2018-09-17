package com.qigaikj.parttimejob.manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.qigaikj.parttimejob.util.DeviceUtils;


/**
 * Created by zs on 2016/7/7.
 */
public class UpdateManager {

    private Context mContext;

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate(final boolean isToast, int nVersion_code, String loadurl, String message) {
        /**
         * 在这里请求后台接口，获取更新的内容和最新的版本号
         */
        // 版本的更新信息
//        String version_info = "更新内容\n" + "    1. 车位分享异常处理\n" + "    2. 发布车位折扣格式统一\n" + "    ";
        int mVersion_code = DeviceUtils.getVersionCode(mContext);// 当前的版本号
//        int nVersion_code = 2;
        if (mVersion_code < nVersion_code) {
            // 显示提示对话
            showNoticeDialog(message,loadurl);
        } else {
            if (isToast) {
                Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 显示更新对话框
     *
     * @param version_info
     */
    private void showNoticeDialog(String version_info, final String loadurl) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("更新提示");
        builder.setMessage(version_info);
        // 更新
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                Intent intent=new Intent(mContext, DownLoadService.class);
//                intent.putExtra("LoadUrl",loadurl);
//                mContext.startService(intent);
            }
        });
        // 稍后更新
        builder.setNegativeButton("以后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
}
