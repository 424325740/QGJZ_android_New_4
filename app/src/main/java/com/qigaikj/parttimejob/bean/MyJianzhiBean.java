package com.qigaikj.parttimejob.bean;

import java.util.List;

public class MyJianzhiBean {

    /**
     * ret : 1
     * message : 请求成功
     * data : [{"wid":"44","title":"运营","money":"20.00","unit":"1","address":"背景","uname":"shy","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/BC7751E30AC4A0027B36688BC777C7D2/100","state":"2","flag":"0"},{"wid":"31","title":"会议执行","money":"558.00","unit":"2","address":"北京","uname":"shy","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/BC7751E30AC4A0027B36688BC777C7D2/100","state":"2","flag":"1"},{"wid":"34","title":"把的摩羯","money":"666.00","unit":"2","address":"北京国贸地铁站","uname":"shy","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/BC7751E30AC4A0027B36688BC777C7D2/100","state":"2","flag":"3"},{"wid":"17","title":"测试兼职教授","money":"666.00","unit":"2","address":"SOHO现代城A座","uname":"Make it happen","logo":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKy8nrX7vOPKPlZictpqPYhIT18icicZaKv7zicIq2Tu01HALuGmf31JG7We5npMzUlpVNt9A9c4Cbbuw/132","state":"2","flag":"2"},{"wid":"18","title":null,"money":null,"unit":null,"address":null,"uname":null,"logo":null,"state":"2","flag":"1"}]
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
         * wid : 44
         * title : 运营
         * money : 20.00
         * unit : 1
         * address : 背景
         * uname : shy
         * logo : http://thirdqq.qlogo.cn/qqapp/1105947309/BC7751E30AC4A0027B36688BC777C7D2/100
         * state : 2
         * flag : 0
         */

        private String wid;
        private String title;
        private String money;
        private String unit;
        private String address;
        private String uname;
        private String logo;
        private String state;
        private String flag;

        public String getWid() {
            return wid;
        }

        public void setWid(String wid) {
            this.wid = wid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
