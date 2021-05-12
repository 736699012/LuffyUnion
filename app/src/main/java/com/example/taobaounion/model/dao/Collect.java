package com.example.taobaounion.model.dao;

import com.example.taobaounion.model.bean.CollectionBean;
import com.example.taobaounion.model.bean.IBaseInfo;

import java.util.Objects;

import cn.bmob.v3.BmobObject;

public class Collect extends BmobObject implements IBaseInfo {

    private String title = "";
    private float finalMoney = 0.0f;
    private String coverUrl = "";
    private String url = "";
    private long coupons = 0;
    private String userId = "";


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Collect() {

    }

    public Collect(CollectionBean collectionBean) {
        this.title = collectionBean.getTitle();
        this.finalMoney = collectionBean.getFinalMoney();
        this.coverUrl = collectionBean.getCoverUrl();
        this.url = collectionBean.getLink();
        this.coupons = collectionBean.getCoupons();
    }

    public Collect(String title, float finalMoney, String coverUrl, String url) {
        this.title = title;
        this.finalMoney = finalMoney;
        this.coverUrl = coverUrl;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Collect)) return false;
        Collect collect = (Collect) o;
        return Objects.equals(title, collect.title) &&
                Objects.equals(userId, collect.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, userId);
    }

    public Collect(Collect collect) {
        this.title = collect.getTitle();
        this.finalMoney = collect.getFinalMoney();
        this.coverUrl = collect.getCoverUrl();
        this.url = collect.getUrl();
        this.coupons = collect.getCoupons();
        setObjectId(collect.getObjectId());
        setACL(collect.getACL());
        setCreatedAt(collect.getCreatedAt());
        setUpdatedAt(collect.getUpdatedAt());
    }

    @Override
    public String getPict_url() {
        return coverUrl;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getLink() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getFinalMoney() {
        return finalMoney;
    }

    public void setFinalMoney(float finalMoney) {
        this.finalMoney = finalMoney;
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

    public long getCoupons() {
        return coupons;
    }

    public void setCoupons(long coupons) {
        this.coupons = coupons;
    }
}
