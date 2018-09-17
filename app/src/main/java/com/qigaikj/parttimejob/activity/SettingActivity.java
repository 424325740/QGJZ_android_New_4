package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.New_Version;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.DataCleanManager;
import com.qigaikj.parttimejob.util.DeviceUtils;
import com.qigaikj.parttimejob.util.FileUtils;
import com.qigaikj.parttimejob.util.MemoryUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.PromptDialog;
import com.qigaikj.parttimejob.view.SwitchButton;

import java.io.File;

import io.rong.imkit.RongIM;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 设置页面
 * */

public class SettingActivity extends BaseActivity{

    private TextView tvModifyPhone;
    private SwitchButton switch_button;
    private RelativeLayout rlClear;
    private TextView tv_Clear_Num;
    private TextView tvUpdate;
    private Button btnSignOut;

    private PromptDialog promptDialog;
    private PromptDialog banbenDialog;
    private PromptDialog quitDialog;

    public boolean isUpdate = false;//默认最新版本
    public int updateurl = 0;//版本号
    private String loadurl;//下载地址
    private String message;//提示

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String ss = null;
            try {
                ss = DataCleanManager.getCacheSize(FileUtils.getCacheDir(getApplicationContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }
//            tv_Clear_Num.setText(ss);
            Log.i("asdf", "删除完成");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initDetail() {
        try {
            String ss = DataCleanManager.getCacheSize(FileUtils.getCacheDir(getApplicationContext()));
            tv_Clear_Num.setText(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvModifyPhone = (TextView) findViewById(R.id.tv_Modify_Phone);
        switch_button = (SwitchButton) findViewById(R.id.switch_button);
        rlClear = (RelativeLayout) findViewById(R.id.rlClear);
        tv_Clear_Num = (TextView) findViewById(R.id.tv_Clear_Num);
        tvUpdate = (TextView) findViewById(R.id.tv_Update);
        btnSignOut= (Button) findViewById(R.id.btn_SignOut);

        /**———— 修改手机号 —————*/
        tvModifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        /**———— 开关按钮 —————*/
        switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    showToastLong("仅WIFI播放视频开启");
                } else {
                    showToastLong("仅WIFI播放视频关闭");
                }
            }
        });
        /**———— 清理缓存 —————*/
        rlClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        /**———— 更新 —————*/
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToastLong("暂无数据接口");
//                Update();
            }
        });
        /**———— 退出 —————*/
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showQuitDialog();
            }
        });
    }

    @Override
    protected void initTitleView() {
        getIvLeftImage().setVisibility(View.VISIBLE);
        getTvTextTitle().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTvTextTitle().setText("设置");
    }

    public void DeleteCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = MemoryUtils.Path.JUXIN_VIDEO_DIRECTORY;
                for (int i = 0; i < 4; i++) {
                    switch (i) {
                        case 0://删除视频
                            FileUtils.delteFiles(new File(path));
                            break;
                        case 1://删除音频
                            FileUtils.delteFiles(new File(MemoryUtils.Path.JUXIN_AUDIO_DIRECTORY));
                            break;
                        case 2://删除图片
                            FileUtils.delteFiles(new File(MemoryUtils.Path.JUXIN_IMAGE_DIRECTORY));
                            break;
                        case 3://删除log日志
                            FileUtils.delteFiles(new File(MemoryUtils.Path.JUXIN_LOG_DIRECTORY));
                            break;

                    }
                }
                Log.i("asdf", "删除文件");
                handler.sendMessage(new Message());
            }
        }).start();
    }

    public void showDialog() {
        if (promptDialog == null) {
            promptDialog = new PromptDialog(this, R.style.DialogTheme, "是否清除缓存？", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    promptDialog.dismiss();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteCache();
                    promptDialog.dismiss();
                }
            });
            promptDialog.show();
            WindowManager m = this.getWindowManager();
            Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = promptDialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.width = (int) (d.getWidth());    //宽度设置为全屏
            promptDialog.getWindow().setAttributes(p);
        } else {
            promptDialog.show();
        }
    }

    public void showQuitDialog() {
        if (quitDialog == null) {
            quitDialog = new PromptDialog(this, R.style.DialogTheme, "退出当前账号？", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quitDialog.dismiss();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginOut();
                    quitDialog.dismiss();
                }
            });
            quitDialog.show();
            WindowManager m = this.getWindowManager();
            Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = quitDialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.width = (int) (d.getWidth());    //宽度设置为全屏
            quitDialog.getWindow().setAttributes(p);

        } else {
            quitDialog.show();
        }
    }

    /**
     * 检查更新
     */
    private void Update() {
        RetrofitManager.getInstance().createReq(BlogService.class).getNew_version().enqueue(new Callback<WrapperRspEntity<New_Version>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<New_Version>> call, Response<WrapperRspEntity<New_Version>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    int code = Integer.valueOf(response.body().getData().getVersion());
                    int mycode = DeviceUtils.getVersionCode(getApplicationContext());
                    if (code != 0 && mycode != 0) {
                        if (code > mycode) {//服务器版本号大于本地版本号
                            tvUpdate.setText("有新版本，点击更新");
                            isUpdate = true;
                            updateurl = code;
                            message = response.body().getData().getExplain();
                            loadurl = response.body().getData().getDown_url();
                        } else {
                            tvUpdate.setText("当前已是最新版本");
                            isUpdate = false;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<New_Version>> call, Throwable t) {

            }
        });
    }

    public void LoginOut() {
        String city = sharedPreferencesHelper.getString(SharedPreferencesHelper.CITY, "");
        String phone = sharedPreferencesHelper.getString(SharedPreferencesHelper.USER_ACCOUNT, "");
        String tocken = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        sharedPreferencesHelper.putString(SharedPreferencesHelper.CITY, city);
        sharedPreferencesHelper.putString(SharedPreferencesHelper.USER_ACCOUNT, phone);
        sharedPreferencesHelper.clear();
//        RongIM.getInstance().logout();//融云退出
        finish();
    }
}
