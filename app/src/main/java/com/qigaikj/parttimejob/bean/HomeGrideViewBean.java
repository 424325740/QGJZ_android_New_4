package com.qigaikj.parttimejob.bean;

import java.util.List;

/**
 * author:created by liangliang on 2018/6/3/003
 * function:
 */

public class HomeGrideViewBean {
    /**
     * tid : 12
     * tname : 瑜伽
     * pic : http://ceshi.qigaikj.com/Public/file/2018-05/5b0cf8a4c1e1b.png
     */

    private String tid;
    private String tname;
    private String pic;
    /**
     * ret : 1
     * message : 请求成功
     * data : [{"tid":"89","tname":"城市名片","pic":"http://cdn.ceshi.qigaikj.com/2018/08/43716636.png"},{"tid":"90","tname":"人脉服务","pic":"http://cdn.ceshi.qigaikj.com/2018/08/25638919.png"},{"tid":"91","tname":"白领兼职","pic":"http://cdn.ceshi.qigaikj.com/2018/08/93863949.png"},{"tid":"92","tname":"蓝领服务","pic":"http://cdn.ceshi.qigaikj.com/2018/08/97083602.png"},{"tid":"93","tname":"学生兼职","pic":"http://cdn.ceshi.qigaikj.com/2018/08/76394594.png"},{"tid":"94","tname":"美丽约","pic":"http://cdn.ceshi.qigaikj.com/2018/08/90881450.png"},{"tid":"95","tname":"丽人时尚","pic":"http://cdn.ceshi.qigaikj.com/2018/08/88019827.png"},{"tid":"96","tname":"休闲娱乐","pic":"http://cdn.ceshi.qigaikj.com/2018/08/3214411.png"},{"tid":"97","tname":"居家生活","pic":"http://cdn.ceshi.qigaikj.com/2018/08/31445834.png"},{"tid":"100","tname":"更多","pic":"http://cdn.ceshi.qigaikj.com/2018/08/95931544.png"}]
     */

    private int ret;
    private String message;
    private List<HomeGrideViewBean> data;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HomeGrideViewBean> getData() {
        return data;
    }

    public void setData(List<HomeGrideViewBean> data) {
        this.data = data;
    }
}
