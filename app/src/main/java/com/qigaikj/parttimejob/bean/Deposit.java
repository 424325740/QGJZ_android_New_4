package com.qigaikj.parttimejob.bean;

/**
 * Created by Administrator on 2017/12/5/005.
 * 保存提现时数据信息
 */

public class Deposit {

    /**
     * money : 0.17
     * zfb_accounts :
     * zfb_realname :
     */

    private String money;
    private String zfb_accounts;
    private String zfb_realname;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getZfb_accounts() {
        return zfb_accounts;
    }

    public void setZfb_accounts(String zfb_accounts) {
        this.zfb_accounts = zfb_accounts;
    }

    public String getZfb_realname() {
        return zfb_realname;
    }

    public void setZfb_realname(String zfb_realname) {
        this.zfb_realname = zfb_realname;
    }
}
