package com.qigaikj.parttimejob.bean;

/**
 * Created by Administrator on 2017/12/4/004.
 */

public class WalletDetails {

    /**
     * payment : 0.01
     * out_trade_no : 20171202111836766249519777946456
     * transaction_id : 4200000003201712028472983686
     * addtime : 2017-12-02 11:18:36
     * endtime : 2017-12-02 11:18:46
     * pay : 2
     */

    private String payment;
    private String out_trade_no;
    private String transaction_id;
    private String addtime;
    private String endtime;
    private String pay;

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
}
