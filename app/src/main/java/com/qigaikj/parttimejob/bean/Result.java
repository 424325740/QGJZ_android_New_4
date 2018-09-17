package com.qigaikj.parttimejob.bean;

/**
 * Created by Administrator on 2017/11/28/028.
 */

public class Result {

    /**
     * result_code : SUCCESS
     * flag : 1
     * total_price : 0.01
     * payment : 0.01
     * out_trade_no : 20171128184843940892340680668821
     * transaction_id : 4200000020201711287661099399
     * addtime : 2017-11-28 18:48:44
     * endtime : 2038-01-19 11:14:07
     */

    private String result_code;
    private int flag;
    private String total_price;
    private String payment;
    private String out_trade_no;
    private String transaction_id;
    private String addtime;
    private String endtime;

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

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
}
