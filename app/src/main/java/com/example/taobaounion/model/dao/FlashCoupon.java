package com.example.taobaounion.model.dao;

import com.example.taobaounion.model.bean.FlashSaleData;
import com.example.taobaounion.model.bean.IBaseInfo;

import java.util.Objects;

import cn.bmob.v3.BmobObject;

public class FlashCoupon extends BmobObject implements IBaseInfo {

    private String title = "";
    private String coverUrl = "";
    private String url = "";
    private long coupons = 0;
    private String userId = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlashCoupon)) return false;
        FlashCoupon that = (FlashCoupon) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, userId);
    }

    private long startTime = 0;

    public FlashCoupon() {

    }

    public FlashCoupon(FlashSaleData flashSaleData) {
        this.url = flashSaleData.getLinkUrl();
        this.coverUrl = flashSaleData.getPict_url();
        this.title = flashSaleData.getTitle();
        this.startTime = flashSaleData.getStartTime();
        this.coupons = flashSaleData.getCouponCount();
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
