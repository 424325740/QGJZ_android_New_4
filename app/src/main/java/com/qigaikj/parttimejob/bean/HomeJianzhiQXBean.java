package com.qigaikj.parttimejob.bean;

public class HomeJianzhiQXBean {

    /**
     * ret : 1
     * message : 请求成功
     * data : {"wid":"33","name":"测试明天兼职","address":"SOHO现代城A座","start_date":"6月30日","end_date":"7月2日","money":"666.00","unit":"2","way":"1","introduce":"名模们在哪里？","require":"建立和管理","uname":"마누라王冰","logo":"http://thirdqq.qlogo.cn/qqapp/1105947309/1AF3AA30DFDF21803A63CED7CF6536C7/100","state":"2","is_apply":"0","is_like":"2"}
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
         * wid : 33
         * name : 测试明天兼职
         * address : SOHO现代城A座
         * start_date : 6月30日
         * end_date : 7月2日
         * money : 666.00
         * unit : 2
         * way : 1
         * introduce : 名模们在哪里？
         * require : 建立和管理
         * uname : 마누라王冰
         * logo : http://thirdqq.qlogo.cn/qqapp/1105947309/1AF3AA30DFDF21803A63CED7CF6536C7/100
         * state : 2
         * is_apply : 0
         * is_like : 2
         */

        private String wid;
        private String name;
        private String address;
        private String start_date;
        private String end_date;
        private String money;
        private String unit;
        private String way;
        private String introduce;
        private String require;
        private String uname;
        private String logo;
        private String state;
        private String is_apply;
        private String is_like;

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

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
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

        public String getWay() {
            return way;
        }

        public void setWay(String way) {
            this.way = way;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getRequire() {
            return require;
        }

        public void setRequire(String require) {
            this.require = require;
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

        public String getIs_apply() {
            return is_apply;
        }

        public void setIs_apply(String is_apply) {
            this.is_apply = is_apply;
        }

        public String getIs_like() {
            return is_like;
        }

        public void setIs_like(String is_like) {
            this.is_like = is_like;
        }
    }
}
