package com.qigaikj.parttimejob.bean;

import java.util.List;

public class FabuJianzhiBean {

    /**
     * ret : 1
     * message : 请求成功
     * data : [{"wid":"41","uname":"마누라王冰","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/1AF3AA30DFDF21803A63CED7CF6536C7/100","state":"2","title":"123","address":"123","money":"1.00","unit":"1","flag":"1"},{"wid":"39","uname":"마누라王冰","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/1AF3AA30DFDF21803A63CED7CF6536C7/100","state":"2","title":"测试","address":"上海","money":"50.00","unit":"2","flag":"1"},{"wid":"33","uname":"마누라王冰","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/1AF3AA30DFDF21803A63CED7CF6536C7/100","state":"2","title":"测试明天兼职","address":"SOHO现代城A座","money":"666.00","unit":"2","flag":"1"},{"wid":"30","uname":"마누라王冰","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/1AF3AA30DFDF21803A63CED7CF6536C7/100","state":"2","title":"程序员","address":"北京市朝阳区人民法院","money":"500.00","unit":"2","flag":"1"}]
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
         * wid : 41
         * uname : 마누라王冰
         * logo : http://thirdqq.qlogo.cn/qqapp/1105947309/1AF3AA30DFDF21803A63CED7CF6536C7/100
         * state : 2
         * title : 123
         * address : 123
         * money : 1.00
         * unit : 1
         * flag : 1
         */

        private String wid;
        private String uname;
        private String logo;
        private String state;
        private String title;
        private String address;
        private String money;
        private String unit;
        private String flag;

        public String getWid() {
            return wid;
        }

        public void setWid(String wid) {
            this.wid = wid;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
