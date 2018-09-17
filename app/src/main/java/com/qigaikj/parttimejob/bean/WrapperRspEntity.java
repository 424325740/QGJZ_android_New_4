package com.qigaikj.parttimejob.bean;

/**
 * 作者：庞宇锋
 * 功能：
 * 时间：2017/8/15.
 */

public class WrapperRspEntity<T> {
    private int ret;
    private T data;
    private String message; //errorMSG;

    public int getStatus() {
        return ret;
    }

    public void setStatus(int ret) {
        this.ret = ret;
    }

    public T getData() {
        return data;
    }

    public void setData(T result) {
        this.data = result;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String message) {
        this.message = message;
    }

}
