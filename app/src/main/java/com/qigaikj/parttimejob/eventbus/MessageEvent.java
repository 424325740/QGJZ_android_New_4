package com.qigaikj.parttimejob.eventbus;

import com.qigaikj.parttimejob.bean.HomeSearchBean;

import java.util.List;

public class MessageEvent {
    private List<HomeSearchBean> homeSearchBeans;
    private String str;

    public MessageEvent(List<HomeSearchBean> homeSearchBeans) {
        this.homeSearchBeans=homeSearchBeans;
    }


    public List<HomeSearchBean> getHomeSearchBeans() {
        return homeSearchBeans;
    }

    public void setHomeSearchBeans(List<HomeSearchBean> homeSearchBeans) {
        this.homeSearchBeans = homeSearchBeans;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
