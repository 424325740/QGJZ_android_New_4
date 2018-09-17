package com.qigaikj.parttimejob.bean;

import java.io.Serializable;
import java.util.List;

public class DingdanYiYaoqingBean implements Serializable{

    /**
     * ret : 1
     * message : 请求成功
     * data : [{"uid":"1","uname":"qgid_1","logo":"http://ceshi.qigaikj.com/Public/file/20171109163437.png","gender":"1","state":"2","phone_state":"1","wx_state":"2","serve":null,"price":null,"unit":null,"introduce":null,"addtime":"2018-09-05 14:01:16","sincerity_gold":null},{"uid":"2","uname":"qgid_2","logo":"http://ceshi.qigaikj.com/Public/file/20171109163437.png","gender":"1","state":"2","phone_state":"1","wx_state":"2","serve":null,"price":null,"unit":null,"introduce":null,"addtime":"2018-09-05 14:01:42","sincerity_gold":null}]
     */

    private int ret;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * uid : 1
         * uname : qgid_1
         * logo : http://ceshi.qigaikj.com/Public/file/20171109163437.png
         * gender : 1
         * state : 2
         * phone_state : 1
         * wx_state : 2
         * serve : null
         * price : null
         * unit : null
         * introduce : null
         * addtime : 2018-09-05 14:01:16
         * sincerity_gold : null
         */

        private String uid;
        private String uname;
        private String logo;
        private String gender;
        private String state;
        private String phone_state;
        private String wx_state;
        private Object serve;
        private Object price;
        private Object unit;
        private Object introduce;
        private String addtime;
        private Object sincerity_gold;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPhone_state() {
            return phone_state;
        }

        public void setPhone_state(String phone_state) {
            this.phone_state = phone_state;
        }

        public String getWx_state() {
            return wx_state;
        }

        public void setWx_state(String wx_state) {
            this.wx_state = wx_state;
        }

        public Object getServe() {
            return serve;
        }

        public void setServe(Object serve) {
            this.serve = serve;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
            this.unit = unit;
        }

        public Object getIntroduce() {
            return introduce;
        }

        public void setIntroduce(Object introduce) {
            this.introduce = introduce;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public Object getSincerity_gold() {
            return sincerity_gold;
        }

        public void setSincerity_gold(Object sincerity_gold) {
            this.sincerity_gold = sincerity_gold;
        }
    }
}
