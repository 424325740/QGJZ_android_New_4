package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.adapter.Home_fram1_Adapter;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.PartTime;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.ActivityUtils;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.view.PtClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/11/21/021.
 * 搜索职位
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.et_search_clear)
    PtClearEditText etSearchClear;
    @BindView(R.id.lv_search_list)
    ListView lvSearchList;
    @BindView(R.id.tv_search_sousuo)
    TextView tvSearchSousuo;
    private Home_fram1_Adapter home_fram1_adapter;
    private List<PartTime> partTimeList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initDetail() {
        etSearchClear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//                filterData(s.toString());
//                if (s!=null&&!s.equals("")&&s.length()>1){
//                    getSearch(s.toString());
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSearchClear.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        getSearch(etSearchClear.getText().toString().trim());
                        break;

                }
                return false;
            }
        });
        partTimeList = new ArrayList<>();
        home_fram1_adapter = new Home_fram1_Adapter(partTimeList);
        lvSearchList.setAdapter(home_fram1_adapter);
        //点击跳转到详情
        lvSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("posId", partTimeList.get(position).getId());
//                ActivityUtils.startActivityForBundleData(SearchActivity.this, PositionDetailsActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initTitleView() {
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTvTextTitle().setVisibility(View.VISIBLE);
        getTvTextTitle().setText("搜索");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private int pager = 1;

    /**
     * 搜索
     */
    public void getSearch(String name) {
        RetrofitManager.getInstance().createReq(BlogService.class).getSearch(name, "20", String.valueOf("1")).enqueue(new Callback<WrapperRspEntity<List<PartTime>>>() {
            @Override
            public void onResponse(Call<WrapperRspEntity<List<PartTime>>> call, Response<WrapperRspEntity<List<PartTime>>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    partTimeList.clear();
                    partTimeList.addAll(response.body().getData());
                    home_fram1_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<WrapperRspEntity<List<PartTime>>> call, Throwable t) {

            }

        });
    }

    @OnClick(R.id.tv_search_sousuo)
    public void onViewClicked() {
        getSearch(etSearchClear.getText().toString().trim());
    }
}
