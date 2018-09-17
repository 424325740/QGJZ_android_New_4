package com.qigaikj.parttimejob.bean;

/**
 * Created by Administrator on 2017/11/4/004.
 * 用户登录信息
 */

public class Login {

    /**
     * token : 02169bf024d1af874fb0dd2aabd65dfe
     */

    private String token;
    private String rctoken;
    private String userId;
    private String vip;
    private String integral;
    private String ticket;

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getIntegral() {
        return integral;
    }

    public String getTicket() {
        return ticket;
    }

    public String getVip() {
        return vip;
    }

    public String getRctoken() {
        return rctoken;
    }

    public void setRctoken(String rctoken) {
        this.rctoken = rctoken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
