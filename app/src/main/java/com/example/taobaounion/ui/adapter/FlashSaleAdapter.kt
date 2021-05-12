package com.example.taobaounion.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.taobaounion.R
import com.example.taobaounion.model.bean.FlashSaleData
import com.example.taobaounion.model.dao.FlashCoupon
import com.example.taobaounion.ui.custom.FlashSaleItemView
import com.example.taobaounion.utils.SizeUtils
import java.util.*


class FlashSaleAdapter : RecyclerView.Adapter<FlashSaleAdapter.InnerHolder>() {

    var flashSaleList = arrayListOf<FlashCoupon>()
    private var listener: OnFlashSaleItemClickListener? = null

    companion object {
        private const val DEFAULT_TIME: Long = 10 * 60 * 1000
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        return InnerHolder(FlashSaleItemView(parent.context))
    }

    override fun getItemCount(): Int = flashSaleList.size


    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val flashSaleData = flashSaleList[position]
        holder.bindData(flashSaleData)
        val useTime = System.currentTimeMillis() - flashSaleData.startTime
        val timeRemainIng = (10 * 60 * 1000 - useTime) / 1000
        holder.itemView.isClickable = timeRemainIng > 0
        holder.itemView.isEnabled = timeRemainIng > 0
        holder.itemView.setOnClickListener {
            listener?.onItemClick(flashSaleData)
        }
    }


    fun setData(list: List<FlashCoupon>?) {
        flashSaleList.clear()
        if (list != null) {
            flashSaleList.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun setFlashSaleListener(listener: OnFlashSaleItemClickListener) {
        this.listener = listener
    }

    class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bindData(data: FlashCoupon) {
            if (itemView is FlashSaleItemView) {
                itemView.bindData(data)
            }
        }


    }

    interface OnFlashSaleItemClickListener {

        fun onItemClick(flashSaleData: FlashCoupon)
    }

}