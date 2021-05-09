package com.example.taobaounion.model.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CollectionBean implements IBaseInfo {

    private List<CollectionBean> mCollectionBeanList;

    private String title = "";
    private float finalMoney = 0.0f;
    private String coverUrl = "";
    private String url = "";
    private long coupons = 0;

    public long getCoupons() {
        return coupons;
    }

    public void setCoupons(long coupons) {
        this.coupons = coupons;
    }

    public List<CollectionBean> getCollectionBeanList() {
        if (mCollectionBeanList == null) {
            mCollectionBeanList = new ArrayList<>();
        }
        return mCollectionBeanList;
    }

    public void setCollectionBeanList(List<CollectionBean> collectionBeanList) {
        mCollectionBeanList = collectionBeanList;
    }

    public CollectionBean() {

    }

    public CollectionBean(String title, float finalMoney, String coverUrl, String url) {
        this.title = title;
        this.finalMoney = finalMoney;
        this.coverUrl = coverUrl;
        this.url = url;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectionBean)) return false;
        CollectionBean that = (CollectionBean) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(finalMoney, that.finalMoney) &&
                Objects.equals(coverUrl, that.coverUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, finalMoney, coverUrl);
    }
}
