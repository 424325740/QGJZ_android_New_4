package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.ClassifyForAll;
import com.qigaikj.parttimejob.bean.ClassifyViewPagerBean;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.menuList.ClassiFyErjiMenuAdapter;
import com.qigaikj.parttimejob.menuList.ClassiFyYijiMenuAdapter;
import com.qigaikj.parttimejob.menuList.GlideRoundTransform;
import com.qigaikj.parttimejob.menuList.MyViewPagerAdapter;
import com.qigaikj.parttimejob.util.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 分类页面
 */
public class ClassiFyActivity extends BaseActivity {
    private static final String TAG = "ClassiFyActivity";
    private ViewPager viewpager;
    private LinearLayout mindicator;// 底部页面小圆点布局
    private List<ImageView> imgList;// 展示的视图列表
    private ImageView[] bottomImages;// 底部当前小圆点
    private int currentIndex = 0;// 当前显示的页面position
    private boolean isAuto = true;// 当前自动的状态为true
    private boolean isTouch = false;// 当前触碰状态为false
    private List<String> imgUrlList = new ArrayList<>();
    private ImageView img;

    private ListView lv_menu;
    private ListView lv_home;
    private List<String> tabName = new ArrayList<>();
    private List<ClassifyForAll> YiJimenuList = new ArrayList<>();
    private List<Integer> showTitle = new ArrayList<>();

    /**轮播图*/
    private List<ClassifyViewPagerBean> BannerList = new ArrayList<>();

    //left
    private ClassiFyYijiMenuAdapter menuadapter;
    //right
    private ClassiFyErjiMenuAdapter menufirstadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**加载banner图*/
        ObtionViewPagerNetWork();
        /**加载分类数据*/
        ObtionClassifyDataNetWork();

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_classi_fy;
    }

    @Override
    protected void initDetail() {

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        lv_home = (ListView) findViewById(R.id.lv_home);
        mindicator = (LinearLayout) findViewById(R.id.mindicator);
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuadapter.setSelectItem(position);
                menuadapter.notifyDataSetInvalidated();
                lv_home.setSelection(position);
            }
        });
        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showToastShort("点击了 = " + position);
            }
        });
        lv_home.setOnScrollListener(new AbsListView.OnScrollListener() {
            private boolean scrollFlag;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                scrollFlag = scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (!scrollFlag) {
                    return;
                }
                Log.d(TAG, "onScroll: " + firstVisibleItem);
                menuadapter.setSelectItem(firstVisibleItem);
                lv_menu.smoothScrollToPosition(firstVisibleItem);
                menuadapter.notifyDataSetInvalidated();
            }
        });
    }

    @Override
    protected void initTitleView() {
        setTvTextTitle("分类");
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化viewPager数据
     */
    private void initViewPager(List<String> list) {
        RequestManager requestManager;
        imgList = new ArrayList<ImageView>();
        for (int i = 0; i < list.size(); i++) {
            View v = View.inflate(this, R.layout.viewpager_item, null);
            img = (ImageView) v.findViewById(R.id.mimg);
            //加载banner圆角图片
            requestManager = Glide.with(this);
            requestManager.load(list.get(i)).transform(new GlideRoundTransform(getApplicationContext())).into(img);
            imgList.add(img);
        }
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(imgList);
        viewpager.setAdapter(adapter);
    }

    /**
     * 初始化底部小圆点
     */
    private void initBottom() {
        bottomImages = new ImageView[imgList.size()];

        for (int j = 0; j < bottomImages.length; j++) {
            ImageView bottom = new ImageView(this);
            // 给bottom控件加上布局属性
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    60, 60);// 控件的宽高
            params.setMargins(5, 0, 5, 0);// 控件左、上、右、下
            bottom.setLayoutParams(params);
            // 初始时默认第一个小圆点为选中状态
            if (j == 0) {
                bottom.setImageResource(R.mipmap.banner_incidicatorselect);
            } else {
                bottom.setImageResource(R.mipmap.banner_indicatornormal);
            }
            bottomImages[j] = bottom;
            // 将底部小圆点加入视加入线性布局中
            mindicator.addView(bottom);
        }
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                // 将当前显示页面的position对应的小圆点设为选中状态
                for (int i = 0; i < bottomImages.length; i++) {
                    int p = arg0 % imgList.size();
                    if (i == p) {
                        bottomImages[i].setImageResource(R.mipmap.banner_incidicatorselect);
                    } else {
                        bottomImages[i].setImageResource(R.mipmap.banner_indicatornormal);
                    }
                }
                // 当前显示的position赋值给成员变量
                currentIndex = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                // 判断是否在手动滑动
                // 当手动滑动视图的时候将isTouch标志位设为true
                if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
                    isTouch = false;
                } else {
                    isTouch = true;
                }
            }
        });
    }

    Thread t = new Thread(new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (isAuto) {
                try {
                    t.sleep(3000);// 睡眠3秒
                    handler.sendEmptyMessage(0);// 一直发送空消息
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    });
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                if (isTouch) {// 如果手动滑动时不处理
                    return;
                }
                // 如果自动轮播时，让代表显示页面的指示位置+1
                currentIndex++;
                viewpager.setCurrentItem(currentIndex);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();// 让线程停止
        isAuto = false;
    }

    public void ObtionClassifyDataNetWork() {
        RetrofitManager.getInstance().createReq(BlogService.class).getClassifyForAll().enqueue(new Callback<WrapperRspEntity<List<ClassifyForAll>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<ClassifyForAll>>> call, Response<WrapperRspEntity<List<ClassifyForAll>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (response.body().getData().size() > 0) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            showTitle.add(i);
                            ClassifyForAll dataBean = response.body().getData().get(i);
                            String yiji = dataBean.getYiji();
                            tabName.add(yiji);
                            YiJimenuList.add(dataBean);
                        }
                        menuadapter = new ClassiFyYijiMenuAdapter(ClassiFyActivity.this, tabName);
                        lv_menu.setAdapter(menuadapter);
                        menufirstadapter = new ClassiFyErjiMenuAdapter(ClassiFyActivity.this, YiJimenuList);
                        lv_home.setAdapter(menufirstadapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<WrapperRspEntity<List<ClassifyForAll>>> call, Throwable t) {

            }
        });
    }

    /**
     * 加载banner图数据
     */
    private void ObtionViewPagerNetWork() {

        RetrofitManager.getInstance().createReq(BlogService.class).getClassifyForAll_Banner().enqueue(new Callback<WrapperRspEntity<List<ClassifyViewPagerBean>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<ClassifyViewPagerBean>>> call, Response<WrapperRspEntity<List<ClassifyViewPagerBean>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (response.body().getData().size() > 0) {
                        BannerList.addAll(response.body().getData());
                        for (ClassifyViewPagerBean bean : BannerList) {
                            imgUrlList.add(bean.getPic_url());
                        }
                        initViewPager(imgUrlList);
                        initBottom();
                        t.start();
                    }
                }
            }
            @Override
            public void onFailure(Call<WrapperRspEntity<List<ClassifyViewPagerBean>>> call, Throwable t) {

            }
        });
    }
}
