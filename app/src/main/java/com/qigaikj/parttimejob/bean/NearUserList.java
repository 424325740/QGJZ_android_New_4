package com.qigaikj.parttimejob.bean;

import java.util.List;

public class NearUserList {


    /**
     * ret : 1
     * message : 请求成功
     * data : [{"lng":"116.3988364","lat":"39.8458129","uid":"1","logo":"http://ceshi.qigaikj.com/Public/file/20171109163437.png"},{"lng":"116.3988364","lat":"39.8458129","uid":"2","logo":"http://ceshi.qigaikj.com/Public/file/20171109163437.png"},{"lng":"116.4766230","lat":"39.9076030","uid":"8","logo":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKy8nrX7vOPKPlZictpqPYhIT18icicZaKv7zicIq2Tu01HALuGmf31JG7We5npMzUlpVNt9A9c4Cbbuw/132"},{"lng":"116.4600000","lat":"39.9200000","uid":"21","logo":"http://ceshi.qigaikj.com/Public/file/20171109163437.png"}]
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
         * lng : 116.3988364
         * lat : 39.8458129
         * uid : 1
         * logo : http://ceshi.qigaikj.com/Public/file/20171109163437.png
         */

        private String lng;
        private String lat;
        private String uid;
        private String logo;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
