package com.qigaikj.parttimejob.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.activity.AuthenticationActivity;
import com.qigaikj.parttimejob.activity.ContactsActivity;
import com.qigaikj.parttimejob.activity.DetailsActivity;
import com.qigaikj.parttimejob.activity.FollowActivity;
import com.qigaikj.parttimejob.activity.LoginHlperActivity;
import com.qigaikj.parttimejob.activity.MoreActivity;
import com.qigaikj.parttimejob.activity.MyWalletActivity;
import com.qigaikj.parttimejob.activity.SettingActivity;
import com.qigaikj.parttimejob.activity.SkillActivity;
import com.qigaikj.parttimejob.activity.UserSecurityActivity;
import com.qigaikj.parttimejob.activity.home.MessageActivity;
import com.qigaikj.parttimejob.activity.my.EditActivity;
import com.qigaikj.parttimejob.activity.my.ExtensionActivity;
import com.qigaikj.parttimejob.activity.my.YouHuiJuanActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.RecommendList;
import com.qigaikj.parttimejob.bean.UserImage;
import com.qigaikj.parttimejob.bean.UserInfo;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.MemoryUtils;
import com.qigaikj.parttimejob.util.PhotoUtils;
import com.qigaikj.parttimejob.util.RetrofitCallback;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.CameraChoiceDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/11/3/003.
 * 个人中心
 */

public class Fragment5 extends Fragment {

    @BindView(R.id.iv_Message)
    ImageView iv_Message;
    @BindView(R.id.User_avator)
    CircleImageView User_avator;
    @BindView(R.id.tv_Logoin)
    TextView tv_Logoin;
    @BindView(R.id.ll_User)
    LinearLayout ll_User;
    @BindView(R.id.tv_User_Name)
    TextView tv_User_Name;
    @BindView(R.id.tv_User_info)
    TextView tv_User_info;

    @BindView(R.id.ll_Wallet)
    LinearLayout ll_Wallet;
    @BindView(R.id.tv_Wallet)
    TextView tv_Wallet;
    @BindView(R.id.ll_Coupon)
    LinearLayout ll_Coupon;
    @BindView(R.id.tv_Coupon)
    TextView tv_Coupon;
    @BindView(R.id.ll_Income)
    LinearLayout ll_Income;
    @BindView(R.id.tv_Income)
    TextView tv_Income;

    @BindView(R.id.iv_Advertisement)
    ImageView iv_Advertisement;

    @BindView(R.id.rvChang) // 为你推荐
            RecyclerView rvChang;
    @BindView(R.id.bi)
    ImageView bi;
    private RecommendAdapter mAdapter;
    private List<RecommendList> recommendList = new ArrayList<>();

    Unbinder unbinder;
    String tocken;
    View view;

    /**
     * data
     */
    private boolean isTock = false;//默认未登陆
    private Intent intent;
    private Intent intent1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment5, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }


    private void initView() {
    }


    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();//获取用户信息
        recommendList.clear();
        tocken = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        if (tocken == null || tocken.equals("")) {
            isTock = false;
            ll_User.setVisibility(View.GONE);
            bi.setVisibility(View.GONE);
            tv_Logoin.setVisibility(View.VISIBLE);
        } else {
            isTock = true;
            bi.setVisibility(View.VISIBLE);
            ll_User.setVisibility(View.VISIBLE);
            tv_Logoin.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.llUserInfo, R.id.ivSetting, R.id.iv_Message, R.id.User_avator, R.id.tv_Logoin, R.id.ll_Wallet, R.id.ll_Coupon, R.id.ll_Income, R.id.iv_Advertisement
            , R.id.lin_jineng1, R.id.lin_xuqiu, R.id.lin_guanzhu, R.id.lin_renzheng, R.id.lin_tuiguang, R.id.lin_lianxi, R.id.lin_zhanghao, R.id.lin_gengduo,R.id.bi,R.id.tv_User_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llUserInfo://用户资料
//                ActivityUtils.startActivity(getActivity(), UserInfoDetailActivity.class);
                break;
            case R.id.ivSetting://用户设置
                ActivityUtils.startActivity(getActivity(), SettingActivity.class);
                break;
            case R.id.iv_Message://用户消息
                ActivityUtils.startActivity(getActivity(), MessageActivity.class);
                break;
            case R.id.User_avator://点击用户头像
                if (tocken.equals("") || tocken == null) {
                    ActivityUtils.startActivity(getActivity(), LoginHlperActivity.class);
                } else {
                    ShowPopuWindow();
                }
                break;
            case R.id.tv_Logoin://请登陆
                ActivityUtils.startActivity(getActivity(), LoginHlperActivity.class);
                break;
            case R.id.ll_Wallet://钱包
                ActivityUtils.startActivity(getActivity(), MyWalletActivity.class);
                break;
            case R.id.ll_Coupon://优惠卷
                ActivityUtils.startActivity(getActivity(), YouHuiJuanActivity.class);
                break;
            case R.id.ll_Income://收入
                Toast.makeText(getActivity(), "穷！(*￣へ￣*)", Toast.LENGTH_SHORT);
                break;
            case R.id.iv_Advertisement://广告
                Toast.makeText(getActivity(), "没广告！(*￣へ￣*)", Toast.LENGTH_SHORT);
                break;
            case R.id.lin_jineng1://技能
                intent1 = new Intent(getActivity(),SkillActivity.class);
                intent1.putExtra("state","0");
                startActivity(intent1);
                break;
            case R.id.lin_xuqiu://需求
                intent1 =new Intent(getActivity(),SkillActivity.class);
                intent1.putExtra("state","1");
                startActivity(intent1);
                break;
            case R.id.lin_guanzhu://关注
                ActivityUtils.startActivity(getActivity(), FollowActivity.class);
                break;
            case R.id.lin_renzheng://认证
                ActivityUtils.startActivity(getActivity(), AuthenticationActivity.class);
                break;
            case R.id.lin_tuiguang://推广
                ActivityUtils.startActivity(getActivity(), ExtensionActivity.class);
                break;
            case R.id.lin_lianxi://联系人
                ActivityUtils.startActivity(getActivity(), ContactsActivity.class);
                break;
            case R.id.lin_zhanghao://账号安全
                ActivityUtils.startActivity(getActivity(), UserSecurityActivity.class);
                break;
            case R.id.lin_gengduo://更多
                ActivityUtils.startActivity(getActivity(), MoreActivity.class);
                break;
            case R.id.bi://编辑
                ActivityUtils.startActivity(getActivity(), EditActivity.class);
                break;
            case R.id.tv_User_info://个人主页
//                ActivityUtils.startActivity(getActivity(), DetailsActivity.class);
                intent1 = new Intent(getActivity(),DetailsActivity.class);
                intent1.putExtra("zhuangtai","1");
                startActivity(intent1);
        }
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        String tocken = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        if (tocken.equals("") || tocken == null) {//未登录
            ll_User.setVisibility(View.GONE);
            bi.setVisibility(View.GONE);
            tv_Logoin.setVisibility(View.VISIBLE);
            User_avator.setImageResource(R.mipmap.logo);
        } else {
            ll_User.setVisibility(View.VISIBLE);
            bi.setVisibility(View.VISIBLE);
            tv_Logoin.setVisibility(View.GONE);
            GetRecommend();
            RetrofitManager.getInstance().createReq(BlogService.class).getUserInfo(tocken).enqueue(new Callback<WrapperRspEntity<UserInfo>>() {
                @Override
                public void onResponse(Call<WrapperRspEntity<UserInfo>> call, Response<WrapperRspEntity<UserInfo>> response) {
                    if (response.body().getStatus() == RetrofitManager.CODE) {
                        String useid = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.USER_ID, "");
                        //头像地址
                        if (response.body().getData() != null && !response.body().getData().equals("")) {
                            Glide.with(getContext()).load(response.body().getData().logo).into(User_avator);
                        }
                        //昵称
                        if (response.body().getData().uname != null && !response.body().getData().uname.equals("")) {
                            tv_User_Name.setText(response.body().getData().uname);
                        }
                        //广告图
                        if (response.body().getData().advert.pic != null && !response.body().getData().advert.pic.equals("")) {
                            Glide.with(getContext()).load(response.body().getData().advert.pic).into(iv_Advertisement);
                            iv_Advertisement.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<WrapperRspEntity<UserInfo>> call, Throwable t) {

                }
            });
        }
    }

    /**
     * 获取为你推荐
     */
    public void GetRecommend() {
        String tocken = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        if (tocken.equals("") || tocken == null) {//未登录
            rvChang.setVisibility(View.GONE);
        } else {
            rvChang.setVisibility(View.VISIBLE);
            RetrofitManager.getInstance().createReq(BlogService.class).getRecommend(tocken).enqueue(new Callback<WrapperRspEntity<List<RecommendList>>>() {
                @Override
                public void onResponse(Call<WrapperRspEntity<List<RecommendList>>> call, Response<WrapperRspEntity<List<RecommendList>>> response) {
                    if (response.body().getStatus() == RetrofitManager.CODE) {
                        recommendList.addAll(response.body().getData());

                   /*     for(int j=0;j<itemList.size();j++)
                        {
                            for(int i = 0 ; i<itemList.get(j).size();i++)
                            {
                                Map<String,Object> map=(Map<String, Object>) itemList.get(j).get(i);
                            }
                        }*/

                        //通知适配器刷新数据
                        mAdapter = new RecommendAdapter(R.layout.home_recommend_list_item,recommendList);
                        Log.i("asd", "recommendList == " + recommendList);
                        initAdapter();
                    }
                }

                @Override
                public void onFailure(Call<WrapperRspEntity<List<RecommendList>>> call, Throwable t) {

                }
            });
        }
    }

    /**
     * 设置RecyclerView属性
     */
    private void initAdapter() {
        rvChang.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter.openLoadAnimation();
        rvChang.setAdapter(mAdapter);//设置adapter
        //设置item点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }


    class RecommendAdapter extends BaseQuickAdapter<RecommendList, BaseViewHolder> {
        public RecommendAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        public void convert(BaseViewHolder baseViewHolder, RecommendList data) {
            Glide.with(mContext).load(data.logo).into((ImageView) baseViewHolder.getView(R.id.ivUserPortrait));
            baseViewHolder.setText(R.id.tvUserName, data.uname);
            baseViewHolder.setText(R.id.tvUserOccupation, data.skill);
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

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                Toast.makeText(getActivity(), "您已拒绝过一次", Toast.LENGTH_SHORT);
            }
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                File file1 = new File(fileUri, getPhotoFileName());
                imageUri = Uri.fromFile(file1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(getContext(), "com.qigaikj.parttimejob.FileProvider", file1);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {

                Toast.makeText(getContext(), "设备没有SD卡", Toast.LENGTH_SHORT);
            }
        }
    }

    /**
     * 自动获取sdk权限
     */
    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }

    private CameraChoiceDialog jxCameraChoiceDialog;//弹出框

    /**
     * 点击显示弹出框
     */
    private void ShowPopuWindow() {
        if (jxCameraChoiceDialog == null) {
            jxCameraChoiceDialog = new CameraChoiceDialog(getActivity(), R.style.DialogTheme, new View.OnClickListener() {
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
            WindowManager m = getActivity().getWindowManager();
            Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = jxCameraChoiceDialog.getWindow().getAttributes();  //获取对话框当前的参数值
            p.width = (int) (d.getWidth());    //宽度设置为全屏
            jxCameraChoiceDialog.getWindow().setAttributes(p);
        } else {
            jxCameraChoiceDialog.show();
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
                            imageUri = FileProvider.getUriForFile(getContext(), "com.qigaikj.parttimejob.FileProvider", file);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        Toast.makeText(getContext(), "设备没有SD卡", Toast.LENGTH_SHORT);
                    }
                } else {

                    Toast.makeText(getContext(), "请允许打开相机！！", Toast.LENGTH_SHORT);
                }
                break;
            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {
                    Toast.makeText(getContext(), "请允许打操作SDCard！！", Toast.LENGTH_SHORT);
                }
                break;
        }
    }

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
                        Uri newUri = Uri.parse(PhotoUtils.getPath(getContext(), data.getData()));
                        File f = new File(newUri.getPath());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            newUri = FileProvider.getUriForFile(getContext(), "com.qigaikj.parttimejob.FileProvider", f);
                            newUri = FileProvider.getUriForFile(getContext(), "com.qigaikj.parttimejob.FileProvider", f);
                        }
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else {
                        Toast.makeText(getContext(), "设备没有SD卡！", Toast.LENGTH_SHORT);
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Log.i("asdf", "头像地址获取成功--------------------------" + cropImageUri);
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, getContext());
                    if (bitmap != null) {
                        User_avator.setImageBitmap(bitmap);
                    }
                    upLoad();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 上传图片
     * create by weiang at 2016/5/20 17:33.
     */
    private void upLoad() {
        String tocken = SharedPreferencesHelper.getInstance(getActivity()).getString(SharedPreferencesHelper.TOKEN, "");
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("token", tocken);//ParamKey.TOKEN 自定义参数key常量类，即参数名
//        RequestBody imageBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), Uploadfile);
        builder.addFormDataPart("file", Uploadfile.getName(), imageBody);//imgfile 后台接收图片流的参数名

        List<MultipartBody.Part> parts = builder.build().parts();
        RetrofitManager.getInstance().createReq(BlogService.class).uploadUserImage(parts).enqueue(new RetrofitCallback<UserImage>(getContext()) {
            @Override
            public void onRes(Call<WrapperRspEntity<UserImage>> call, Response<WrapperRspEntity<UserImage>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    LogUtils.i("上传成功" + RetrofitManager.BaseImageUrl + response.body().getData().getAvatar_url());
                    Glide.with(getContext()).load(response.body().getData().getAvatar_url()).into(User_avator);
//                    Glide.with(getContext()).load(response.body().getData().getAvatar_url())
//                            .placeholder(R.mipmap.icon_moren)
//                            .bitmapTransform(new BlurTransformation(getContext(), 12), new CenterCrop(getContext()))
//                            .into(ivBgFrm5Image);
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErr(Call<WrapperRspEntity<UserImage>> call, Throwable t) {

            }
        });
    }
}
