package com.qigaikj.parttimejob.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 发布兼职页面
 */
public class ReleasePartTimeJobActivity extends BaseActivity {

    @BindView(R.id.etJobName) //输入工作名称
            EditText etJobName;
    @BindView(R.id.etJobAddress) // 输入工作地址
            EditText etJobAddress;
    @BindView(R.id.tvStartTime) // 开始时间
            TextView tvStartTime;
    @BindView(R.id.tvEndTime) // 结束时间
            TextView tvEndTime;
    @BindView(R.id.etMoney) // 价钱
            EditText etMoney;
    @BindView(R.id.tvTime) // 时间
            TextView tvTime;
    @BindView(R.id.tvSettlementMode) //结算方式
            TextView tvSettlementMode;
    @BindView(R.id.etJobContent) //职业介绍
            EditText etJobContent;
    @BindView(R.id.etAdditionalContent) //其他介绍
            EditText etAdditionalContent;

    private int option;
    private TimePickerView pvCustomLunar;
    private OptionsPickerView optionstyle;
    private int Type = 0;// 0、开始时间   1、结束时间   2、时间    3、结算方式
    private List<String> Time_List = new ArrayList<>();//时间
    private List<String> SettlementMode_List = new ArrayList<>();//结算方式

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_part_time_job;
    }

    @Override
    protected void initDetail() {

    }

    @Override
    protected void initTitleView() {
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("发布兼职");
        getTvRightText().setVisibility(View.VISIBLE);
        getTvRightText().setText("发布");
        getTvRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
                String name = etJobName.getText().toString();
                String address = etJobAddress.getText().toString();
                String start_date = tvStartTime.getText().toString();
                String end_date = tvEndTime.getText().toString();
                String money = etMoney.getText().toString();
                String introduce = etJobContent.getText().toString();
                String require = etAdditionalContent.getText().toString();

                String unit;
                String way;
                if ("天".equals(tvTime.getText().toString())) {
                    unit = "1";
                } else {
                    unit = "2";
                }
                if ("日结".equals(tvSettlementMode.getText().toString())) {
                    way = "1";
                } else {
                    way = "2";
                }
                if (TextUtils.isEmpty(name)) {
                    showToastShort("请输入工作名称");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    showToastShort("请输入工作地点");
                    return;
                }
                if (TextUtils.isEmpty(start_date)) {
                    showToastShort("请选择起始时间");
                    return;
                }
                if (TextUtils.isEmpty(end_date)) {
                    showToastShort("请输入结束时间");
                    return;
                }
                if (TextUtils.isEmpty(money)) {
                    showToastShort("请输入服务金额");
                    return;
                }
                if (TextUtils.isEmpty(unit)) {
                    showToastShort("请选择单位");
                    return;
                }
                if (TextUtils.isEmpty(way)) {
                    showToastShort("请选择结算方式");
                    return;
                }
                if (TextUtils.isEmpty(introduce)) {
                    showToastShort("请输入兼职介绍");
                    return;
                }
                if (TextUtils.isEmpty(require)) {
                    showToastShort("请输入兼职要求");
                    return;
                }

                PostIssue_work(token, name, address, start_date, end_date, money, unit, way, introduce, require);
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

    public void PostIssue_work(String token, String name, String address, String start_date, String end_date, String money, String unit, String way, String introduce, String require) {
        RetrofitManager.getInstance().createReq(BlogService.class).postIssue_work(token, name, address, start_date, end_date, money, unit, way, introduce, require).enqueue(new Callback<WrapperRspEntity>() {
            @Override
            public void onResponse(Call<WrapperRspEntity> call, Response<WrapperRspEntity> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    showToastShort(response.body().getMsg());
                    ReleasePartTimeJobActivity.this.finish();
                } else {
                    showToastShort(response.body().getMsg());
                }
            }
            @Override
            public void onFailure(Call<WrapperRspEntity> call, Throwable t) {

            }
        });
    }

    /**
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    private void initLunarPicker(int Type) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2069, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                switch (Type) {
                    case 0:
                        tvStartTime.setText(getTime(date));
                        break;
                    case 1:
                        tvEndTime.setText(getTime(date));
                        break;
                }
            }
        }).setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {
                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.dismiss();
                            }
                        });
                    }
                    /**
                     * 公农历切换后调整宽
                     * @param v
                     * @param yearWeight
                     * @param weight
                     */
                    private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                        ViewGroup timepicker = (ViewGroup) v.findViewById(R.id.timepicker);
                        View year = timepicker.getChildAt(0);
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timepicker.getChildCount(); i++) {
                            View childAt = timepicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                            childLp.weight = weight;
                            childAt.setLayoutParams(childLp);
                        }
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 添加选择器数据
     */
    public void initStyle(int Type) {
        optionstyle = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                switch (Type) {
                    case 2:
                        tvTime.setText(Time_List.get(options1));

                        break;
                    case 3:
                        tvSettlementMode.setText(SettlementMode_List.get(options1));
                        break;
                }
            }
        }).build();
        switch (Type) {
            case 2:
                if (Time_List != null && Time_List.size() > 0) {
                    Time_List.clear();
                }
                Time_List.add("天");
                Time_List.add("时");
                optionstyle.setPicker(Time_List);
                break;
            case 3:
                if (SettlementMode_List != null && SettlementMode_List.size() > 0) {
                    SettlementMode_List.clear();
                }
                SettlementMode_List.add("日结");
                SettlementMode_List.add("完工结");
                optionstyle.setPicker(SettlementMode_List);
                break;
        }
    }

    @OnClick({R.id.tvStartTime, R.id.tvEndTime, R.id.etMoney, R.id.llTime, R.id.llSettlementMode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvStartTime:
                initLunarPicker(0);//0、开始时间
                pvCustomLunar.show();
                break;
            case R.id.tvEndTime:
                initLunarPicker(1);//1、结束时间
                pvCustomLunar.show();
                break;
            case R.id.etMoney:
                break;
            case R.id.llTime:
                initStyle(2);//2、时间
                optionstyle.show();
                break;
            case R.id.llSettlementMode://3、结算方式
                initStyle(3);
                optionstyle.show();
                break;
        }
    }
}
