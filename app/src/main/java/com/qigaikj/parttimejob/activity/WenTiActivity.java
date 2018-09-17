package com.qigaikj.parttimejob.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.MyissueDetail;
import com.qigaikj.parttimejob.bean.ProvinceBean;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.MemoryUtils;
import com.qigaikj.parttimejob.util.PhotoUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.CameraChoiceDialog;
import com.qigaikj.parttimejob.view.PromptDialog;

import java.io.File;
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

/**
 * 开发者：庞宇锋
 * 时间： 2018/1/13/013.
 * 功能：问题反馈
 */

public class WenTiActivity extends BaseActivity {
    @BindView(R.id.et_wen_ti_phone)
    EditText etWenTiPhone;
    @BindView(R.id.tv_wen_ti_type)
    TextView tvWenTiType;
    @BindView(R.id.et_wenti_context)
    EditText etWentiContext;
    @BindView(R.id.iv_wenti_image)
    ImageView ivWentiImage;
    @BindView(R.id.ll_wenti_layout)
    LinearLayout llWentiLayout;
    private String type;
    private OptionsPickerView optionstyle;//结算方式
    private List<String> style_list;//结算方式
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wenti;
    }

    @Override
    protected void initDetail() {
        initStyle();
    }

    @Override
    protected void initTitleView() {
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTvTextTitle().setText("意见反馈");
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvRightText().setText("提交");
        getTvRightText().setVisibility(View.VISIBLE);
        getTvRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setConnent();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_wen_ti_type, R.id.ll_wenti_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wen_ti_type:
                optionstyle.show();
                break;
            case R.id.ll_wenti_layout:
                ShowPopuWindow();
                break;
        }
    }

    /**
     * 初始化结算方式选择器
     */
    private void initStyle() {
        style_list = new ArrayList<>();
        style_list.add("问题反馈");
        style_list.add("功能建议");
        style_list.add("商务合作");
        style_list.add("其他问题");
        optionstyle = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        tvWenTiType.setText(style_list.get(options1));
                        switch (style_list.get(options1)) {
                            case "问题反馈":
                                type = "1";
                                break;
                            case "功能建议":
                                type = "2";
                                break;
                            case "商务合作":
                                type = "3";
                                break;
                            case "其他问题":
                                type = "4";
                                break;
                        }
                    }
                })
                .build();
        optionstyle.setPicker(style_list);
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
                        ivWentiImage.setImageBitmap(bitmap);

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

    /**
     * 提交意见反馈
     */
    public void setConnent() {
        String content = etWentiContext.getText().toString().trim();
        String contact_way = etWenTiPhone.getText().toString().trim();
        if (contact_way == null || contact_way.equals("")) {
            showToastShort("请输入联系方式");
            return;
        }
        if (type == null || type.equals("")) {
            showToastShort("请选择意见类型");
            return;
        }
        if (content == null || content.equals("")) {
            showToastShort("请输入意见内容");
            return;
        }

        String token = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("contact_way", contact_way)
                .addFormDataPart("token", token)
                .addFormDataPart("type", type)
                .addFormDataPart("content", content);//ParamKey.TOKEN 自定义参数key常量类，即参数名
        if (Uploadfile != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), Uploadfile);
            builder.addFormDataPart("file", Uploadfile.getName(), imageBody);//imgfile 后台接收图片流的参数名
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        RetrofitManager.getInstance().createReq(BlogService.class).getSuggest(parts).enqueue(new Callback<WrapperRspEntity<MyissueDetail>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<MyissueDetail>> call, Response<WrapperRspEntity<MyissueDetail>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    showDialog();
                }
//                showToastShort(response.body().getMsg());
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<MyissueDetail>> call, Throwable t) {

            }
        });
    }

    /**
     * 提交成功后提示
     */
    private PromptDialog promptDialog;

    public void showDialog() {
        if (promptDialog == null) {
            promptDialog = new PromptDialog(this, R.style.DialogTheme, "您提出的问题已提交成功", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    promptDialog.dismiss();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    promptDialog.dismiss();
                }
            });
            promptDialog.show();
            WindowManager m = this.getWindowManager();
            Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = promptDialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.width = (int) (d.getWidth());    //宽度设置为全屏
            promptDialog.getWindow().setAttributes(p);
            promptDialog.dismessLeft();
        } else {
            promptDialog.show();
        }
    }
}
