package com.qigaikj.parttimejob.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.util.AndroidWorkaround;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.LoadDlialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/3/003.
 * 所有父类的模板
 */

public abstract class BaseActivity extends AppCompatActivity {
    @BindView(R.id.iv_left_image)
    ImageView ivLeftImage;
    @BindView(R.id.tv_left_text)
    TextView tvLeftText;
    @BindView(R.id.tv_text_title)
    TextView tvTextTitle;
    @BindView(R.id.iv_right_image)
    ImageView ivRightImage;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.rl_layout)
    RelativeLayout rlLayout;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    View vLins;
    protected Bundle savedInstanceState;
    /**
     * 上下文对象
     */
    protected Context mContext;
    /**
     * SharedPreferences
     */
    protected SharedPreferencesHelper sharedPreferencesHelper;

    /**
     * 网络正在加载
     */
    protected LoadDlialog loadDlialog;

    public BaseAplcation application;

    protected int mColorId = R.color.transparent;//状态栏的默认背景色
    private SystemBarTintManager tintManager;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setFullscreen();
        setContentView(R.layout.base_activity);
        this.savedInstanceState = savedInstanceState;
        ivLeftImage = (ImageView) findViewById(R.id.iv_left_image);
        tvLeftText = (TextView) findViewById(R.id.tv_left_text);
        tvTextTitle = (TextView) findViewById(R.id.tv_text_title);
        ivRightImage = (ImageView) findViewById(R.id.iv_right_image);
        tvRightText = (TextView) findViewById(R.id.tv_right_text);
        rlLayout = (RelativeLayout) findViewById(R.id.rl_layout);
        vLins = findViewById(R.id.v_lins);
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
////            getActivity().getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        if (getContentViewId() == 0) {
            throw new RuntimeException("未设置界面布局");
        } else {
            alabSetContentView(getContentViewId());
            //添加注解依赖包
            ButterKnife.bind(this);
            mContext = this;
            sharedPreferencesHelper = SharedPreferencesHelper.getInstance(mContext);
            //设置头部状态栏
            initTitleView();
            //初始化
            initDetail();
//            PushAgent.getInstance(mContext).onAppStart();
            /*if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
                AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
            }*/

        }

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            return;
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }

    /**
     * 设置主内容区域的layoutRes
     *
     * @param layoutResId
     */
    private void alabSetContentView(int layoutResId) {
        LinearLayout llContent = (LinearLayout) findViewById(R.id.ll_layout);
        LayoutInflater inflater = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(layoutResId, (ViewGroup) findViewById(R.id.ll_layout), false);

        llContent.addView(v);
    }

    /**
     * 设置内容区域布局
     *
     * @return 内容区域布局的ID
     */
    protected abstract int getContentViewId();

    /**
     * 获取数据，初始化
     */
    protected abstract void initDetail();

    /**
     * 设置标题栏
     */
    protected abstract void initTitleView();

    /**
     * 显示左侧ImageView并设置图片
     */
    public void setIvLeftImage(int imageid) {
        ivLeftImage.setVisibility(View.VISIBLE);
        //默认值为0时，显示默认图片
        if (imageid == 0) {
            ivLeftImage.setImageResource(imageid);
        }

    }

    /**
     * 显示左侧TextView并设置文字
     */
    public void setTvLeftText(String leftText) {
        tvLeftText.setVisibility(View.VISIBLE);
        if (leftText != null && leftText != "") {
            tvLeftText.setText(leftText);
        }
    }

    /**
     * 显示标题TextView并设置文字
     */
    public void setTvTextTitle(String textTitle) {
        tvTextTitle.setVisibility(View.VISIBLE);
        if (textTitle != null && textTitle != "") {
            tvTextTitle.setText(textTitle);
        }
    }

    /**
     * 显示右侧ImageView并设置图片
     */
    public void setIvRightImage(int imageid) {
        ivRightImage.setVisibility(View.VISIBLE);
        //默认值为0时，显示默认图片
        if (imageid != 0) {
            ivRightImage.setImageResource(imageid);
        }
    }

    /**
     * 显示右侧TextView并设置文字
     */
    public void setTvRightText(String rightText) {
        tvRightText.setVisibility(View.VISIBLE);
        if (rightText != null && rightText != "") {
            tvRightText.setText(rightText);
        }
    }

    /**
     * 获取左侧ImageView
     */
    public ImageView getIvLeftImage() {
        return ivLeftImage;
    }

    /**
     * 获取右侧ImageView
     */
    public ImageView getIvRightImage() {
        return ivRightImage;
    }

    /**
     * 获取左侧TextView
     */
    public TextView getTvLeftText() {
        return tvLeftText;
    }

    /**
     * 获取右侧TextView
     */
    public TextView getTvRightText() {
        return tvRightText;
    }

    /**
     * 获取标题TextView
     */
    public TextView getTvTextTitle() {
        return tvTextTitle;
    }

    /**
     * 获取整个标题栏
     */
    public RelativeLayout getRlLayout() {
        return rlLayout;
    }

    /**
     * 6.0动态请求权限
     */
    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    public PermissionRunnable permissionRunnable;

    /**
     * 隐藏状态栏
     */
    public void hidetitle() {
        rlLayout.setVisibility(View.GONE);
        vLins.setVisibility(View.GONE);
    }

    /**
     * 打印吐司（短时间）
     *
     * @param msg 要吐的内容
     */
    public void showToastShort(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 打印吐司（长时间）
     *
     * @param msg 要吐的内容
     */
    public void showToastLong(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 请求权限
     *
     * @param permission         请求的权限
     * @param permissionRunnable 权限请求结果回调
     */
    protected void requestPermission(String permission, PermissionRunnable permissionRunnable) {
        if (permissionRunnable != null) {
            this.permissionRunnable = permissionRunnable;
        }
        //版本判断
        if (Build.VERSION.SDK_INT >= 23) {
            //减少是否拥有权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {

                //弹出对话框接收权限(此处可同时申请多个权限)
                ActivityCompat.requestPermissions(BaseActivity.this, new String[]{permission}, REQUEST_CODE_ASK_CALL_PHONE);

                return;
            } else {
                permissionRunnable.allowable();

            }
        } else {
            permissionRunnable.allowable();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionRunnable.allowable();
                } else {
                    // 获取失败
                    permissionRunnable.disallowable();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 回调6.0申请权限结果
     */

    public interface PermissionRunnable {
        //获取成功
        public void allowable();

        //获取失败
        public void disallowable();
    }

    /**
     * 设置状态栏颜色
     */
    protected void setStatusBarColor(int color) {

        final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
        final String TAG_MARGIN_ADDED = "marginAdded";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

//            getWindow().setStatusBarColor(color);
            Window window = getWindow();
            // 取消状态栏透明
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 添加 Flag 把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 设置状态栏颜色
            window.setStatusBarColor(color);
            // 设置系统状态栏处于可见状态
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            // 让 view 不根据系统窗口来调整自己的布局
            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false);
                ViewCompat.requestApplyInsets(mChildView);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            Window window = getWindow();
            // 设置 Window 为全透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            // 获取父布局
            View mContentChild = mContentView.getChildAt(0);

            // 如果已经存在状态栏则移除，防止重复添加
            ViewGroup mDecorView = (ViewGroup) window.getDecorView();
            View fakeView = mDecorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
            if (fakeView != null) mDecorView.removeView(fakeView);

            // 添加一个View 来作为状态栏的填充
            View mStatusBarView = new View(this);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
            layoutParams.gravity = Gravity.TOP;
            mStatusBarView.setLayoutParams(layoutParams);
            mStatusBarView.setBackgroundColor(color);
            mStatusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);
            mDecorView.addView(mStatusBarView);

            // 设置子控件到状态栏的间距
            if (mContentChild != null && !TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
                lp.topMargin += getStatusBarHeight();
                mContentChild.setLayoutParams(lp);
                mContentChild.setTag(TAG_MARGIN_ADDED);
            }

            // 不预留系统栏位置
            if (mContentChild != null) ViewCompat.setFitsSystemWindows(mContentChild, false);
            // 如果使用了 ActionBar 则需要再将布局与状态栏的高度跳高一个 ActionBar 的高度
            int action_bar_id = getResources().getIdentifier("action_bar", "id", getPackageName());
            View view = findViewById(action_bar_id);
            if (view != null) {
                TypedValue typedValue = new TypedValue();
                if (getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
                    int actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
                    getWindow().findViewById(Window.ID_ANDROID_CONTENT).setPadding(0, actionBarHeight, 0, 0);
                }
            }
        }
    }

    /**
     * 获取状态栏高度
     */
    protected int getStatusBarHeight() {
        int result = 0;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    protected void setFullscreen() {
    }

    //当程序崩溃时，解决fragment出现重叠问题
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }
}
