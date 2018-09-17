package com.qigaikj.parttimejob.bean;

import java.util.List;

public class SearchJiluBean {

    /**
     * ret : 1
     * message : 请求成功
     * data : {"history":["测试数据","看电视","数据","看电影","测试","你猜","兼职","TA约","约TA","游泳","游泳","游泳","滑雪","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳","游泳"],"yueta":["游泳","健身","滑雪","美甲","家教"],"work":["运营","产品运营","打麻将","123","文案策划"]}
     */

    private int ret;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> history;
        private List<String> yueta;
        private List<String> work;

        public List<String> getHistory() {
            return history;
        }

        public void setHistory(List<String> history) {
            this.history = history;
        }

        public List<String> getYueta() {
            return yueta;
        }

        public void setYueta(List<String> yueta) {
            this.yueta = yueta;
        }

        public List<String> getWork() {
            return work;
        }

        public void setWork(List<String> work) {
            this.work = work;
        }
    }
}
