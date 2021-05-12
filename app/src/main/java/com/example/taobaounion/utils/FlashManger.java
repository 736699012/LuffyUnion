package com.example.taobaounion.utils;

import com.example.taobaounion.model.dao.FlashCoupon;

import java.util.List;

public class FlashManger {

    private List<FlashCoupon> mFlashCouponList;

    private static FlashManger sFlashManger = new FlashManger();

    private FlashManger() {

    }

    public static FlashManger getInstance() {
        return sFlashManger;
    }

    public List<FlashCoupon> getFlashCouponList() {
        return mFlashCouponList;
    }

    public void setFlashCouponList(List<FlashCoupon> flashCouponList) {
        mFlashCouponList = flashCouponList;
    }
}
