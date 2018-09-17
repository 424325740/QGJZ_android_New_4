package com.qigaikj.parttimejob.view.follow;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.FollowAdapter;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.FollowBean;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;
import com.qigaikj.parttimejob.view.SwipeRefreshView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowItemTwo {

    private Context _context;
    private List<FollowBean> FollowList;
    private List<FollowBean> AllFollowList = new ArrayList<>();
    private SwipeRefreshView SwipeRefresh;

    private int pageIndex = 1;
    private int pageSumNew = 0; // 最新总页数
    private RecyclerView rvFollowPeople;
    private RelativeLayout rlDefault;
    private TextView tvDefaultMention;

    private FollowAdapter adapter;

    public View CancelGetview(Context context) {
        _context = context;
        View view =((Activity)context).getLayoutInflater().inflate(R.layout.follow_item_one, null);
        initView(view);
        getData();
        return view;
    }

    private void initView(View view) {
        rvFollowPeople = (RecyclerView) view.findViewById(R.id.rvFollowPeople);
        SwipeRefresh = (SwipeRefreshView) view.findViewById(R.id.SwipeRefresh);
        SwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark, android.R.color.holo_purple);
        // 手动调用,通知系统去测量
        SwipeRefresh.measure(0, 0);
        SwipeRefresh.setRefreshing(false);
        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AllFollowList != null && AllFollowList.size() > 0) {
                    AllFollowList.clear();
                    getData();
                } else {
                    getData();
                }
            }
        });

        //默认提示
        rlDefault = (RelativeLayout) view.findViewById(R.id.rlDefault);
        tvDefaultMention = (TextView) view.findViewById(R.id.tvDefaultMention);
        tvDefaultMention.setText("暂无数据");
    }

    /**
     *
     * 获取我的关注
     * state（1关注的人 2关注的兼职）
     *
     * */
    private void getData() {
        String token = SharedPreferencesHelper.getInstance(_context).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getMyCollect(token, "2", "1", "10").enqueue(new Callback<WrapperRspEntity<List<FollowBean>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<FollowBean>>> call, Response<WrapperRspEntity<List<FollowBean>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    if (response.body().getData() != null && response.body().getData().size() > 0) {
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        if (SwipeRefresh.isRefreshing()) {
                            SwipeRefresh.setRefreshing(false);
                        }
                        AllFollowList.addAll(response.body().getData());
                        //通知适配器刷新数据
                        adapter = new FollowAdapter(_context, R.layout.follow_item_one_details, AllFollowList);
                        initAdapter();
                        rlDefault.setVisibility(View.GONE);
                    } else {
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        if (SwipeRefresh.isRefreshing()) {
                            SwipeRefresh.setRefreshing(false);
                        }
                        rlDefault.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(_context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    rvFollowPeople.setVisibility(View.GONE);
                    rlDefault.setVisibility(View.VISIBLE);
                    Toast.makeText(_context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
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
        rvFollowPeople.setLayoutManager(new LinearLayoutManager(_context));
        adapter.openLoadAnimation();
        rvFollowPeople.setAdapter(adapter);//设置adapter
        //设置item点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }
}
