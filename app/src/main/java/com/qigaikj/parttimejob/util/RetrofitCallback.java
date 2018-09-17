package com.qigaikj.parttimejob.util;

import android.content.Context;

import com.qigaikj.parttimejob.bean.WrapperRspEntity;
import com.qigaikj.parttimejob.view.LoadDlialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/11/28/028.
 * 网络接口返回
 */

public abstract class RetrofitCallback<T> implements Callback<WrapperRspEntity<T>> {
    public Context mcontext;

    public RetrofitCallback(Context context) {
        this.mcontext = context;
        LoadDlialog.showLoadDialog(mcontext, "", true);
    }

    @Override
    public void onResponse(Call<WrapperRspEntity<T>> call, Response<WrapperRspEntity<T>> response) {
        LoadDlialog.dismissLoadDialog();
        onRes(call, response);
        LogUtils.i("在这里返回");
    }

    @Override
    public void onFailure(Call<WrapperRspEntity<T>> call, Throwable t) {
        LoadDlialog.dismissLoadDialog();
        onErr(call, t);
    }

    public abstract void onRes(Call<WrapperRspEntity<T>> call, Response<WrapperRspEntity<T>> response);

    public abstract void onErr(Call<WrapperRspEntity<T>> call, Throwable t);
}
