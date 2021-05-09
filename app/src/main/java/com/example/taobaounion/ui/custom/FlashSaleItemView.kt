package com.example.taobaounion.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.taobaounion.R
import com.example.taobaounion.model.bean.FlashSaleData
import com.example.taobaounion.utils.PresentManger
import com.example.taobaounion.utils.SizeUtils
import kotlinx.android.synthetic.main.item_flash_sale.view.*

class FlashSaleItemView @JvmOverloads
constructor(context: Context, attributes: AttributeSet? = null
            , def: Int = 0) : LinearLayout(context, attributes, def) {
    companion object {
        private const val DEFAULT_TIME: Long = 10 * 60 * 1000
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.item_flash_sale, this)
    }

    private var mData: FlashSaleData? = null

    fun bindData(data: FlashSaleData) {
        mData = data
        val roundedCorners = RoundedCorners(SizeUtils.dip2px(context, 16f))
        val bitmapTransform = RequestOptions.bitmapTransform(roundedCorners)
        Glide.with(flash_sale_cover.context).load(data.cover)
                .apply(bitmapTransform)
                .into(flash_sale_cover)
        flash_sale_title.text = data.titles
        flash_sale_coupon.text = "限时省${data.couponCount}元"
        updateTime(data.startTime)
    }


    private fun updateTime(startTime: Long) {
        val useTime = System.currentTimeMillis() - startTime
        val timeRemainIng = (DEFAULT_TIME - useTime) / 1000
        if (timeRemainIng <= 0) {
            flash_sale_no_time.text = "亲,下次跑快点哟"
            flash_sale_no_time.visibility = View.VISIBLE
            flash_sale_has_time.visibility = View.GONE
            val flashSalePresenter = PresentManger.getInstance().flashSalePresenter
            isClickable = false
        } else {
            flash_sale_has_time.text = "剩余时间${timeRemainIng}秒"
            isClickable = true
            postDelayed({
                updateTime(startTime)
            }, 1000)
        }
    }
}