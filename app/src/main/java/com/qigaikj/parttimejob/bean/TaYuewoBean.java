package com.qigaikj.parttimejob.bean;

import java.util.List;

public class TaYuewoBean {

    /**
     * ret : 1
     * message : 请求成功
     * data : [{"did":"35","uid":"1","uname":"qgid_1","logo":"http://ceshi.qigaikj.com/Public/file/20171109163437.png","gender":"1","state":"2","phone_state":"1","wx_state":"2","name":"健身","flag":"3","distance":"12244949"}]
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
         * did : 35
         * uid : 1
         * uname : qgid_1
         * logo : http://ceshi.qigaikj.com/Public/file/20171109163437.png
         * gender : 1
         * state : 2
         * phone_state : 1
         * wx_state : 2
         * name : 健身
         * flag : 3
         * distance : 12244949
         */

        private String did;
        private String uid;
        private String uname;
        private String logo;
        private String gender;
        private String state;
        private String phone_state;
        private String wx_state;
        private String name;
        private String flag;
        private String distance;

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
