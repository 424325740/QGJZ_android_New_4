package com.qigaikj.parttimejob.activity.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qigaikj.parttimejob.R;
import com.qigaikj.parttimejob.base.BaseActivity;
import com.qigaikj.parttimejob.bean.BlogService;
import com.qigaikj.parttimejob.bean.XiuGaiBean;
import com.qigaikj.parttimejob.util.RetrofitManager;
import com.qigaikj.parttimejob.util.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Xiugaipassword extends BaseActivity {
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.newpass)
    EditText newpass;
    @BindView(R.id.querenpass)
    EditText querenpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_xiugai;
    }

    @Override
    protected void initDetail() {

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
        getTvTextTitle().setText("修改密码");
    }

    public void getDate(String pwd,String password) {
        String token = SharedPreferencesHelper.getInstance(Xiugaipassword.this).getString(SharedPreferencesHelper.TOKEN, "");
        RetrofitManager.getInstance().createReq(BlogService.class).getXiugaipassword(token,pwd,password ).enqueue(new Callback<XiuGaiBean>() {
            @Override
            public void onResponse(Call<XiuGaiBean> call, Response<XiuGaiBean> response) {
                String message = response.body().getMessage();
                Toast.makeText(Xiugaipassword.this,message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<XiuGaiBean> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.wancheng)
    public void onViewClicked() {
        Editable text = pass.getText();
        Editable text1 = newpass.getText();
        Editable text2 = querenpass.getText();
        String pwd=text+"";
        String password =text1+"";
        String newpaw=text2+"";
        if (pwd == null || pwd.equals("")) {
            showToastShort("请输入密码");
            return;
        }
        if (password == null || password.equals("")) {
            showToastShort("请输入新的密码");
            return;
        }
        if (newpaw == null || newpaw.equals("")||newpaw==password) {
            showToastShort("两次密码不一致");
            return;
        }
        getDate(pwd,password);
    }
}
