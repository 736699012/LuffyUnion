package com.example.taobaounion.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.taobaounion.R
import com.example.taobaounion.model.bean.IBaseInfo
import com.example.taobaounion.ui.adapter.HomePagerLooperAdapter
import com.example.taobaounion.utils.PresentManger
import com.example.taobaounion.utils.SizeUtils
import com.example.taobaounion.view.ILoginCallBack
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : AppCompatActivity(), HomePagerLooperAdapter.OnLooperItemClickListen, ILoginCallBack {
    private var mAdapter: HomePagerLooperAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        initView()
        initListen()
        bindData()
    }

    private fun bindData() {
//        mAdapter?.setData(contents)
//        val dx: Int = Int.MAX_VALUE / 2 % contents.size
//        把数据设置到中间
        //        把数据设置到中间
//        if (guide_pager_looper != null) {
//            guide_pager_looper.setCurrentItem(Int.MAX_VALUE / 2 - dx)
//        }
        if (guide_pager_points != null) {
            guide_pager_points.removeAllViews()
            val size = SizeUtils.dip2px(guide_pager_looper.context, 8f)
            val marginLeft = SizeUtils.dip2px(guide_pager_looper.context, 5f)
            for (i in 0..3) {
                val point = View(guide_pager_looper.context)
                if (i == 0) {
                    point.setBackgroundResource(R.drawable.shape_point_bg_selected)
                } else {
                    point.setBackgroundResource(R.drawable.shape_point_bg_normal)
                }
                val layoutParams = LinearLayout.LayoutParams(size, size)
                layoutParams.leftMargin = marginLeft
                layoutParams.rightMargin = marginLeft
                point.layoutParams = layoutParams
                guide_pager_points.addView(point)
            }
        }
    }

    private fun initListen() {
        guide_pager_looper.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            /**
             * 被选中时触发
             * @param position
             */
            override fun onPageSelected(position: Int) {
                if (0 != mAdapter?.dataSize) {
                    val realPosition: Int = position % (mAdapter?.dataSize)!!
                    //                更改point的颜色
                    updateLooperPoint(realPosition)
                }
                if (position == 3) {
                    guide_enter.visibility = View.VISIBLE
                } else {
                    guide_enter.visibility = View.GONE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        guide_enter.setOnClickListener {
            val personDescPresenter = PresentManger.getInstance().personDescPresenter
            val detailDesc = personDescPresenter.detailDesc
            val loginPresenter = PresentManger.getInstance().loginPresenter
            loginPresenter.registerViewCallBack(this)
            loginPresenter.checkLogin(detailDesc?.name, detailDesc?.password)
        }
    }

    private fun updateLooperPoint(realPosition: Int) {
        for (i in 0 until guide_pager_points.childCount) {
            val point: View = guide_pager_points.getChildAt(i)
            if (realPosition == i) {
                point.setBackgroundResource(R.drawable.shape_point_bg_selected)
            } else {
                point.setBackgroundResource(R.drawable.shape_point_bg_normal)
            }
        }
    }

    private fun initView() {
//                创建轮播图适配器
        mAdapter = HomePagerLooperAdapter()
        mAdapter?.setOnLooperItemClickListen(this)
//        设置适配器
        guide_pager_looper.adapter = mAdapter
    }

    override fun onLooperItemClickListen(dataBean: IBaseInfo?) {

    }

    override fun onLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginError() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}