package com.qigaikj.parttimejob.bean;

/**
 * Created by Administrator on 2017/11/4/004.
 * 保存用户信息
 */

public class UserInfo {

    /**
     *
     * 测试token：6de34cdd042b6b7c6af3de735c1cd3fb
     *
     *
     * uid（用户id）
     uname（用户昵称）
     logo（用户头像）
     gender（性别 1男 2女）
     age（年龄）
     money（钱包余额）
     coupon（优惠券）
     income（收入）
     advert（广告）
     aid（广告id）
     pic（广告图片）
     */

    public String uid;
    public String uname;
    public String logo;
    public String gender;
    public String age;
    public String money;
    public String coupon;
    public String income;
    public UserInfoItem advert;

    public class UserInfoItem {
        public String aid;
        public String pic;
    }
}
