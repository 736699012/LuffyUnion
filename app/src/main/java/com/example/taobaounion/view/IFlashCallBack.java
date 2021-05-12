package com.example.taobaounion.view;

import com.example.taobaounion.base.IBaseCallback;
import com.example.taobaounion.model.dao.FlashCoupon;

import java.util.List;

public interface IFlashCallBack extends IBaseCallback {

    void onSuccess(List<FlashCoupon> list);
}
