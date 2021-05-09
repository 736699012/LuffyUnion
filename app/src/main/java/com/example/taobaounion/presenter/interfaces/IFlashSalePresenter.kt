package com.example.taobaounion.presenter.interfaces

import com.example.taobaounion.model.bean.FlashSaleData

interface IFlashSalePresenter {

    fun addFlashSale(data: FlashSaleData)

    fun isLose(data: FlashSaleData): Boolean

    fun getFlashSaleList(): List<FlashSaleData>

}