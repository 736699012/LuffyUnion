package com.example.taobaounion.model.dao;

import cn.bmob.v3.BmobObject;

public class UnInsert extends BmobObject {

    private String title = "";
    private String coverUrl = "";
    private String url = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
