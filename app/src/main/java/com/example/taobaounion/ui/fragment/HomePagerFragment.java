package com.example.taobaounion.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.bean.Categories;
import com.example.taobaounion.model.bean.HomePagerContent;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.model.bean.ILinearInfo;
import com.example.taobaounion.model.dao.UnInsert;
import com.example.taobaounion.presenter.interfaces.ICategoryPagerPresenter;
import com.example.taobaounion.ui.adapter.HomePagerContentAdapter;
import com.example.taobaounion.ui.adapter.RecommendAdapter;
import com.example.taobaounion.utils.Constant;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.PresentManger;
import com.example.taobaounion.utils.SizeUtils;
import com.example.taobaounion.utils.TicketUtil;
import com.example.taobaounion.utils.ToastUtil;
import com.example.taobaounion.utils.Utils;
import com.example.taobaounion.view.ICategoryPagerCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.utils.TbNestedScrollView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback, HomePagerContentAdapter.OnItemClickListen, RecommendAdapter.OnRecommendItemClickListener {


    private ICategoryPagerPresenter mICategoryPagerPresenter;
    private int mMaterialId;

    @BindView(R.id.home_pager_content_list)
    public RecyclerView mContentList;
    //    @BindView(R.id.home_pager_looper)
//    public AutoLooperViewPager mLooper;
    @BindView(R.id.home_pager_content_title)
    public TextView mTitle;
    //    @BindView(R.id.home_pager_points)
//    public LinearLayout mLinearLayout;
    @BindView(R.id.home_pager_parent)
    public LinearLayout homePagerParent;
    @BindView(R.id.home_pager_header)
    public LinearLayout homePagerHeader;
    @BindView(R.id.home_tb_nested_scroll_view)
    public TbNestedScrollView mTbNestedScrollView;
    @BindView(R.id.home_pager_refresh)
    public TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.home_pager_recommend)
    public RecyclerView mRecommend;

    private HomePagerContentAdapter mHomePagerContentAdapter;
    //    private HomePagerLooperAdapter mHomePagerLooperAdapter;
    private RecommendAdapter mRecommendAdapter;

    public static HomePagerFragment newInstance(Categories.DataBean dataBean, int position) {
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_HOME_PAGER_TITLE, dataBean.getTitle());
        bundle.putInt(Constant.KEY_HOME_PAGER_MATERIAL_ID, dataBean.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }


    @Override
    protected int getRootViewId() {
        return R.layout.fragment_home_pager;
    }


    @Override
    public void onResume() {
        super.onResume();
//        mLooper.startAutoLooper();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mLooper.stopAutoLooper();
    }

    @Override
    protected void onRetryClick() {
        mICategoryPagerPresenter.reload(mMaterialId);
    }

    @Override
    protected void initView(View view) {
//        ????????????????????????
        mContentList.setLayoutManager(new LinearLayoutManager(getContext()));

//        ???????????????
        mHomePagerContentAdapter = new HomePagerContentAdapter();
//        ???????????????
        mContentList.setAdapter(mHomePagerContentAdapter);
        mHomePagerContentAdapter.setOnItemClickListen(this);
//        ????????????????????????
//        mHomePagerLooperAdapter = new HomePagerLooperAdapter();
//        mHomePagerLooperAdapter.setOnLooperItemClickListen(this);
//        ???????????????
//        mLooper.setAdapter(mHomePagerLooperAdapter);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadmore(true);
//        ??????????????????
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (mICategoryPagerPresenter != null) {
                    mICategoryPagerPresenter.loaderMore(mMaterialId);
                }
            }
        });
        mRecommend.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mRecommendAdapter = new RecommendAdapter();
        mRecommendAdapter.setOnRecommendListener(this);
        mRecommend.setAdapter(mRecommendAdapter);
        mRecommend.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = SizeUtils.dip2px(view.getContext(), 5f);
                outRect.top = SizeUtils.dip2px(view.getContext(), 5f);
                outRect.left = SizeUtils.dip2px(view.getContext(), 5f);
                outRect.right = SizeUtils.dip2px(view.getContext(), 5f);
            }
        });
    }

    @Override
    protected void initListen() {
        homePagerParent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int measuredHeight = 0;
                if (homePagerParent != null) {
                    measuredHeight = homePagerParent.getMeasuredHeight();
                }
                int headerMeasuredHeight = 0;
                if (homePagerHeader != null) {
                    headerMeasuredHeight = homePagerHeader.getMeasuredHeight();
                }
                if (mTbNestedScrollView != null) {
                    mTbNestedScrollView.setHeaderHeight(headerMeasuredHeight);
                }
                if (mContentList != null) {
                    ViewGroup.LayoutParams layoutParams = mContentList.getLayoutParams();
                    layoutParams.height = measuredHeight;
//                    mContentList.setLayoutParams(layoutParams);
                }

//                LogUtils.d(HomePagerFragment.this, "measuredHeight " + measuredHeight);

                if (measuredHeight != 0) {
                    homePagerParent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

            }
        });

//        mLooper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            /**
//             * ??????????????????
//             * @param position
//             */
//            @Override
//            public void onPageSelected(int position) {
//                if (mHomePagerLooperAdapter.getDataSize() != 0) {
//                    int realPosition = position % mHomePagerLooperAdapter.getDataSize();
////                ??????point?????????
//                    updateLooperPoint(realPosition);
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

//    private void updateLooperPoint(int realPosition) {
//        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
//            View point = mLinearLayout.getChildAt(i);
//            if (realPosition == i) {
//                point.setBackgroundResource(R.drawable.shape_point_bg_selected);
//            } else {
//                point.setBackgroundResource(R.drawable.shape_point_bg_normal);
//            }
//        }
//    }

    @Override
    protected void initPresent() {
        mICategoryPagerPresenter = PresentManger.getInstance().getCategoryPagerPresenter();
        mICategoryPagerPresenter.registerViewCallBack(this);
    }

    @Override
    protected void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString(Constant.KEY_HOME_PAGER_TITLE);
            mMaterialId = bundle.getInt(Constant.KEY_HOME_PAGER_MATERIAL_ID);
//            LogUtils.d(this, "title == " + title);
//            LogUtils.d(this, "materialId == " + mMaterialId);
//            ????????????
            mICategoryPagerPresenter.getContentByCategoryId(mMaterialId);
            if (mTitle != null) {
                mTitle.setText(title);
            }
        }

    }

    @Override
    public void onContentLoad(List<HomePagerContent.DataBean> contents) {
        stateChange(State.SUCCESS);
        LogUtils.d(this, "onContentLoad ...");
        Utils.filterList(contents);
        mHomePagerContentAdapter.setData(contents);
    }

    @Override
    public int getCategoryId() {
        return mMaterialId;
    }

    @Override
    public void onError() {
        stateChange(State.ERROR);
    }

    @Override
    public void onEmpty() {
        stateChange(State.EMPTY);
    }

    @Override
    public void onLoading() {
        stateChange(State.LOADING);
    }

    @Override
    public void onLoaderMoreError() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadmore();
        }
        ToastUtil.showToast("???????????????????????????");
    }

    @Override
    public void onLoaderMoreEmpty() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadmore();
        }
        ToastUtil.showToast("?????????????????????");
    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean> contents) {
        mHomePagerContentAdapter.addData(contents);
        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadmore();
        }
        ToastUtil.showToast("?????????" + contents.size() + "?????????");
    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean> contents) {
        mRecommendAdapter.setData(contents);
//        mHomePagerLooperAdapter.setData(contents);
//        int dx = (Integer.MAX_VALUE / 2) % contents.size();
////        ????????????????????????
//        if (mLooper != null) {
//            mLooper.setCurrentItem(Integer.MAX_VALUE / 2 - dx);
//        }
//        if (mLinearLayout != null) {
//            mLinearLayout.removeAllViews();
//            int size = SizeUtils.dip2px(getContext(), 8);
//            int marginLeft = SizeUtils.dip2px(getContext(), 5);
//            for (int i = 0; i < contents.size(); i++) {
//                View point = new View(getContext());
//                if (i == 0) {
//                    point.setBackgroundResource(R.drawable.shape_point_bg_selected);
//                } else {
//                    point.setBackgroundResource(R.drawable.shape_point_bg_normal);
//                }
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
//                layoutParams.leftMargin = marginLeft;
//                layoutParams.rightMargin = marginLeft;
//                point.setLayoutParams(layoutParams);
//                mLinearLayout.addView(point);
//            }
//        }


    }

    @Override
    public void onItemClickListen(IBaseInfo dataBean) {
        LogUtils.d(this, "onItemClickListen... " + dataBean.getTitle());
        long couponAmount = 0;
        if (dataBean instanceof HomePagerContent.DataBean) {
            couponAmount = ((HomePagerContent.DataBean) dataBean).getCoupon_amount();
        }
        handleChangeActivity(dataBean, couponAmount);
    }

    @Override
    public void onLoseClick(int pos, ILinearInfo dataBean, UnInsert unInsert) {
        mHomePagerContentAdapter.removeData(dataBean, pos);
        Utils.addUnInsert(unInsert);
    }

    private void handleChangeActivity(IBaseInfo dataBean, long couponAmount) {
        TicketUtil.handle2TaoBao(getContext(), dataBean, couponAmount);
    }

//    @Override
//    public void onLooperItemClickListen(IBaseInfo dataBean) {
//        LogUtils.d(this, "onLooperItemClickListen... " + dataBean.getTitle());
//        long couponAmount = 0;
//        if (dataBean instanceof HomePagerContent.DataBean) {
//            couponAmount = ((HomePagerContent.DataBean) dataBean).getCoupon_amount();
//        }
//        handleChangeActivity(dataBean, couponAmount);
//    }

    @Override
    public void onRecommendItemClick(@NotNull IBaseInfo dataBean) {
        LogUtils.d(this, "onRecommendItemClick... " + dataBean.getTitle());
        long couponAmount = 0;
        if (dataBean instanceof HomePagerContent.DataBean) {
            couponAmount = ((HomePagerContent.DataBean) dataBean).getCoupon_amount();
        }
        handleChangeActivity(dataBean, couponAmount);
    }
}
