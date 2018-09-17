package com.qigaikj.parttimejob.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.Deposit;
import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.util.RetrofitCallback;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

import static com.qigaikj.parttimejob.R.*;

/**
 * Created by Administrator on 2017/12/5/005.
 * 绑定微信与支付宝账户
 */

public class BindingActivity extends BaseActivity {
    @BindView(id.et_binding_name)
    EditText etBindingName;
    @BindView(id.et_binding_zh)
    EditText etBindingZh;
    @BindView(id.tv_binding_comment)
    TextView tvBindingComment;
    private boolean isName = false;
    private boolean isZh = false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_binding;
    }

    @Override
    protected void initDetail() {
        etBindingName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    isName = true;
                } else {
                    isName = false;
                }
                if (isName && isZh) {
                    tvBindingComment.setBackgroundResource(R.drawable.bg_binding_comment_yes);
                } else {
                    tvBindingComment.setBackgroundResource(R.drawable.bg_binding_comment_no);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etBindingZh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    isZh = true;
                } else {
                    isZh = false;
                }
                if (isName && isZh) {
                    tvBindingComment.setBackgroundDrawable(getResources().getDrawable(drawable.bg_binding_comment_yes));
                } else {
                    tvBindingComment.setBackgroundDrawable(getResources().getDrawable(drawable.bg_binding_comment_no));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initTitleView() {
        getTvTextTitle().setText("绑定支付宝");
        getTvTextTitle().setVisibility(View.VISIBLE);
        getIvLeftImage().setVisibility(View.VISIBLE);
        getIvLeftImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(id.tv_binding_comment)
    public void onViewClicked() {
        getBindingZfb();
    }

    /**
     * 绑定支付宝账号
     */
    public void getBindingZfb() {
        String accounts = etBindingZh.getText().toString().trim();
        String name = etBindingName.getText().toString().trim();
        if (name == null || name.equals("")) {
            return;
        }
        if (accounts == null || accounts.equals("")) {
            return;
        }
        String tocken = sharedPreferencesHelper.getString(SharedPreferencesHelper.TOKEN, "");
        if (tocken == null || tocken.equals("")) {
            return;
        }
        RetrofitManager.getInstance().createReq(BlogService.class).getBinDing(tocken, accounts, name).enqueue(new RetrofitCallback<Deposit>(this) {
            @Override
            public void onRes(Call<WrapperRspEntity<Deposit>> call, Response<WrapperRspEntity<Deposit>> response) {
                if (response.body().getStatus() == RetrofitManager.CODE) {
                    showToastShort("绑定成功");
                    finish();
                }
            }

            @Override
            public void onErr(Call<WrapperRspEntity<Deposit>> call, Throwable t) {

            }
        });
    }
}
