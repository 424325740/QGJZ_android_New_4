package com.qigaikj.parttimejob.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索bean
 */
public class HomeSearchBean implements Serializable{

    /**
     * ret : 1
     * message : 请求成功
     * data : {"work":[{"wid":"39","name":"测试","address":"上海","money":"50.00","unit":"2","uid":"3","uname":"shy","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/BC7751E30AC4A0027B36688BC777C7D2/100","state":"2","hits":"0","integrity":"0"},{"wid":"38","name":"测试","address":"朝阳区","money":"5.00","unit":"1","uid":"15","uname":"(～ o ～)~zZ","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/0DF39CDA2C9EF26F8E91596E3EE3F354/100","state":"2","hits":"0","integrity":"0"},{"wid":"33","name":"测试明天兼职","address":"SOHO现代城A座","money":"666.00","unit":"2","uid":"3","uname":"shy","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/BC7751E30AC4A0027B36688BC777C7D2/100","state":"2","hits":"0","integrity":"0"},{"wid":"32","name":"629测试发布的兼职","address":"SOHO现代城A座","money":"222.00","unit":"2","uid":"3","uname":"shy","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/BC7751E30AC4A0027B36688BC777C7D2/100","state":"2","hits":"0","integrity":"0"},{"wid":"17","name":"测试兼职教授","address":"SOHO现代城A座","money":"666.00","unit":"2","uid":"8","uname":"Make it happen","logo":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKy8nrX7vOPKPlZictpqPYhIT18icicZaKv7zicIq2Tu01HALuGmf31JG7We5npMzUlpVNt9A9c4Cbbuw/132","state":"2","hits":"0","integrity":"0"},{"wid":"13","name":"测试兼职2","address":"北京市","money":"666.00","unit":"2","uid":"8","uname":"Make it happen","logo":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKy8nrX7vOPKPlZictpqPYhIT18icicZaKv7zicIq2Tu01HALuGmf31JG7We5npMzUlpVNt9A9c4Cbbuw/132","state":"2","hits":"0","integrity":"0"},{"wid":"12","name":"测试兼职2","address":"北京市","money":"666.00","unit":"2","uid":"8","uname":"Make it happen","logo":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKy8nrX7vOPKPlZictpqPYhIT18icicZaKv7zicIq2Tu01HALuGmf31JG7We5npMzUlpVNt9A9c4Cbbuw/132","state":"2","hits":"0","integrity":"0"},{"wid":"11","name":"测试兼职","address":"北京","money":"223.00","unit":"2","uid":"8","uname":"Make it happen","logo":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKy8nrX7vOPKPlZictpqPYhIT18icicZaKv7zicIq2Tu01HALuGmf31JG7We5npMzUlpVNt9A9c4Cbbuw/132","state":"2","hits":"0","integrity":"0"}],"advert":[{"aid":"6","pic":"http://cdn.ceshi.qigaikj.com/2018/06/96660569.png"}]}
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

    public static class DataBean implements Serializable{
        private List<WorkBean> work;
        private List<AdvertBean> advert;

        public List<WorkBean> getWork() {
            return work;
        }

        public void setWork(List<WorkBean> work) {
            this.work = work;
        }

        public List<AdvertBean> getAdvert() {
            return advert;
        }

        public void setAdvert(List<AdvertBean> advert) {
            this.advert = advert;
        }

        public static class WorkBean {
            /**
             * wid : 39
             * name : 测试
             * address : 上海
             * money : 50.00
             * unit : 2
             * uid : 3
             * uname : shy
             * logo : http://thirdqq.qlogo.cn/qqapp/1105947309/BC7751E30AC4A0027B36688BC777C7D2/100
             * state : 2
             * hits : 0
             * integrity : 0
             */

            private String wid;
            private String name;
            private String address;
            private String money;
            private String unit;
            private String uid;
            private String uname;
            private String logo;
            private String state;
            private String hits;
            private String integrity;

            public String getWid() {
                return wid;
            }

            public void setWid(String wid) {
                this.wid = wid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
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

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
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
             * aid : 6
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
