package com.qigaikj.parttimejob.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.ViewpagerAdapter;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.HomeBannerList;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.menuList.MyViewPagerAdapter;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.ScreenInfo;
import com.qigaikj.parttimejob.view.follow.FollowItemOne;
import com.qigaikj.parttimejob.view.follow.FollowItemTwo;
import com.qigaikj.parttimejob.view.userinfodetail.UserInfoDetailOne;
import com.qigaikj.parttimejob.view.userinfodetail.UserInfoDetailThree;
import com.qigaikj.parttimejob.view.userinfodetail.UserInfoDetailTwo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * 个人资料详情
 *
 * */

public class UserInfoDetailActivity extends BaseActivity{

    @BindView(R.id.ivLeft)//退出
            ImageView ivLeft;
    @BindView(R.id.ivRight_left)//二维码
            ImageView ivRight_left;
    @BindView(R.id.ivRight_right)//更多
            ImageView ivRight_right;
    @BindView(R.id.banner)//轮播图
            Banner banner;

    @BindView(R.id.tvUserData)//资料
            TextView tvUserData;
    @BindView(R.id.tvDynamic)//动态
            TextView tvDynamic;
    @BindView(R.id.tvComment)//评论
            TextView tvComment;
    @BindView(R.id.rl_jz_lay)
            RelativeLayout rlJzLay;
    @BindView(R.id.pagerDetail)//主视图
            ViewPager pagerDetail;

    @BindView(R.id.tvPrivateLetter)//私信
            LinearLayout tvPrivateLetter;
    @BindView(R.id.tvShare)//分享
            LinearLayout tvShare;
    @BindView(R.id.tvRightOff_YueTa)//约TA
            LinearLayout tvRightOff_YueTa;

    public Context context;
    private RelativeLayout rlDefault;
    private TextView tvDefaultMention;

    private UserInfoDetailOne faBuFragment1;
    private UserInfoDetailTwo faBuFragment2;
    private UserInfoDetailThree faBuFragment3;
    private List<Fragment> fragmentList;
    private ViewpagerAdapter viewpagerAdapter;

    /**
     * 首页BannerList数据列表
     */
    private List<HomeBannerList> AllbannerList = new ArrayList<>();
    private List<String> bannerList = new ArrayList<String>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        ButterKnife.bind(UserInfoDetailActivity.this);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_info_detail;
    }

    @Override
    protected void initDetail() {
        banner.isAutoPlay(true);
        banner.setDelayTime(5000);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                Intent intent = new Intent(UserInfoDetailActivity.this, WebWiewActivity.class);
//                intent.putExtra("url", AllbannerList.get(position).url);
//                startActivity(intent);
            }
        });
        setStatusBarColor(Color.BLACK);
        getData(); //获取用户详情页banner

        fragmentList = new ArrayList<>();
        faBuFragment1 = new UserInfoDetailOne();
        faBuFragment2 = new UserInfoDetailTwo();
        faBuFragment3 = new UserInfoDetailThree();
        fragmentList.add(faBuFragment1);
        fragmentList.add(faBuFragment2);
        fragmentList.add(faBuFragment3);
        viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(), fragmentList);
        pagerDetail.setAdapter(viewpagerAdapter);
        pagerDetail.setOffscreenPageLimit(3);
        InitImageView();
        pagerDetail.setOnPageChangeListener(new MayPageChangeLisener());
        pagerDetail.setCurrentItem(0);
        tvUserData.setTextColor(getResources().getColor(R.color.mytext_1));

    }

    @OnClick({R.id.ivLeft, R.id.ivRight_left, R.id.ivRight_right, R.id.tvUserData, R.id.tvDynamic, R.id.tvComment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivLeft: //退出
                UserInfoDetailActivity.this.finish();
                break;
            case R.id.ivRight_left: //二维码
                break;
            case R.id.ivRight_right://更多
                break;
            case R.id.tvUserData: //资料
                pagerDetail.setCurrentItem(0);
                break;
            case R.id.tvDynamic://动态
                pagerDetail.setCurrentItem(1);
                break;
            case R.id.tvComment: //评论
                pagerDetail.setCurrentItem(2);
                break;
        }
    }

    private int currIndex = 0;// 当前页卡编号
    private int screenWidth;//控件宽度

    //获取滑动框的大小，
    private void InitImageView() {
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
//        screenWidth = llFrm1Layout.getLayoutParams().width;//获取控件宽度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rlJzLay.getLayoutParams();
        lp.width = screenWidth / 3;//设置黑色阴影靠里，所有宽度加20，下面，滑动距离减20
        rlJzLay.setLayoutParams(lp);
    }

    public class MayPageChangeLisener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //此处滑动以父控件代替屏幕宽度
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rlJzLay.getLayoutParams();
            if (currIndex == 0 && position == 0)// 0->1
            {
                lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 4) + currIndex
                        * (screenWidth / 3));

            } else if (currIndex == 1 && position == 0) // 1->0
            {
                lp.leftMargin = (int) (-(1 - positionOffset)
                        * (screenWidth * 1.0 / 3) + currIndex
                        * (screenWidth / 3));

            } else if (currIndex == 1 && position == 1) // 1->2
            {
                lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 3) + currIndex
                        * (screenWidth / 3));
            } else if (currIndex == 2 && position == 1) // 2->1
            {
                lp.leftMargin = (int) (-(1 - positionOffset)
                        * (screenWidth * 1.0 / 3) + currIndex
                        * (screenWidth / 3));
            } else if (currIndex == 2 && position == 2) // 2->3
            {
                lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 3) + currIndex
                        * (screenWidth / 3));
            } else if (currIndex == 3 && position == 2) // 3->2
            {
                lp.leftMargin = (int) (-(1 - positionOffset)
                        * (screenWidth * 1.0 / 3) + currIndex
                        * (screenWidth / 3));
            } else if (currIndex == 3 && position == 3) {
                lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 3) + currIndex
                        * (screenWidth / 3));
            }

            rlJzLay.setLayoutParams(lp);
        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    tvUserData.setTextColor(getResources().getColor(R.color.mytext_1));
                    tvUserData.setTextSize(16);
                    tvDynamic.setTextColor(getResources().getColor(R.color.mytext_3));
                    tvDynamic.setTextSize(14);
                    tvComment.setTextColor(getResources().getColor(R.color.mytext_3));
                    tvComment.setTextSize(14);
                    break;
                case 1:
                    tvUserData.setTextColor(getResources().getColor(R.color.mytext_3));
                    tvUserData.setTextSize(14);
                    tvDynamic.setTextColor(getResources().getColor(R.color.mytext_1));
                    tvDynamic.setTextSize(16);
                    tvComment.setTextColor(getResources().getColor(R.color.mytext_3));
                    tvComment.setTextSize(14);
                    break;
                case 2:
                    tvUserData.setTextColor(getResources().getColor(R.color.mytext_3));
                    tvUserData.setTextSize(14);
                    tvDynamic.setTextColor(getResources().getColor(R.color.mytext_3));
                    tvDynamic.setTextSize(14);
                    tvComment.setTextColor(getResources().getColor(R.color.mytext_1));
                    tvComment.setTextSize(16);
                    break;

            }
            currIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 初始化数据
     */
    public void getData() {
        //清除数据缓存
        if (bannerList != null && bannerList.size() > 0) {
            bannerList.clear();
        }
        getHomeBanner();//获取首页banner轮播图
    }

    /**
     * 获取首页banner数据
     */
    private void getHomeBanner() {
        RetrofitManager.getInstance().createReq(BlogService.class).getHomeBannerList().enqueue(new Callback<WrapperRspEntity<List<HomeBannerList>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<HomeBannerList>>> call, Response<WrapperRspEntity<List<HomeBannerList>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (response.body().getData().size() > 0) {
                        AllbannerList.addAll(response.body().getData());
                        for (int i = 0; i < AllbannerList.size(); i++) {
                            bannerList.add(AllbannerList.get(i).pic);
                        }
                        if (AllbannerList.size() < 2) {
                            banner.isAutoPlay(false);
                        }
                        banner.setImages(bannerList)
                                .setImageLoader(new ImageLoader() {
                                    @Override
                                    public void displayImage(Context context, Object path, ImageView imageView) {
                                        Glide.with(UserInfoDetailActivity.this)
                                                .load(path)
                                                .into(imageView);}}).start();
                    }
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<List<HomeBannerList>>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void initTitleView() {
        hidetitle();
    }
}
