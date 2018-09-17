package com.qigaikj.parttimejob.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16/016.
 * 保存微信返回的用户信息
 */

public class WXUser implements Serializable {

    /**
     * access_token : 3_H1gMBInWwzz2tBCfdD5URdrVAftSOAEcyCgoKQA3XvyJ4KcfaSqtkaZW4mvx72Adx7JwdDFZ8_LYcuYK6GqE8WpPthS1z_3Dm6RM3RUkfK8
     * expires_in : 7200
     * refresh_token : 3_qRXitvkl0Ewl4Hve4j8AXqx9rTwt5QHdNHcVLIHM7hnA-3ceaFsYVIHrBU-BBJh8qSkPPmjbi-KRe9H7BmY-EfYlBtnCX4GOZTjF7CSVY7A
     * openid : oDJztwCNKhFCMdeQvhrQByWRN5-E
     * scope : snsapi_userinfo
     * unionid : oEtDHw1wfv_VCcQfYHOzIcnr83-E
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
    /**
     * openid : 普通用户的标识，对当前开发者帐号唯一
     * nickname : 普通用户昵称
     * sex : 普通用户性别，1为男性，2为女性
     * language :语言（官方文档未写，暂时不知有什么用）
     * city : 普通用户个人资料填写的城市
     * province : 普通用户个人资料填写的省份
     * country : 国家，如中国为CN
     * headimgurl : 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
     * privilege : 用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
     * unionid : 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
     */

    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<?> privilege;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }


    public List<?> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }
}
