package com.example.taobaounion.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FootPrintData implements Serializable, IBaseInfo {

    private List<FootPrintData> footPrintList = new ArrayList<>();

    private Ticket mTicket;
    private String url;
    private String link;
    private String title;

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCoupon() {
        return mCoupon;
    }

    public void setCoupon(long coupon) {
        mCoupon = coupon;
    }

    private long mCoupon;

    public List<FootPrintData> getFootPrintList() {
        return footPrintList;
    }

    public void setFootPrintList(List<FootPrintData> footPrintList) {
        this.footPrintList = footPrintList;
    }

    @Override
    public String getPict_url() {
        return url;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Ticket getTicket() {
        return mTicket;
    }

    public void setTicket(Ticket ticket) {
        mTicket = ticket;
    }

}


