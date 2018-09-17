package com.qigaikj.parttimejob.bean;

import java.util.List;

public class MyXuqiuBean {

    /**
     * ret : 1
     * message : 请求成功
     * data : [{"kid":"48","name":"挂号","state":"1","require":"121","gender":"1","stat":"1","serve":"0"},{"kid":"47","name":"城市名片","state":"2","require":"1","gender":"1","stat":"1","serve":"0"},{"kid":"46","name":"城市名片","state":"1","require":"啥都会","gender":"3","stat":"1","serve":"0"},{"kid":"45","name":"居家生活","state":"1","require":"1","gender":"3","stat":"1","serve":"0"},{"kid":"44","name":"热门推荐","state":"1","require":"1","gender":"3","stat":"1","serve":"0"},{"kid":"43","name":"热门推荐","state":"1","require":"213","gender":"3","stat":"1","serve":"0"},{"kid":"26","name":"美甲","state":"1","require":"牛逼点","gender":"3","stat":"1","serve":"0"}]
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

    public static class DataBean {
        /**
         * kid : 48
         * name : 挂号
         * state : 1
         * require : 121
         * gender : 1
         * stat : 1
         * serve : 0
         */

        private String kid;
        private String name;
        private String state;
        private String require;
        private String gender;
        private String stat;
        private String serve;

        public String getKid() {
            return kid;
        }

        public void setKid(String kid) {
            this.kid = kid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getRequire() {
            return require;
        }

        public void setRequire(String require) {
            this.require = require;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public String getServe() {
            return serve;
        }

        public void setServe(String serve) {
            this.serve = serve;
        }
    }
}
