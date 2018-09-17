package com.qigaikj.parttimejob.bean;

/**
 * Created by Administrator on 2017/11/4/004.
 * 兼职列表
 */

public class PartTime {


    /**
     * id : 1
     * title : 百度外卖送餐
     * address : 北京市通州区北苑地铁口
     * begindate : 08-15
     * enddate : 09-13
     * num_people : 10
     * pay_style : 2
     * money : 1000
     */

    private String id;
    private String title;
    private String address;
    private String begindate;
    private String enddate;
    private String num_people;
    private String pay_style;
    private String money;
    private String distance;
    private String tag;//1：不显示2：企业保障
    private String salary;
    private String pledge_flag;// 1未退押金 2已退押金 3无押金
    private String pic;

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }

    public void setPledge_flag(String pledge_flag) {
        this.pledge_flag = pledge_flag;
    }

    public String getPledge_flag() {
        return pledge_flag;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSalary() {
        return salary;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNum_people() {
        return num_people;
    }

    public void setNum_people(String num_people) {
        this.num_people = num_people;
    }

    public String getPay_style() {
        return pay_style;
    }

    public void setPay_style(String pay_style) {
        this.pay_style = pay_style;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
