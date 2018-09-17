package com.qigaikj.parttimejob.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;


/**
 * 作者：庞宇锋
 * 功能：单例模式封装Retrofit
 * 时间：2017/8/14.
 */

public class RetrofitManager {
    //    public static final String BasUrl="http:www.i91share.com/api/";
//    public static final String BasUrl="http://www.haoshengsd.com/api/";
    public static final String QQID = "1105947309";
    public static final String QQKEY = "LyER1LV6ROcm11dV";
    public static final String WXID = "wx838d6c655fba1d1b";
//    public static final String BasUrl = "http://qigaikj.com/";
    public static final String BasUrl = "http://ceshi.qigaikj.com/";

    public static final String BaseImageUrl = "http://qigaikj.com/Public/";
    public static final String LOGINURL="http://qigaikj.com/Public/agreement/users.html";
    public static final String FABUNURL="http://qigaikj.com/Public/agreement/official.html";
    public static final String VIPURL="http://qigaikj.com/Public/agreement/member.html";
    public static RetrofitManager retrofitManager;

    public static int CODE=1;

    private Retrofit retrofit;

    private RetrofitManager() {
        initRetrofit();
    }

    public static synchronized RetrofitManager getInstance() {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
        }
        return retrofitManager;
    }

    private void initRetrofit() {
        //Log拦截器
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (true) {
            builder.addInterceptor(new LoggingInterceptor());
        }
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();//使用 gson coverter，统一日期请求格式

        OkHttpClient okHttpClient = builder.build();

        retrofit = new Retrofit.Builder().baseUrl(BasUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public <T> T createReq(Class<T> reqServer) {
        return retrofit.create(reqServer);
    }
}
