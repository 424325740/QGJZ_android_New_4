package com.qigaikj.parttimejob.bean;

/**
 * Created by Administrator on 2017/11/24/024.
 * 保存版本更新内容
 */

public class New_Version {

    /**
     * version : 1.0
     * down_url : http://qigaikj.com/app-release.apk
     * explain : 版本更新啦！
     */

    private String version;
    private String down_url;
    private String explain;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
