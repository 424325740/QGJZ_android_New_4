package com.qigaikj.parttimejob.view.userinfodetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.UserInfoDetailOneAdapter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.FollowBean;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.qigaikj.parttimejob.base.BaseAplcation.sharedPreferencesHelper;

public class UserInfoDetailOne extends Fragment{

    Unbinder unbinder;
    View view;

    private List<String> FollowPeopleList = new ArrayList<>();

    private UserInfoDetailOneAdapter adapter;

    private TextView tvRealName; //实名
    private TextView tvSkill; //技能
    private TextView tvPhone; //手机
    private TextView tvWeChat; //微信

    private RecyclerView rvSkill; //技能
    private RecyclerView rvDemand; //需求

    private EditText etUserIntroduce; //自我介绍
    private TextView tvUserId; //用户ID
    private TextView tvUserJob; //用户职业
    private TextView tvUserBirthday; //用户生日
    private TextView tvUserAddress; //用户地址
    private TextView tvUserHeight; //用户身高
    private TextView tvUserSincerityNum; //用户诚信值

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_info_detail_one, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvSkill = (RecyclerView) view.findViewById(R.id.rvSkill);

        tvRealName = (TextView) view.findViewById(R.id.tvRealName);
        rvSkill = (RecyclerView) view.findViewById(R.id.rvSkill);
        rvDemand = (RecyclerView) view.findViewById(R.id.rvDemand);

        etUserIntroduce = (EditText) view.findViewById(R.id.etUserIntroduce);
        tvUserId = (TextView) view.findViewById(R.id.tvUserId);
        tvUserJob = (TextView) view.findViewById(R.id.tvUserJob);
        tvUserBirthday = (TextView) view.findViewById(R.id.tvUserBirthday);
        tvUserAddress = (TextView) view.findViewById(R.id.tvUserAddress);
        tvUserHeight = (TextView) view.findViewById(R.id.tvUserHeight);
        tvUserSincerityNum = (TextView) view.findViewById(R.id.tvUserSincerityNum);

        for (int i = 0; i < 3; i++) {
            FollowPeopleList.add("测试");
        }
        initAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     *
     * 获取我的关注
     * state（1关注的人 2关注的兼职）
     *
     * */
    private void getData() {
        String token = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getMyCollect(token, "1", "1", "10").enqueue(new Callback<WrapperRspEntity<List<FollowBean>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<FollowBean>>> call, Response<WrapperRspEntity<List<FollowBean>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<List<FollowBean>>> call, Throwable t) {

            }
        });
    }
    /**
     * 设置RecyclerView属性
     */
    private void initAdapter() {
        rvSkill.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UserInfoDetailOneAdapter(getActivity(), R.layout.user_info_detail_one_item, FollowPeopleList);
        adapter.openLoadAnimation();
        rvSkill.setAdapter(adapter);//设置adapter
        //设置item点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }
}
