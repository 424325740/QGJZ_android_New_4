package com.qigaikj.parttimejob.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.MyissueDetail;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.MemoryUtils;
import com.qigaikj.parttimejob.util.PhotoUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.CameraChoiceDialog;

import java.io.File;
import java.text.SimpleDateFormat;
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
 * 发布动态
 * */

public class ReleaseDynamicActivity extends BaseActivity {

    @BindView(R.id.etContext)//动态内容
            EditText etContext;
    @BindView(R.id.tvContextNum)//动态内容
            TextView tvContextNum;
    @BindView(R.id.ivImage)//添加图片
            ImageView ivImage;

    private TextWatcher mTextWatcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_dynamic;
    }

    @Override
    protected void initDetail() {
        mTextWatcher = new TextWatcher() {
            private CharSequence temp;
            private int editStart ;
            private int editEnd ;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                temp = s;
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
                //          mTextView.setText(s);//将输入的内容实时显示
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                editStart = etContext.getSelectionStart();
                editEnd = etContext.getSelectionEnd();
                tvContextNum.setText(temp.length() + "/100字");
                if (temp.length() > 100) {
                    showToastShort("你输入的字数已经超过了限制！");
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    etContext.setText(s);
                    etContext.setSelection(tempSelection);
                }
            }
        };
        etContext.addTextChangedListener(mTextWatcher);
    }

    @Override
    protected void initTitleView() {
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("发布动态");
        getTvRightText().setVisibility(View.VISIBLE);
        getTvRightText().setText("发布");
        getTvRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
                String content = etContext.getText().toString();

                if (TextUtils.isEmpty(content)) {
                    showToastShort("动态内容不能为null");
                    return;
                }
                PostDynamic(token, content);
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

    public void PostDynamic(String token, String content) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("token", token)
                .addFormDataPart("content", content);//ParamKey.TOKEN 自定义参数key常量类，即参数名
        if (Uploadfile != null) {
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), Uploadfile);
            builder.addFormDataPart("file", Uploadfile.getName(), imageBody);//imgfile 后台接收图片流的参数名
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        RetrofitManager.getInstance().createReq(BlogService.class).postDynamic(parts).enqueue(new Callback<WrapperRspEntity<MyissueDetail>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<MyissueDetail>> call, Response<WrapperRspEntity<MyissueDetail>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    showToastShort(response.body().getMsg());
                    ReleaseDynamicActivity.this.finish();
                } else {
                    showToastShort(response.body().getMsg());
                }
            }
            @Override
            public void onFailure(Call<WrapperRspEntity<MyissueDetail>> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.ivImage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivImage:
                ShowPopuWindow();
                break;
        }
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
