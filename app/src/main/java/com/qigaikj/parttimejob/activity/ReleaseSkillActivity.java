package com.qigaikj.parttimejob.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.ClassifyForAll;
import com.qigaikj.parttimejob.bean.MyissueDetail;
import com.qigaikj.parttimejob.bean.SkillData;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.MemoryUtils;
import com.qigaikj.parttimejob.util.PhotoUtils;
import com.qigaikj.parttimejob.util.PopWindowdibu;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.CameraChoiceDialog;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseSkillActivity extends BaseActivity {

    @BindView(R.id.llSkillType)//技能类型
            LinearLayout llSkillType;
    @BindView(R.id.tvSkillType)
    TextView tvSkillType;
    @BindView(R.id.llSkillName)//技能名称
            LinearLayout llSkillName;
    @BindView(R.id.tvSkillName)
    TextView tvSkillName;
    @BindView(R.id.etSkillRequiremen)//技能要求
            EditText etSkillRequirement;
    @BindView(R.id.ivImage)//添加图片
            ImageView ivImage;
    @BindView(R.id.tvLineUp)//线上服务
            ImageView tvLineUp;
    @BindView(R.id.etMoney) // 价钱
            EditText etMoney;
    @BindView(R.id.tvTime) // 时间
            TextView tvTime;
    @BindView(R.id.tvLineDown)//线下服务
            ImageView tvLineDown;
    @BindView(R.id.cyj_1)
    LinearLayout cyj1;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.cyj_2)
    LinearLayout cyj2;
    @BindView(R.id.text_3)
    TextView text3;
    @BindView(R.id.cyj_3)
    LinearLayout cyj3;
    @BindView(R.id.text_4)
    TextView text4;
    @BindView(R.id.cyj_4)
    LinearLayout cyj4;
    @BindView(R.id.text_5)
    TextView text5;
    @BindView(R.id.cyj_5)
    LinearLayout cyj5;
    @BindView(R.id.text_6)
    TextView text6;
    @BindView(R.id.cyj_6)
    LinearLayout cyj6;
    @BindView(R.id.text_7)
    TextView text7;
    @BindView(R.id.cyj_7)
    LinearLayout cyj7;
    @BindView(R.id.text_8)
    TextView text8;
    @BindView(R.id.cyj_8)
    LinearLayout cyj8;
    @BindView(R.id.linvisible)
    LinearLayout linbisible;
    View view;
    @BindView(R.id.llMoney)
    LinearLayout llMoney;
    @BindView(R.id.llMoney_xia)
    LinearLayout llMoneyXia;
    @BindView(R.id.tvTime_xia)
    TextView tvTimeXia;
    @BindView(R.id.llTime_xia)
    LinearLayout llTimeXia;
    @BindView(R.id.linvisible_xia)
    LinearLayout linvisibleXia;
    private int option;
    private String Type = "0";// 0、技能类型   1、技能名称   2、时间
    private OptionsPickerView optionstyle;
    private List<ClassifyForAll> style_list_All = new ArrayList<>();//技能类型
    private List<String> style_list = new ArrayList<>();//技能类型
    private List<String> list = new ArrayList<>();
    private List<String> skill_name_list = new ArrayList<>();//技能名称
    private List<String> Time_List = new ArrayList<>();//时间
    private String isSelect = "1";
    private String sincerity_gold = "0元";
    private Drawable drawableLeft;
    List<Boolean> booleans = new ArrayList<>();
    boolean flag = false;
    private boolean isChecked = false;
    private TextView textView;
    private String zhuangtai;
    private SkillData bean;
    private String kid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getContentViewId() {
        for (int i = 0; i < 8; i++) {
            booleans.add(false);
        }
        Intent intent = getIntent();
        zhuangtai = intent.getStringExtra("zhuangtai");
        kid = intent.getStringExtra("kid");
        bean = (SkillData) intent.getSerializableExtra("bean");

        return R.layout.activity_release_skill;
    }

    @Override
    protected void initDetail() {
        if (bean!=null){
            tvSkillName.setText(bean.name);
            etSkillRequirement.setText(bean.introduce);
            etMoney.setText(bean.price);
            tvTime.setText(bean.unit);
        }
        ObtionClassifyDataNetWork();

    }

    @Override
    protected void initTitleView() {
        if (zhuangtai!=null){
            if (zhuangtai.equals("1")) {
                getTvTextTitle().setVisibility(View.VISIBLE);
                getTvTextTitle().setText("编辑技能");
                getTvRightText().setVisibility(View.VISIBLE);
                getTvRightText().setText("确定");
                getIvLeftImage().setVisibility(View.VISIBLE);
                getTvRightText().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getxiugaiData();
                    }
                });
                getIvLeftImage().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
            return;
        }
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("发布技能");
        getTvRightText().setVisibility(View.VISIBLE);
        getTvRightText().setText("发布");
        getTvRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void flag(int index) {
        for (int i = 0; i < booleans.size(); i++) {
            if (index == i) {
                booleans.set(i, !booleans.get(i));
            } else {
                booleans.set(i, false);
            }
        }
        checked();
    }
    public void getxiugaiData(){
        String token = SharedPreferencesHelper.getInstance(ReleaseSkillActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        String lng = SharedPreferencesHelper.getInstance(ReleaseSkillActivity.this).getString(SharedPreferencesHelper.LONG, "");
        String lat = SharedPreferencesHelper.getInstance(ReleaseSkillActivity.this).getString(SharedPreferencesHelper.LAT, "");
        String type = tvSkillType.getText().toString();
        String name = tvSkillName.getText().toString();
        String s = textView.getText().toString();
        String introduce = etSkillRequirement.getText().toString();
        String serve = isSelect;
        String price = etMoney.getText().toString();
        String unit = tvTime.getText().toString();
        if (TextUtils.isEmpty(type)) {
            showToastShort("请选择技能类型");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            showToastShort("请选择技能名称");
            return;
        }
        if (TextUtils.isEmpty(introduce)) {
            showToastShort("请简单介绍一下自己");
            return;
        }
        if (TextUtils.isEmpty(type)) {
            showToastShort("请选择技能类型");
            return;
        }
        if (TextUtils.isEmpty(price)) {
            showToastShort("请输入金额");
            return;
        }
        if (TextUtils.isEmpty(unit)) {
            showToastShort("请选择时间单位");
            return;
        }
        Xgjineng(token, type, s, introduce, serve, price, unit, sincerity_gold, lng, lat);
        ActivityUtils.startActivity(ReleaseSkillActivity.this, PipeiActivity.class);
    }
    /**
     * 判断数据是否全部选择
     */
    public void getData(){
        String token = SharedPreferencesHelper.getInstance(ReleaseSkillActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        String lng = SharedPreferencesHelper.getInstance(ReleaseSkillActivity.this).getString(SharedPreferencesHelper.LONG, "");
        String lat = SharedPreferencesHelper.getInstance(ReleaseSkillActivity.this).getString(SharedPreferencesHelper.LAT, "");
        String type = tvSkillType.getText().toString();
        String name = tvSkillName.getText().toString();
        String s = textView.getText().toString();
        String introduce = etSkillRequirement.getText().toString();
        String serve = isSelect;
        String price = etMoney.getText().toString();
        String unit = tvTime.getText().toString();
        if (TextUtils.isEmpty(type)) {
            showToastShort("请选择技能类型");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            showToastShort("请选择技能名称");
            return;
        }
        if (TextUtils.isEmpty(introduce)) {
            showToastShort("请简单介绍一下自己");
            return;
        }
        if (TextUtils.isEmpty(type)) {
            showToastShort("请选择技能类型");
            return;
        }
        if (TextUtils.isEmpty(price)) {
            showToastShort("请输入金额");
            return;
        }
        if (TextUtils.isEmpty(unit)) {
            showToastShort("请选择时间单位");
            return;
        }
        PostSkill(token, type, s, introduce, serve, price, unit, sincerity_gold, lng, lat);
        ActivityUtils.startActivity(ReleaseSkillActivity.this, PipeiActivity.class);
    }
    public void checked() {
        Drawable checked = getResources().getDrawable(R.drawable.yuanjiaobut);
        Drawable unchecked = getResources().getDrawable(R.drawable.yuanjiaohome);
        int textChecked = getResources().getColor(R.color.background);
        int textUnChecked = getResources().getColor(R.color.mytext_2);
        cyj1.setBackground(booleans.get(0) ? checked : unchecked);
        text1.setTextColor(booleans.get(0) ? textChecked : textUnChecked);
        cyj2.setBackground(booleans.get(1) ? checked : unchecked);
        text2.setTextColor(booleans.get(1) ? textChecked : textUnChecked);
        cyj3.setBackground(booleans.get(2) ? checked : unchecked);
        text3.setTextColor(booleans.get(2) ? textChecked : textUnChecked);
        cyj4.setBackground(booleans.get(3) ? checked : unchecked);
        text4.setTextColor(booleans.get(3) ? textChecked : textUnChecked);
        cyj5.setBackground(booleans.get(4) ? checked : unchecked);
        text5.setTextColor(booleans.get(4) ? textChecked : textUnChecked);
        cyj6.setBackground(booleans.get(5) ? checked : unchecked);
        text6.setTextColor(booleans.get(5) ? textChecked : textUnChecked);
        cyj7.setBackground(booleans.get(6) ? checked : unchecked);
        text7.setTextColor(booleans.get(6) ? textChecked : textUnChecked);
        cyj8.setBackground(booleans.get(7) ? checked : unchecked);
        text8.setTextColor(booleans.get(7) ? textChecked : textUnChecked);
    }

    @OnClick({R.id.llSkillType, R.id.llSkillName, R.id.llSkillRequirement, R.id.ivImage, R.id.llLineUp, R.id.llMoney, R.id.llTime, R.id.llLineDown, R.id.cyj_1, R.id.cyj_2, R.id.cyj_3, R.id.cyj_4, R.id.cyj_5, R.id.cyj_6, R.id.cyj_7, R.id.cyj_8,
            R.id.llTime_xia})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llSkillType:
                Type = "0";
                initStyle();
                optionstyle.show();
                /**如果当用户更换技能类型，则清空技能名称*/
                if (!TextUtils.isEmpty(tvSkillName.getText().toString())) {
                    tvSkillName.setText("");
                }
                break;
            case R.id.llSkillName:
                if (!TextUtils.isEmpty(tvSkillType.getText().toString())) {
                    Type = "1";
                    initStyle();
                    optionstyle.show();
                } else {
                    showToastShort("请选择技能类型");
                }
                break;
            case R.id.llSkillRequirement:
                break;
            case R.id.ivImage:
                ShowPopuWindow();
                break;
            case R.id.llLineUp:
                if (isChecked == false) {
                    tvLineUp.setImageResource(R.mipmap.btn_select_pre);
                    tvLineDown.setImageResource(R.mipmap.btn_select_nor);
                    linbisible.setVisibility(View.VISIBLE);
                    linvisibleXia.setVisibility(View.GONE);
                } else {
                    isChecked = false;
                }
                isSelect = "1";
                break;
            case R.id.llMoney:
                break;
            case R.id.llTime:
                PopWindowdibu popWindowdibu = new PopWindowdibu(ReleaseSkillActivity.this);
                popWindowdibu.showPopupWindow(view, tvTime);
                break;
            case R.id.llTime_xia:
                PopWindowdibu popWindowdibus = new PopWindowdibu(ReleaseSkillActivity.this);
                popWindowdibus.showPopupWindow(view, tvTimeXia);
                break;
            case R.id.llLineDown:
                if (flag == false) {
                    tvLineUp.setImageResource(R.mipmap.btn_select_nor);
                    tvLineDown.setImageResource(R.mipmap.btn_select_pre);
                    linbisible.setVisibility(View.GONE);
                    linvisibleXia.setVisibility(View.VISIBLE);
//                    flag = true;
                } else {
                    flag = false;
                }
                isSelect = "2";
                break;
            case R.id.cyj_1:
                flag(0);
                break;
            case R.id.cyj_2:
                flag(1);
                break;
            case R.id.cyj_3:
                flag(2);
                break;
            case R.id.cyj_4:
                flag(3);
                break;
            case R.id.cyj_5:
                flag(4);
                break;
            case R.id.cyj_6:
                flag(5);
                break;
            case R.id.cyj_7:
                flag(6);
                break;
            case R.id.cyj_8:
                flag(7);
                break;
        }
    }
    /**
     * 编辑技能
     */
    public void Xgjineng(String token, String type, String name, String introduce, String serve, String price, String unit, String sincerity_gold, String lng, String lat){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("token", token)
                .addFormDataPart("type", type)
                .addFormDataPart("name", name)
                .addFormDataPart("introduce", introduce)
                .addFormDataPart("serve", serve)
                .addFormDataPart("price", price)
                .addFormDataPart("unit", unit)
                .addFormDataPart("sincerity_gold", sincerity_gold)
                .addFormDataPart("lng", lng)
                .addFormDataPart("lat", lat)
                .addFormDataPart("kid",kid);//ParamKey.TOKEN 自定义参数key常量类，即参数名
        if (Uploadfile != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), Uploadfile);
            builder.addFormDataPart("file", Uploadfile.getName(), imageBody);//imgfile 后台接收图片流的参数名
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        RetrofitManager.getInstance().createReq(BlogService.class).xiugai(parts).enqueue(new Callback<WrapperRspEntity<MyissueDetail>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<MyissueDetail>> call, Response<WrapperRspEntity<MyissueDetail>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    showToastShort(response.body().getMsg());
                    ReleaseSkillActivity.this.finish();
                } else {
                    showToastShort(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<MyissueDetail>> call, Throwable t) {

            }
        });
    }
    /**
     * 发布接口数据
     */
    public void PostSkill(String token, String type, String name, String introduce, String serve, String price, String unit, String sincerity_gold, String lng, String lat) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("token", token)
                .addFormDataPart("type", type)
                .addFormDataPart("name", name)
                .addFormDataPart("introduce", introduce)
                .addFormDataPart("serve", serve)
                .addFormDataPart("price", price)
                .addFormDataPart("unit", unit)
                .addFormDataPart("sincerity_gold", sincerity_gold)
                .addFormDataPart("lng", lng)
                .addFormDataPart("lat", lat);//ParamKey.TOKEN 自定义参数key常量类，即参数名
        if (Uploadfile != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), Uploadfile);
            builder.addFormDataPart("file", Uploadfile.getName(), imageBody);//imgfile 后台接收图片流的参数名
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        RetrofitManager.getInstance().createReq(BlogService.class).postSkill(parts).enqueue(new Callback<WrapperRspEntity<MyissueDetail>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<MyissueDetail>> call, Response<WrapperRspEntity<MyissueDetail>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    showToastShort(response.body().getMsg());
                    ReleaseSkillActivity.this.finish();
                } else {
                    showToastShort(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<MyissueDetail>> call, Throwable t) {

            }
        });
    }

    /**
     * 初始化结算方式选择器
     */
    private void initStyle() {
        optionstyle = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                switch (Type) {
                    case "0":
                        tvSkillType.setText(style_list.get(options1));
                        option = options1;
                        break;
                    case "1":
                        tvSkillName.setText(skill_name_list.get(options1));
                        textView = new TextView(ReleaseSkillActivity.this);
                        textView.setText(list.get(options1));
                        break;
                    case "2":
                        tvTime.setText(Time_List.get(options1));
                        break;
                }
            }
        }).build();

        switch (Type) {
            case "0":
                if (style_list != null && style_list.size() > 0) {
                    style_list.clear();
                }
                /**技能类型*/
                for (int i = 0; i < style_list_All.size(); i++) {
                    style_list.add(style_list_All.get(i).getYiji());
                }
                optionstyle.setPicker(style_list);
                break;
            case "1":
                /**技能名称*/
                for (int i = 0; i < style_list_All.get(option).getErji().size(); i++) {
                    skill_name_list.add(style_list_All.get(option).getErji().get(i).tname);
                    list.add(style_list_All.get(option).getErji().get(i).tid);
                }
                optionstyle.setPicker(skill_name_list);
                break;
            case "2":
                if (Time_List != null && Time_List.size() > 0) {
                    Time_List.clear();
                }
                Time_List.add("天");
                Time_List.add("时");
                optionstyle.setPicker(Time_List);
                break;
        }
    }

    /**
     * 二级分类
     */
    public void ObtionClassifyDataNetWork() {
        RetrofitManager.getInstance().createReq(BlogService.class).getClassifyForAll().enqueue(new Callback<WrapperRspEntity<List<ClassifyForAll>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<ClassifyForAll>>> call, Response<WrapperRspEntity<List<ClassifyForAll>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (response.body().getData().size() > 0) {
                        style_list_All.addAll(response.body().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<List<ClassifyForAll>>> call, Throwable t) {

            }
        });
    }

    private CameraChoiceDialog jxCameraChoiceDialog;//弹出框

    /**
     * 点击显示弹出框
     */
    private void ShowPopuWindow() {
        if (jxCameraChoiceDialog == null) {
            jxCameraChoiceDialog = new CameraChoiceDialog(this, R.style.DialogTheme, new View.OnClickListener() {
                @Override
                public void onClick(View v) {//拍照
                    autoObtainCameraPermission();
                    jxCameraChoiceDialog.dismiss();
                }
            }, new View.OnClickListener() {//本地
                @Override
                public void onClick(View v) {
                    autoObtainStoragePermission();
                    jxCameraChoiceDialog.dismiss();
                }
            });
            jxCameraChoiceDialog.show();
            WindowManager m = this.getWindowManager();
            Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = jxCameraChoiceDialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.width = (int) (d.getWidth());    //宽度设置为全屏
            jxCameraChoiceDialog.getWindow().setAttributes(p);
        } else {
            jxCameraChoiceDialog.show();
        }
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(getApplicationContext(), "您已拒绝过一次", Toast.LENGTH_SHORT);
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                File file1 = new File(fileUri, getPhotoFileName());
                imageUri = Uri.fromFile(file1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.qigaikj.parttimejob.FileProvider", file1);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                Toast.makeText(getApplicationContext(), "设备没有SD卡", Toast.LENGTH_SHORT);
            }
        }
    }

    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        File file = new File(fileUri, getPhotoFileName());
                        imageUri = Uri.fromFile(file);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.qigaikj.parttimejob.FileProvider", file);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        Toast.makeText(getApplicationContext(), "设备没有SD卡", Toast.LENGTH_SHORT);
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "请允许打开相机！！", Toast.LENGTH_SHORT);
                }
                break;


            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {
                    Toast.makeText(getApplicationContext(), "请允许打操作SDCard！！", Toast.LENGTH_SHORT);
                }
                break;
        }
    }

    public static final int CODE_GALLERY_REQUEST = 0xa0;
    public static final int CODE_CAMERA_REQUEST = 0xa1;
    public static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(MemoryUtils.Path.JUXIN_IMAGE_DIRECTORY);
    private File fileCropUri = new File(MemoryUtils.Path.JUXIN_IMAGE_DIRECTORY);
    private Uri imageUri;
    private Uri cropImageUri;
    private static final int output_X = 480;
    private static final int output_Y = 480;
    private File Uploadfile;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    LogUtils.i("拍照回调");
                    File file1 = new File(fileCropUri, getPhotoFileName());
                    Uploadfile = file1;
                    cropImageUri = Uri.fromFile(file1);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    LogUtils.i("相册回调");
                    if (hasSdcard()) {
                        File file = new File(fileCropUri, getPhotoFileName());
                        Uploadfile = file;
                        cropImageUri = Uri.fromFile(file);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(getApplicationContext(), data.getData()));
                        File f = new File(newUri.getPath());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            newUri = FileProvider.getUriForFile(getContext(), "com.qigaikj.parttimejob.FileProvider", f);
                            newUri = FileProvider.getUriForFile(getApplicationContext(), "com.qigaikj.parttimejob.FileProvider", f);
                        }
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else {
                        Toast.makeText(getApplicationContext(), "设备没有SD卡！", Toast.LENGTH_SHORT);
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Log.i("asdf", "--------------------------" + cropImageUri);
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, getApplicationContext());
                    if (bitmap != null) {
                        ivImage.setImageBitmap(bitmap);
                    }
                    break;
            }
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    // 使用系统当前日期加以调整作为照片的名称
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }


}
