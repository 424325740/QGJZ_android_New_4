package com.qigaikj.parttimejob.bean;

/**
 * author:created by liangliang on 2018/6/11/011
 * function:
 */

public class NearListBean {


    /**
     * ret : 1
     * message : 请求成功
     * data : [{"tid":"12","tname":"瑜伽"},{"tid":"13","tname":"跑步"},{"tid":"14","tname":"游泳"},{"tid":"15","tname":"羽毛球"},{"tid":"16","tname":"乒乓球"}]
     */
    /**
     * tid : 12
     * tname : 瑜伽
     */

    private String tid;
    private String tname;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }
}
