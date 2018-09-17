package com.qigaikj.parttimejob.bean;


/**
 *
 * 关注的用户----------------------------------------
             * uid（用户id）
             uname（用户昵称）
             logo（用户头像）
             gender（性别 1男 2女）
             state（实名认证状态 1已认证 2未认证）
             user_skill（用户技能）
             flag（1已关注 2未关注）
 关注的兼职-----------------------------------------------
             wid（兼职id）
             uname（用户昵称）
             logo（用户头像）
             state（实名认证状态 1已认证 2未认证）
             name（兼职名称）
             address（工作地址）
             money（薪资）
             unit（单位 1天 2小时）
             flag（1已关注 2未关注）
 *
 * */
public class FollowBean {
    /**共有参数*/
    public String uname;
    public String state;
    public String flag;

    /**关注用户（私有参数）*/
    public String uid;
    public String gender;
    public String user_skill;

    /**关注兼职（私有参数）*/
    public String Wid;
    public String logo;
    public String name;
    public String address;
    public String money;
    public String unit;
}
