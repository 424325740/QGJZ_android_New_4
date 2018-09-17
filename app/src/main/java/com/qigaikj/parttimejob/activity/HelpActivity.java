package com.qigaikj.parttimejob.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.MemoryUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/11/011.
 * 欢迎页面
 */

public class HelpActivity extends BaseActivity {

    @BindView(R.id.iv_help_image)
    ImageView ivHelpImage;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ActivityUtils.startActivityAndFinish(HelpActivity.this, MainActivity.class);
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.ativity_help;
    }

    @Override
    protected void initDetail() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendMessage(new Message());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //创建本地目录
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionRunnable() {
            @Override
            public void allowable() {
                //创建本地目录
                MemoryUtils.createDirectory(getApplicationContext());
            }

            @Override
            public void disallowable() {
                //获取失败
                Toast.makeText(getApplicationContext(), "权限获取失败", Toast.LENGTH_LONG).show();
            }
        });
        requestPermission(Manifest.permission.RECORD_AUDIO, new PermissionRunnable() {
            @Override
            public void allowable() {

            }

            @Override
            public void disallowable() {
                //获取失败
                Toast.makeText(getApplicationContext(), "权限获取失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void initTitleView() {
        hidetitle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        /**去除启动页顶部状态栏*/
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
