package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.view.follow.FollowItemOne;
import com.qigaikj.parttimejob.view.follow.FollowItemTwo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的页面关注功能
 */
public class FollowActivity extends BaseActivity{

    @BindView(R.id.tvFollowUser)
    TextView tvFollowUser;
    @BindView(R.id.tvFollowJob)
    TextView tvFollowJob;
    @BindView(R.id.viewPager)
            ViewPager viewPager;

    private FollowItemOne view0;
    private FollowItemTwo view1;
    private MyViewPagerAdapter myPageAdapter;

    private View FollowItemOneView;
    private View FollowItemTwoView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(FollowActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_follow;
    }

    @Override
    protected void initTitleView() {
            hidetitle();
    }

    @Override
    protected void initDetail() {
        myPageAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myPageAdapter);
    }

    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 2;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            try {
                switch (position) {
                    case 0:
                        view0 = new FollowItemOne();
                        FollowItemOneView = view0.CancelGetview(FollowActivity.this);
                        container.addView(FollowItemOneView);
                        return FollowItemOneView;
                    case 1:
                        view1 = new FollowItemTwo();
                        FollowItemTwoView = view1.CancelGetview(FollowActivity.this);
                        container.addView(FollowItemTwoView);
                        return FollowItemTwoView;
                }
            } catch (Exception ex) {
                System.out.println("ex" + ex);
            }
            return container;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }
    }

    @OnClick({R.id.ivLeftQuit, R.id.tvFollowUser, R.id.tvFollowJob})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivLeftQuit:
                FollowActivity.this.finish();
                break;
            case R.id.tvFollowUser:
                viewPager.setCurrentItem(0, true);
                changeTabTextColor(0);
                break;
            case R.id.tvFollowJob:
                viewPager.setCurrentItem(1, true);
                changeTabTextColor(1);
                break;
        }
    }

    // 改变选中颜色
    private void changeTabTextColor(int index) {
        switch (index) {
            case 0:
                tvFollowUser.setTextColor(getResources().getColor(R.color.mytext_1));
                tvFollowUser.setTextSize(16);
                tvFollowJob.setTextColor(getResources().getColor(R.color.mytext_3));
                tvFollowJob.setTextSize(14);
                break;
            case 1:
                tvFollowUser.setTextColor(getResources().getColor(R.color.mytext_3));
                tvFollowUser.setTextSize(14);
                tvFollowJob.setTextColor(getResources().getColor(R.color.mytext_1));
                tvFollowJob.setTextSize(16);
                break;
        }
    }
}
