package com.example.taobaounion.utils;

import com.example.taobaounion.model.bean.FlashSaleData;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.presenter.interfaces.IFlashSalePresenter;

import java.util.Iterator;
import java.util.List;

public class Utils {

    private static long DEFAULT_TIME = 10 * 60 * 1000;

    public static List<? extends IBaseInfo> filterList(List<? extends IBaseInfo> list) {
        IFlashSalePresenter presenter = PresentManger.getInstance().getFlashSalePresenter();
        List<FlashSaleData> flashSaleList = presenter.getFlashSaleList();
        for (FlashSaleData temp : flashSaleList) {
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
}
