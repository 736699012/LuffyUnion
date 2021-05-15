package com.example.taobaounion.utils;

import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.model.dao.FlashCoupon;
import com.example.taobaounion.model.dao.UnInsert;
import com.example.taobaounion.presenter.interfaces.IUnInsertPresenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Utils {

    private static long DEFAULT_TIME = 10 * 60 * 1000;

    public static List<? extends IBaseInfo> filterList(List<? extends IBaseInfo> list) {
        List<FlashCoupon> flashSaleList = FlashManger.getInstance().getFlashCouponList();
        if (flashSaleList == null || flashSaleList.size() < 0) {
            return list;
        }
        for (FlashCoupon temp : flashSaleList) {
            long useTime = System.currentTimeMillis() - temp.getStartTime();
            long timeRemainIng = (DEFAULT_TIME - useTime) / 1000;
            if (timeRemainIng >= 0) {
                continue;
            }
            Iterator<? extends IBaseInfo> iterator = list.iterator();
            while (iterator.hasNext()) {
                IBaseInfo data = iterator.next();
                if (temp.equals(data)) {
                    iterator.remove();
                }
            }
        }
        return list;
    }

    public static void changeFlash() {
        BmobQuery<FlashCoupon> query = new BmobQuery<>();
        query.findObjects(new FindListener<FlashCoupon>() {
            @Override
            public void done(List<FlashCoupon> list, BmobException e) {
                List<FlashCoupon> filter = new ArrayList<>();
                if (list == null || list.size() <= 0) {
                    return;
                }
                for (FlashCoupon coupon : list) {
                    if (coupon.getUserId().equals(UserManger.getInstance().getUser().getObjectId())) {
                        filter.add(coupon);
                    }
                }
                FlashManger.getInstance().setFlashCouponList(filter);
            }
        });
    }


    public static void addUnInsert(UnInsert unInsert) {
        IUnInsertPresenter iUnInsertPresenter = PresentManger.getInstance().getIUnInsertPresenter();
        iUnInsertPresenter.addUnInsert(unInsert);
    }

    public static void getUnInsert() {
        BmobQuery<UnInsert> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<UnInsert>() {
            @Override
            public void done(List<UnInsert> list, BmobException e) {
                if (e == null) {
                    UnInsertManger.getInstance().setInsertList(list);
                }
            }
        });
    }
}
