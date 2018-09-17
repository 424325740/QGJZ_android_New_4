package com.qigaikj.parttimejob.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/1/001.
 * 我的钱包页面明细
 */

public class Money {

    /**
     * money : 0.01
     * detail : [{"mid":"3","type":"1","time":"2017-12-01 11:05:49","money":"0.01","state":"1","pay":"2"}]
     */

    private String money;
    private List<DetailBean> detail;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public static class DetailBean {
        /**
         * mid : 3
         * type : 1
         * time : 2017-12-01 11:05:49
         * money : 0.01
         * state : 1
         * pay : 2
         */

        private String mid;
        private String type;
        private String time;
        private String money;
        private String state;
        private String pay;
        private String title;

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }
    }
}
