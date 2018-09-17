package com.qigaikj.parttimejob.bean;

import java.io.Serializable;
import java.util.List;

public class DingDanYueTaBean implements Serializable{

    /**
     * ret : 1
     * message : 请求成功
     * data : [{"did":"26","name":null,"pic":null,"addtime":"2018-08-24 09:16:11","serve":"0","days":"3","state":"1","users":[],"flag":"2"}]
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
         * did : 26
         * name : null
         * pic : null
         * addtime : 2018-08-24 09:16:11
         * serve : 0
         * days : 3
         * state : 1
         * users : []
         * flag : 2
         */

        private String did;
        private Object name;
        private Object pic;
        private String addtime;
        private String serve;
        private String days;
        private String state;
        private String flag;
        private List<?> users;

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getPic() {
            return pic;
        }

        public void setPic(Object pic) {
            this.pic = pic;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getServe() {
            return serve;
        }

        public void setServe(String serve) {
            this.serve = serve;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public List<?> getUsers() {
            return users;
        }

        public void setUsers(List<?> users) {
            this.users = users;
        }
    }
}
