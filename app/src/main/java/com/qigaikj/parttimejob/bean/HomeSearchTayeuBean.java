package com.qigaikj.parttimejob.bean;

import java.util.List;

public class HomeSearchTayeuBean {

    /**
     * ret : 1
     * message : 请求成功
     * data : {"demand":[{"did":"14","uid":"3","uname":"shy","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/BC7751E30AC4A0027B36688BC777C7D2/100","gender":"2","state":"2","phone_state":"1","wx_state":"2","skill":"K歌","require":"美食家，有人一起吗？\n坐标北京\n","sex":"1","serve":"1","distance":"0","hits":"0","integrity":"0"}],"advert":[{"aid":"7","pic":"http://cdn.ceshi.qigaikj.com/2018/06/96660569.png"},{"aid":"4","pic":"http://cdn.ceshi.qigaikj.com/2018/06/96660569.png"}]}
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
        private List<DemandBean> demand;
        private List<AdvertBean> advert;

        public List<DemandBean> getDemand() {
            return demand;
        }

        public void setDemand(List<DemandBean> demand) {
            this.demand = demand;
        }

        public List<AdvertBean> getAdvert() {
            return advert;
        }

        public void setAdvert(List<AdvertBean> advert) {
            this.advert = advert;
        }

        public static class DemandBean {
            /**
             * did : 14
             * uid : 3
             * uname : shy
             * logo : http://thirdqq.qlogo.cn/qqapp/1105947309/BC7751E30AC4A0027B36688BC777C7D2/100
             * gender : 2
             * state : 2
             * phone_state : 1
             * wx_state : 2
             * skill : K歌
             * require : 美食家，有人一起吗？
             坐标北京

             * sex : 1
             * serve : 1
             * distance : 0
             * hits : 0
             * integrity : 0
             */

            private String did;
            private String uid;
            private String uname;
            private String logo;
            private String gender;
            private String state;
            private String phone_state;
            private String wx_state;
            private String skill;
            private String require;
            private String sex;
            private String serve;
            private String distance;
            private String hits;
            private String integrity;

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

            public String getSkill() {
                return skill;
            }

            public void setSkill(String skill) {
                this.skill = skill;
            }

            public String getRequire() {
                return require;
            }

            public void setRequire(String require) {
                this.require = require;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getServe() {
                return serve;
            }

            public void setServe(String serve) {
                this.serve = serve;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getHits() {
                return hits;
            }

            public void setHits(String hits) {
                this.hits = hits;
            }

            public String getIntegrity() {
                return integrity;
            }

            public void setIntegrity(String integrity) {
                this.integrity = integrity;
            }
        }

        public static class AdvertBean {
            /**
             * aid : 7
             * pic : http://cdn.ceshi.qigaikj.com/2018/06/96660569.png
             */

            private String aid;
            private String pic;

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }
    }
}
