package com.qigaikj.parttimejob.bean;

/**
 * Created by Administrator on 2017/11/23/023.
 * 最新回复
 */

public class NewMessage {

    /**
     * num : 0
     * logo :
     */

    private String num;
    private String logo;
    /**
     * nid : 1
     * uname : 星空下的张好好
     * content : 人哦啊
     * date : 刚刚
     */

    private String nid;
    private String uname;
    private String content;
    private String date;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
