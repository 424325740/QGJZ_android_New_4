package com.qigaikj.parttimejob.bean;

/**
 * Created by Administrator on 2017/11/16/016.
 * 保存后台返回第三方登陆信息
 */

public class ThirLogin {

    /**
     * token : 74a12da10e455d68cb3eb580ebea9946
     * userId : 239
     * rctoken : qpIsmwg/6fRJH+ObIVBpxfPvP9w01de/g+d/VGU5NhLNMgyn/99H3DCYrb8zViRb+Yj308XCpFdgVB601fDEmQ==
     * flag : 2
     */

    private String token;
    private String userId;
    private String rctoken;
    private String flag;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRctoken() {
        return rctoken;
    }

    public void setRctoken(String rctoken) {
        this.rctoken = rctoken;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
