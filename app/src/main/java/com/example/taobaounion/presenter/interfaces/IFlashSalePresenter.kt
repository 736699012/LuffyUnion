package com.example.taobaounion.presenter.interfaces

import com.example.taobaounion.base.IBasePresenter
import com.example.taobaounion.model.bean.FlashSaleData
import com.example.taobaounion.model.dao.FlashCoupon
import com.example.taobaounion.view.IFlashCallBack

interface IFlashSalePresenter : IBasePresenter<IFlashCallBack> {

    fun addFlashSale(data: FlashCoupon)

    fun isLose(data: FlashCoupon): Boolean

    fun getFlashSaleList(): List<FlashCoupon>

    fun getListByIntent()

}