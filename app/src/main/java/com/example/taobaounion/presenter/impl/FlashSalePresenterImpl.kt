package com.example.taobaounion.presenter.impl

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.example.taobaounion.model.dao.FlashCoupon
import com.example.taobaounion.presenter.interfaces.IFlashSalePresenter
import com.example.taobaounion.utils.JsonCacheUtil
import com.example.taobaounion.utils.ToastUtil
import com.example.taobaounion.utils.UserManger
import com.example.taobaounion.utils.Utils
import com.example.taobaounion.view.IFlashCallBack
import java.util.*

class FlashSalePresenterImpl : IFlashSalePresenter {

    private val instance: JsonCacheUtil = JsonCacheUtil.getInstance()

    private var mCallBack: IFlashCallBack? = null

    companion object {
        const val KEY_FLASH_SALE = "key_flash_sale_00"
    }


    override fun addFlashSale(data: FlashCoupon) {
//        val bean = getBean()
//        var isHas: Boolean = false
//        bean.list.forEach {
//            if (it == data) {
//                isHas = true
//            }
//        }
//        if (!isHas) {
//            bean.list.add(0, data)
//        }
//        instance.saveCache(KEY_FLASH_SALE, bean)
        data.save(object : SaveListener<String>() {
            override fun done(p0: String?, e: BmobException?) {
                if (e == null) {
                    Utils.changeFlash()
                    ToastUtil.showToast("限时领券拉")
                }
            }

        })
    }

    override fun isLose(data: FlashCoupon): Boolean {
        val dur = System.currentTimeMillis() - data.startTime
        if (dur > 10 * 60 * 1000) {
            return true
        }
        return false
    }

    override fun getFlashSaleList(): List<FlashCoupon> {
//        val data = getBean()
//        return data.list
        return arrayListOf()
    }

    override fun getListByIntent() {
        mCallBack?.onLoading()
        val bmobQuery = BmobQuery<FlashCoupon>()
        bmobQuery.findObjects(object : FindListener<FlashCoupon>() {
            override fun done(result: MutableList<FlashCoupon>?, e: BmobException?) {
                if (e == null) {
                    val filter: MutableList<FlashCoupon> = ArrayList()
                    if (result == null || result.size <= 0) {
                        mCallBack?.onEmpty()
                        return
                    }
                    for (coupon in result) {
                        if (coupon.userId == UserManger.getInstance().user.objectId) {
                            filter.add(coupon)
                        }
                    }
                    if (filter.size == 0) {
                        mCallBack?.onEmpty()
                    } else {
                        mCallBack?.onSuccess(filter)
                    }
                } else {
                    mCallBack?.onError()
                }
            }

        })
    }

    override fun unRegisterViewCallBack(callback: IFlashCallBack?) {
        mCallBack = null
    }

    override fun registerViewCallBack(callback: IFlashCallBack?) {
        mCallBack = callback
    }

//    private fun getBean(): FlashSaleData {
//        var data = instance.getValue(KEY_FLASH_SALE, FlashSaleData::class.java)
//        if (data == null) {
//            data = FlashSaleData()
//        }
//        return data
//    }
}