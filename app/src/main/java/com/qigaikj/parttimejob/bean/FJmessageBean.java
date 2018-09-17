package com.qigaikj.parttimejob.bean;

import java.util.List;

public class FJmessageBean {

    /**
     * ret : 1
     * message : 请求成功
     * data : {"uid":"1","uname":"qgid_1","logo":"http://ceshi.qigaikj.com/Public/file/20171109163437.png","user_skill":["书法","K歌","健身"],"gender":"1","state":"2","phone_state":"1","wx_state":"2","introduce":"背相声贯口，太平歌词，快板，评书赞，京剧和评戏唱词\u2026"}
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
        /**
         * uid : 1
         * uname : qgid_1
         * logo : http://ceshi.qigaikj.com/Public/file/20171109163437.png
         * user_skill : ["书法","K歌","健身"]
         * gender : 1
         * state : 2
         * phone_state : 1
         * wx_state : 2
         * introduce : 背相声贯口，太平歌词，快板，评书赞，京剧和评戏唱词…
         */

        private String uid;
        private String uname;
        private String logo;
        private String gender;
        private String state;
        private String phone_state;
        private String wx_state;
        private String introduce;
        private List<String> user_skill;

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

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public List<String> getUser_skill() {
            return user_skill;
        }

        public void setUser_skill(List<String> user_skill) {
            this.user_skill = user_skill;
        }
    }
}
