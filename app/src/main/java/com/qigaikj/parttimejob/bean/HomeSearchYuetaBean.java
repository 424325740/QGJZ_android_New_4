package com.qigaikj.parttimejob.bean;

import java.util.List;

public class HomeSearchYuetaBean {

    /**
     * ret : 1
     * message : 请求成功
     * data : {"skill":[{"uid":"2","uname":"qgid_2","logo":"http://ceshi.qigaikj.com/Public/file/20171109163437.png","user_skill":["陪聊天","王者荣耀","K歌"],"gender":"1","state":"2","phone_state":"1","wx_state":"2","skill_pic":["http://cdn.ceshi.qigaikj.com/2018/08/62967442.png","http://cdn.ceshi.qigaikj.com/2018/08/62967446.png","http://cdn.ceshi.qigaikj.com/2018/08/62967441.png"],"introduce":"收集了很多国家的硬币，基本上全了，还有中国以前的很多纪念币。也收集了很多绝版了的奥运徽章 这个一直很自豪的！","distance":"0","jid":"43","price":"0.00","hits":"0","integrity":"0"}],"advert":[{"aid":"7","pic":"http://cdn.ceshi.qigaikj.com/2018/06/96660569.png"},{"aid":"4","pic":"http://cdn.ceshi.qigaikj.com/2018/06/96660569.png"}]}
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
        private List<SkillBean> skill;
        private List<AdvertBean> advert;

        public List<SkillBean> getSkill() {
            return skill;
        }

        public void setSkill(List<SkillBean> skill) {
            this.skill = skill;
        }

        public List<AdvertBean> getAdvert() {
            return advert;
        }

        public void setAdvert(List<AdvertBean> advert) {
            this.advert = advert;
        }

        public static class SkillBean {
            /**
             * uid : 2
             * uname : qgid_2
             * logo : http://ceshi.qigaikj.com/Public/file/20171109163437.png
             * user_skill : ["陪聊天","王者荣耀","K歌"]
             * gender : 1
             * state : 2
             * phone_state : 1
             * wx_state : 2
             * skill_pic : ["http://cdn.ceshi.qigaikj.com/2018/08/62967442.png","http://cdn.ceshi.qigaikj.com/2018/08/62967446.png","http://cdn.ceshi.qigaikj.com/2018/08/62967441.png"]
             * introduce : 收集了很多国家的硬币，基本上全了，还有中国以前的很多纪念币。也收集了很多绝版了的奥运徽章 这个一直很自豪的！
             * distance : 0
             * jid : 43
             * price : 0.00
             * hits : 0
             * integrity : 0
             */

            private String uid;
            private String uname;
            private String logo;
            private String gender;
            private String state;
            private String phone_state;
            private String wx_state;
            private String introduce;
            private String distance;
            private String jid;
            private String price;
            private String hits;
            private String integrity;
            private List<String> user_skill;
            private List<String> skill_pic;

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

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getJid() {
                return jid;
            }

            public void setJid(String jid) {
                this.jid = jid;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public List<String> getUser_skill() {
                return user_skill;
            }

            public void setUser_skill(List<String> user_skill) {
                this.user_skill = user_skill;
            }

            public List<String> getSkill_pic() {
                return skill_pic;
            }

            public void setSkill_pic(List<String> skill_pic) {
                this.skill_pic = skill_pic;
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
