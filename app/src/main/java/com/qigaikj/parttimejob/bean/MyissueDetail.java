package com.qigaikj.parttimejob.bean;

import java.util.List;

/**
 * 开发者：庞宇锋
 * 时间： 2018/1/11/011.
 * 功能：我的发布详情
 */

public class MyissueDetail {

    /**
     * detail : {"title":"标题","money":"1.00","pay_style":"1","num_people":"1","begindate":"01-12","enddate":"01-20","salary":"1","type":"推广","count":"0"}
     * apply : []
     */

    private DetailBean detail;
    private List<apply> apply;

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public List<apply> getApply() {
        return apply;
    }

    public void setApply(List<apply> apply) {
        this.apply = apply;
    }

    public static class apply {
        private String uid;
        private String name;
        private String work_number;
        private String state;
        private String logo;
        private String flag;

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getFlag() {
            return flag;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLogo() {
            return logo;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWork_number() {
            return work_number;
        }

        public void setWork_number(String work_number) {
            this.work_number = work_number;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    public static class DetailBean {
        /**
         * title : 标题
         * money : 1.00
         * pay_style : 1
         * num_people : 1
         * begindate : 01-12
         * enddate : 01-20
         * salary : 1
         * type : 推广
         * count : 0
         */

        private String title;
        private String money;
        private String pay_style;
        private String num_people;
        private String begindate;
        private String enddate;
        private String salary;
        private String type;
        private String count;

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

        public String getPay_style() {
            return pay_style;
        }

        public void setPay_style(String pay_style) {
            this.pay_style = pay_style;
        }

        public String getNum_people() {
            return num_people;
        }

        public void setNum_people(String num_people) {
            this.num_people = num_people;
        }

        public String getBegindate() {
            return begindate;
        }

        public void setBegindate(String begindate) {
            this.begindate = begindate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
