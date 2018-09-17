package com.qigaikj.parttimejob.bean;

public class ClassifyForAll_Item {

    /**
     * tid  二级分类id
     * tname（二级分类名称）
     * pic（二级分类对应图标）
     * */
    public String tid;
    public String tname;
    public String pic;

    public String getTid() {
        return tid;
    }

    public String getTname() {
        return tname;
    }

    public String getPic() {
        return pic;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

}
