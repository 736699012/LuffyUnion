package com.example.taobaounion.ui.adapter

import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.taobaounion.R
import com.example.taobaounion.model.bean.HomePagerContent
import com.example.taobaounion.model.bean.IBaseInfo
import com.example.taobaounion.utils.Constant
import com.example.taobaounion.utils.SizeUtils


class RecommendAdapter : RecyclerView.Adapter<RecommendAdapter.RecommendHolder>() {

    private var mList = arrayListOf<HomePagerContent.DataBean>()
    private var mCallback: OnRecommendItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_recommend, parent, false)
        return RecommendHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecommendHolder, position: Int) {
        holder.setData(mList[position])
        holder.itemView.setOnClickListener {
            mCallback?.onRecommendItemClick(mList[position])
        }
    }

    fun setData(dataList: List<HomePagerContent.DataBean>) {
        mList.clear()
        mList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun setOnRecommendListener(listener: OnRecommendItemClickListener) {
        mCallback = listener
    }

    interface OnRecommendItemClickListener {
        fun onRecommendItemClick(data: IBaseInfo)
    }


    class RecommendHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val mPic: ImageView
        private val mAnim: ImageView

        init {
            mPic = itemView.findViewById(R.id.recommend_pic)
            mAnim = itemView.findViewById(R.id.recommend_anim)
        }

        fun setData(data: HomePagerContent.DataBean) {
            val roundedCorners = RoundedCorners(SizeUtils.dip2px(itemView.context, 8f))
            val bitmapTransform = RequestOptions.bitmapTransform(roundedCorners)
            val size = 200
            Glide.with(itemView.context).load(Constant.IMG_BASE_URL + data.pict_url + "_" + size + "x" + size + ".jpg").apply(bitmapTransform).into(mPic)
            (mAnim.drawable as AnimationDrawable).start()
        }

    }
}