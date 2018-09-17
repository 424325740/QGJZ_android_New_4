package com.qigaikj.parttimejob.bean;

/**
 * Created by Administrator on 2017/11/13/013.
 * 保存好友信息
 */

public class FriendDetails {

    /**
     * nickname : qgid_239
     * logo : http://qigaikj.com/Public/file/2017-09/59b8fb0a5707c.jpeg
     * tel : 13520274711
     * name : 未认证
     * level : Lv0
     * empirical_value : 10
     * flag : 2
     * state : 2
     */
    private String vip;
    private String nickname;
    private String logo;
    private String tel;
    private String name;
    private String level;
    private int empirical_value;
    private int flag;
    private int state;
    private String issue_sum;//发布兼职次数
    private String work_sum;//兼职次数

    public void setIssue_sum(String issue_sum) {
        this.issue_sum = issue_sum;
    }

    public void setWork_sum(String work_sum) {
        this.work_sum = work_sum;
    }

    public String getIssue_sum() {
        return issue_sum;
    }

    public String getWork_sum() {
        return work_sum;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getVip() {
        return vip;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getEmpirical_value() {
        return empirical_value;
    }

    public void setEmpirical_value(int empirical_value) {
        this.empirical_value = empirical_value;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
