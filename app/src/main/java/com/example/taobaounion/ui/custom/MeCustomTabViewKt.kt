package com.example.taobaounion.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.taobaounion.R
import kotlinx.android.synthetic.main.custom_me_tab_view.view.*

class MeCustomTabViewKt @JvmOverloads
constructor(context: Context, attributes: AttributeSet? = null
            , def: Int = 0) : LinearLayout(context, attributes, def) {

    private var mListener: OnItemClickListener? = null

    companion object {
        val resourceIds: IntArray = intArrayOf(R.mipmap.collect_me, R.mipmap.foot_print, R.mipmap.flash_sale)
        val titleList: Array<String> = arrayOf<String>("个人收藏", "足迹", "限时抢购")
    }

    fun setOnItemListener(listener: OnItemClickListener) {
        mListener = listener
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_me_tab_view, this)
        custom_collect.run {
            bindData(resourceIds[0], titleList[0])
            setOnClickListener {
                mListener?.onCollectClick()
            }
        }
        custom_foot_print.run {
            bindData(resourceIds[1], titleList[1])
            setOnClickListener {
                mListener?.onFootPrintClick()
            }
        }
        custom_flash_sale.run {
            bindData(resourceIds[2], titleList[2])
            setOnClickListener {
                mListener?.onFlashSaleClick()
            }
        }
    }


    interface OnItemClickListener {
        fun onCollectClick()
        fun onFootPrintClick()
        fun onFlashSaleClick()
    }

}