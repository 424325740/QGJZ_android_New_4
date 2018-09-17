package com.qigaikj.parttimejob.bean;

import java.util.List;

/**
 * author:created by liangliang on 2018/6/7/007
 * function:
 */

public class ClassifyForAll {

    private String yiji;
    private List<ClassifyForAll_Item> erji;


    public List<ClassifyForAll_Item> getErji() {
        return erji;
    }
    public void setErji(List<ClassifyForAll_Item> erji) {
        this.erji = erji;
    }

    public String getYiji() {
        return yiji;
    }
    public void setYiji(String yiji) {
        this.yiji = yiji;
    }
}
