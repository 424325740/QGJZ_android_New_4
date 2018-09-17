package com.qigaikj.parttimejob.bean;

public class BaomingBean {

    /**
     * ret : 1
     * message : 报名成功
     * data : {"is_apply":"1"}
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
         * is_apply : 1
         */

        private String is_apply;

        public String getIs_apply() {
            return is_apply;
        }

        public void setIs_apply(String is_apply) {
            this.is_apply = is_apply;
        }
    }
}
