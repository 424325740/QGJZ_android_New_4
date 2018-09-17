package com.qigaikj.parttimejob.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/11/27/027.
 * 微信支付时所需参数
 */

public class WeiXing {


    /**
     * appid : wx838d6c655fba1d1b
     * partnerid : 1447865002
     * prepayid : wx201711271449363847c0acb50604941726
     * package : Sign=WXPay
     * noncestr : DgBTXNYRUlCHGJdbdrWJtsQZQ3H5kGsU
     * timestamp : 1511765376
     * sign : 2659B6703745125AE470EF466D34ED70
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    @SerializedName("package")
    private String packageX;
    private String noncestr;
    private int timestamp;
    private String sign;
    private String result_code;
    private String order;

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_code() {
        return result_code;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
