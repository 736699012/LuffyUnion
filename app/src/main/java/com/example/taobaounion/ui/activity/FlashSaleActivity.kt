package com.example.taobaounion.ui.activity

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taobaounion.R
import com.example.taobaounion.base.BaseActivity
import com.example.taobaounion.model.bean.FlashSaleData
import com.example.taobaounion.model.dao.FlashCoupon
import com.example.taobaounion.ui.adapter.FlashSaleAdapter
import com.example.taobaounion.utils.PresentManger
import com.example.taobaounion.utils.SizeUtils
import com.example.taobaounion.utils.TicketUtil
import com.example.taobaounion.view.IFlashCallBack
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.activity_flash_sale.*
import java.util.*

class FlashSaleActivity : BaseActivity(), FlashSaleAdapter.OnFlashSaleItemClickListener, IFlashCallBack {

    private var flashSaleAdapter: FlashSaleAdapter? = null

    override fun initPresent() {
    }

    override fun getLayoutId(): Int = R.layout.activity_flash_sale


    override fun initView() {
        val flashSalePresenter = PresentManger.getInstance().flashSalePresenter
        flashSalePresenter.registerViewCallBack(this)
        flashSalePresenter.getListByIntent()
        flashSaleAdapter = FlashSaleAdapter()
        flashSaleAdapter?.setFlashSaleListener(this)
        flash_sale_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = flashSaleAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.run {
                        val space = SizeUtils.dip2px(context, 10f)
                        bottom = space
                        top = space
                        left = space
                        right = space
                    }
                }
            })
        }
        flash_refresh.run {
            setEnableLoadmore(false)
            setEnableRefresh(true)
            setOnRefreshListener(object : RefreshListenerAdapter() {
                override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                    super.onRefresh(refreshLayout)
                    flashSalePresenter.getListByIntent()
                    flash_refresh.finishRefreshing()
                }
            })
        }
    }

    override fun onItemClick(flashSaleData: FlashCoupon) {
        TicketUtil.handle2TaoBao(baseContext, flashSaleData, flashSaleData.coupons)
    }

    override fun onEmpty() {
        flash_empty.visibility = View.VISIBLE
        flash_loading.visibility = View.GONE
    }

    override fun onSuccess(list: MutableList<FlashCoupon>?) {
        flashSaleAdapter?.setData(list)
        flash_loading.visibility = View.GONE
        flash_empty.visibility = View.GONE
    }

    override fun onLoading() {
        flash_loading.visibility = View.VISIBLE
        flash_empty.visibility = View.GONE
    }

    override fun onError() {
        flash_empty.visibility = View.GONE
        flash_loading.visibility = View.GONE
    }

}