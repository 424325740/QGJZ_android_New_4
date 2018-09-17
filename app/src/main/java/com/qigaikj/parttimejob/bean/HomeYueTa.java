package com.qigaikj.parttimejob.bean;

import java.util.List;

public class HomeYueTa {

    /**
     *
     * 首页约TA
     *
     * uid（用户id）
     uname（用户昵称）
     logo（用户头像）
     user_skill（用户技能）
     gender（性别 1男 2女）
     state（实名认证状态 1已认证 2未认证）
     phone_state（手机号认证状态 1已认证 2未认证）
     wx_state（微信认证状态 1已认证 2未认证）
     skill_pic（技能照片）
     introduce（技能介绍）
     *
     * */

    public String uid;
    public String uname;
    public String logo;
    public List<String> user_skill;
    public String gender;
    public String state;
    public String phone_state;
    public String wx_state;
    public List<String> skill_pic;
    public String introduce;
}
