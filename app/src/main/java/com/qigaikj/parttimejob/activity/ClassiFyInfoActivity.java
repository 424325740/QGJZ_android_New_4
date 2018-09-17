package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.ropdownmenu.DropdownButton;
import com.qigaikj.parttimejob.ropdownmenu.DropdownItemObject;
import com.qigaikj.parttimejob.ropdownmenu.DropdownListView;
import com.qigaikj.parttimejob.util.LogUtils;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页的下拉菜单
 */
public class ClassiFyInfoActivity extends BaseActivity implements DropdownListView.Container {

    @BindView(R.id.dbOne)//
            DropdownButton chooseOne;
    @BindView(R.id.dbTwo)//
            DropdownButton chooseTwo;
    @BindView(R.id.dbThree)//
            DropdownButton chooseThree;

    @BindView(R.id.dropdownType)//
            DropdownListView dropdownOne;
    @BindView(R.id.dropdownLanguage)//
            DropdownListView dropdownTwo;
    @BindView(R.id.dropdownShaiXuan)//
            DropdownListView dropdownThree;

    @BindView(R.id.mask)//
            View mask;

    private List<DropdownItemObject> chooseOneData = new ArrayList<>();//选择项1
    private List<DropdownItemObject> chooseTwoData = new ArrayList<>();//选择项2
    private List<DropdownItemObject> chooseThreeData = new ArrayList<>();//选择项3
    Animation dropdown_in, dropdown_out, dropdown_mask_out;
    private DropdownListView currentDropdownList;

    DropdownListView.Container container;

    DemoAdapter demoAdapter;

    private List<String> ClassiFyInfoList = new ArrayList();
    private String headers[] = {"约TA", "最新发布", "筛选"};
    private List<View> popupViews = new ArrayList<>();
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        /**加载数据*/
        ObtionClassifyDataNetWork();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_classi_fy_info;
    }

    @Override
    protected void initDetail() {

        dropdown_in = AnimationUtils.loadAnimation(ClassiFyInfoActivity.this, R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(ClassiFyInfoActivity.this, R.anim.dropdown_out);
        dropdown_mask_out = AnimationUtils.loadAnimation(ClassiFyInfoActivity.this, R.anim.dropdown_mask_out);
        reset();
        chooseOneData.add(new DropdownItemObject("约TA", 0, "约TA"));
        chooseOneData.add(new DropdownItemObject("TA约我", 1, "TA约我"));
        chooseOneData.add(new DropdownItemObject("兼职", 2, "兼职"));
        dropdownOne.bind(chooseOneData, chooseOne, ClassiFyInfoActivity.this, 0);

        chooseTwoData.add(new DropdownItemObject("最新发布", 0, "最新发布"));
        chooseTwoData.add(new DropdownItemObject("人气最高", 1, "人气最高"));
        chooseTwoData.add(new DropdownItemObject("距离最近", 2, "距离最近"));
        chooseTwoData.add(new DropdownItemObject("诚信值最高", 3, "诚信值最高"));
        chooseTwoData.add(new DropdownItemObject("价格最高", 4, "价格最高"));
        dropdownTwo.bind(chooseTwoData, chooseTwo, ClassiFyInfoActivity.this, 0);

        chooseThreeData.add(new DropdownItemObject("筛选", 0, "筛选"));
        dropdownThree.bind(chooseThreeData, chooseThree, ClassiFyInfoActivity.this, 0);

        dropdown_mask_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (currentDropdownList == null) {
                    reset();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void show(DropdownListView view) {
        if (currentDropdownList != null) {
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_out);
            currentDropdownList.setVisibility(View.GONE);
            currentDropdownList.button.setChecked(false);
        }
        currentDropdownList = view;
        mask.clearAnimation();
        mask.setVisibility(View.VISIBLE);
        currentDropdownList.clearAnimation();
        currentDropdownList.startAnimation(dropdown_in);
        currentDropdownList.setVisibility(View.VISIBLE);
        currentDropdownList.button.setChecked(true);
    }

    public void hide() {
        if (currentDropdownList != null) {
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_out);
            currentDropdownList.button.setChecked(false);
            mask.clearAnimation();
            mask.startAnimation(dropdown_mask_out);
        }
        currentDropdownList = null;
    }

    public void onSelectionChanged(DropdownListView view) {
        if (view == dropdownOne) {

        }
    }

    /**
     * 设置动画
     */
    void reset() {
        chooseOne.setChecked(false);
        chooseTwo.setChecked(false);
        chooseThree.setChecked(false);

        dropdownOne.setVisibility(View.GONE);
        dropdownTwo.setVisibility(View.GONE);
        dropdownThree.setVisibility(View.GONE);
        mask.setVisibility(View.GONE);

        dropdownOne.clearAnimation();
        dropdownTwo.clearAnimation();
        dropdownThree.clearAnimation();
        mask.clearAnimation();
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

        @Override
        public DemoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            DemoAdapter.ViewHolder viewHolder = new DemoAdapter.ViewHolder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_class_ifyerjimenu, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.tv_UserName.setText(ClassiFyInfoList.get(position));
            holder.tv_UserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.i("---------" + ClassiFyInfoList.get(holder.getLayoutPosition()).toString()+"------------点击的item" + holder.getLayoutPosition());
                }
            });
        }

        @Override
        public int getItemCount() {
            return ClassiFyInfoList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private LinearLayout ll_User;
            private ImageView iv_UserLogo;
            private TextView tv_UserName;
            private TextView tv_User_Speciality;
            private TextView tv_Distance;
            private ImageView icon_User_1;
            private ImageView icon_User_2;
            private ImageView icon_User_3;
            private ImageView icon_User_4;
            private ImageView iv_Imager_1;
            private ImageView iv_Imager_2;
            private ImageView iv_Imager_3;
            private TextView tv_User_info;

            public ViewHolder(View itemView) {
                super(itemView);
                ll_User = (LinearLayout) itemView.findViewById(R.id.ll_User);
                iv_UserLogo = (ImageView) itemView.findViewById(R.id.iv_UserLogo);
                tv_UserName = (TextView) itemView.findViewById(R.id.tv_UserName);
                tv_User_Speciality = (TextView) itemView.findViewById(R.id.tv_User_Speciality);
                tv_Distance = (TextView) itemView.findViewById(R.id.tv_Distance);
                icon_User_1 = (ImageView) itemView.findViewById(R.id.icon_User_1);
                icon_User_2 = (ImageView) itemView.findViewById(R.id.icon_User_2);
                icon_User_3 = (ImageView) itemView.findViewById(R.id.icon_User_3);
                icon_User_4 = (ImageView) itemView.findViewById(R.id.icon_User_4);
                iv_Imager_1 = (ImageView) itemView.findViewById(R.id.iv_Imager_1);
                iv_Imager_2 = (ImageView) itemView.findViewById(R.id.iv_Imager_2);
                iv_Imager_3 = (ImageView) itemView.findViewById(R.id.iv_Imager_3);
                tv_User_info = (TextView) itemView.findViewById(R.id.tv_User_info);
            }
        }
    }

    public void ObtionClassifyDataNetWork() {
        String tid = getIntent().getStringExtra("tid");
        String token = SharedPreferencesHelper.getInstance(ClassiFyInfoActivity.this).getString(SharedPreferencesHelper.TOKEN, "");
        String Title = getIntent().getStringExtra("Title");
        if (!TextUtils.isEmpty(Title)) {
            setTvTextTitle(Title);
        }
    }
}
