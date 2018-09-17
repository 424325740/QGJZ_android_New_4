package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.ClassifyForAll;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 发布约TA
 */

public class ReleaseYueTaActivity extends BaseActivity {

    @BindView(R.id.llSkillType)//技能类型
            LinearLayout llSkillType;
    @BindView(R.id.tvSkillType)
    TextView tvSkillType;
    @BindView(R.id.llSkillName)//技能名称
            LinearLayout llSkillName;
    @BindView(R.id.tvSkillName)
    TextView tvSkillName;
    @BindView(R.id.llSkillRequirement)//技能要求
            LinearLayout llSkillRequirement;
    @BindView(R.id.etSkillRequirement)
    EditText etSkillRequirement;
    @BindView(R.id.llSkillSex)//性别男女
            LinearLayout llSkillSex;
    @BindView(R.id.tvSkillSex)
    TextView tvSkillSex;
    @BindView(R.id.tvLineUp)//线上服务
            ImageView tvLineUp;
    @BindView(R.id.tvLineDown)//线下服务
            ImageView tvLineDown;
    @BindView(R.id.linshi)
    LinearLayout linshi;
    @BindView(R.id.tian)
    LinearLayout tian;
    @BindView(R.id.tiem)
    TextView tiem;
    @BindView(R.id.danwei)
    TextView danwei;

    private int option;
    private String Type = "0";// 0、技能类型   1、技能名称   2、性别
    private OptionsPickerView optionstyle;
    private List<ClassifyForAll> style_list_All = new ArrayList<>();//技能数据
    private List<String> style_list = new ArrayList<>();//技能类型
    private List<String> skill_name_list = new ArrayList<>();//技能名称
    private List<String> skill_name_listid = new ArrayList<>();//技能名称id
    private List<String> skill_name_listpic = new ArrayList<>();//技能名称图标
    private List<String> sex_list = new ArrayList<>();//男女无
    private List<String> Time_List = new ArrayList<>();//时间
    private String isSelect;
    private boolean isChecked = false;
    private boolean flag = false;
    private TextView textView;

    /**
     * 发布约TA
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_release_yueta;
    }

    @Override
    protected void initDetail() {
        ObtionClassifyDataNetWork();
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
                        textView = new TextView(ReleaseYueTaActivity.this);
                        textView.setText(skill_name_listid.get(options1));
                        skill_name_listpic.get(options1);
                        break;
                    case "2":
                        tvSkillSex.setText(sex_list.get(options1));
                        break;
                    case "3":
                        danwei.setText(Time_List.get(options1));
                        break;
                    case "4":
                        tiem.setText(Time_List.get(options1));
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
                if (skill_name_list != null && skill_name_list.size() > 0) {
                    skill_name_list.clear();
                }
                /**技能名称*/
                for (int i = 0; i < style_list_All.get(option).getErji().size(); i++) {
                    skill_name_list.add(style_list_All.get(option).getErji().get(i).tname);
                    skill_name_listid.add(style_list_All.get(option).getErji().get(i).tid);
                    skill_name_listpic.add(style_list_All.get(option).getErji().get(i).pic);
                }
                optionstyle.setPicker(skill_name_list);
                break;
            case "2":
                if (sex_list != null && sex_list.size() > 0) {
                    sex_list.clear();
                }
                sex_list.add("男");
                sex_list.add("女");
                sex_list.add("不限");
                optionstyle.setPicker(sex_list);
                break;
            case "3":
                if (Time_List != null && Time_List.size() > 0) {
                    Time_List.clear();
                }
                Time_List.add("天");
                Time_List.add("时");
                optionstyle.setPicker(Time_List);
                break;
            case "4":
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
     * 获取分类数据
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

    /**
     * 发布数据
     */
    public void PostDemand(String token, String type, String s, String require, String gender, String serve, String lng, String lat) {
        RetrofitManager.getInstance().createReq(BlogService.class).postDemand(token, type, s, require, gender, serve, lng, lat).enqueue(new Callback<WrapperRspEntity>() {
            @Override
            public void onResponse(Call<WrapperRspEntity> call, Response<WrapperRspEntity> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    showToastShort(response.body().getMsg());
                    ReleaseYueTaActivity.this.finish();
                } else {
                    showToastShort(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity> call, Throwable t) {

            }
        });
    }

    @Override
    protected void initTitleView() {
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("约TA");
        getTvRightText().setVisibility(View.VISIBLE);
        getTvRightText().setText("发布");
        getTvRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
                String type = tvSkillType.getText().toString();
                String name = tvSkillName.getText().toString();
                String s = textView.getText().toString();
                String require = etSkillRequirement.getText().toString();
                String gender = tvSkillSex.getText().toString();
                String serve = isSelect;
                String lng = sharedPreferencesHelper.getString(SharedPreferencesHelper.LONG, "");
                String lat = sharedPreferencesHelper.getString(SharedPreferencesHelper.LAT, "");

                if (TextUtils.isEmpty(token)) {
                    showToastShort("token不能为空");
                    return;
                }
                if (TextUtils.isEmpty(type)) {
                    showToastShort("请选择技能类型");
                    return;
                }
                if (TextUtils.isEmpty(s)) {
                    showToastShort("请选择技能名称");
                    return;
                }
                if (TextUtils.isEmpty(gender)) {
                    showToastShort("请选择技性别");
                    return;
                }
                /*if (TextUtils.isEmpty(serve)) {
                    showToastShort("请选择线上或线下");
                    return;
                }*/
                PostDemand(token, type, s, require, gender, serve, lng, lat);
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

    @OnClick({R.id.llSkillType, R.id.llSkillName, R.id.llSkillRequirement, R.id.llSkillSex, R.id.llLineUp, R.id.llLineDown, R.id.danwei, R.id.tiem})
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
            case R.id.llSkillSex:
                Type = "2";
                initStyle();
                optionstyle.show();
                break;
            case R.id.danwei:
                Type = "3";
                initStyle();
                optionstyle.show();
                break;
            case R.id.tiem:
                Type = "4";
                initStyle();
                optionstyle.show();
                break;
            case R.id.llLineUp:
                if (isChecked == false) {
                tvLineUp.setImageResource(R.mipmap.btn_select_pre);
                tvLineDown.setImageResource(R.mipmap.btn_select_nor);
                linshi.setVisibility(View.VISIBLE);
                tian.setVisibility(View.GONE);
                    isSelect = "1";
//                    isChecked = true;
            } else {
                isChecked = false;
            }
                break;
            case R.id.llLineDown:
                if (flag == false) {
                    tvLineUp.setImageResource(R.mipmap.btn_select_nor);
                    tvLineDown.setImageResource(R.mipmap.btn_select_pre);
                    linshi.setVisibility(View.GONE);
                    tian.setVisibility(View.VISIBLE);
                    isSelect = "2";
//                    flag = true;
                } else {
                    flag = false;
                }

                break;
        }
    }
}
