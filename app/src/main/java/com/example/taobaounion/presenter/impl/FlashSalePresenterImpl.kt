package com.example.taobaounion.presenter.impl

import com.example.taobaounion.model.bean.FlashSaleData
import com.example.taobaounion.presenter.interfaces.IFlashSalePresenter
import com.example.taobaounion.utils.JsonCacheUtil

class FlashSalePresenterImpl : IFlashSalePresenter {

    private val instance: JsonCacheUtil = JsonCacheUtil.getInstance()

    companion object {
        const val KEY_FLASH_SALE = "key_flash_sale_00"
    }


    override fun addFlashSale(data: FlashSaleData) {
        val bean = getBean()
        var isHas: Boolean = false
        bean.list.forEach {
            if (it == data) {
                isHas = true
            }
        }
        if (!isHas) {
            bean.list.add(0, data)
        }
        instance.saveCache(KEY_FLASH_SALE, bean)
    }

    override fun isLose(data: FlashSaleData): Boolean {
        val dur = System.currentTimeMillis() - data.startTime
        if (dur > 10 * 60 * 1000) {
            return true
        }
        return false
    }

    override fun getFlashSaleList(): List<FlashSaleData> {
        val data = getBean()
        return data.list
    }

    private fun getBean(): FlashSaleData {
        var data = instance.getValue(KEY_FLASH_SALE, FlashSaleData::class.java)
        if (data == null) {
            data = FlashSaleData()
        }
        return data
    }
}