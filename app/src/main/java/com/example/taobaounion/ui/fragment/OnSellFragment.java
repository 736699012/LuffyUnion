package com.example.taobaounion.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.model.bean.OnSellContent;
import com.example.taobaounion.model.dao.UnInsert;
import com.example.taobaounion.presenter.interfaces.IOnSellPresenter;
import com.example.taobaounion.ui.adapter.OnSellContentAdapter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.PresentManger;
import com.example.taobaounion.utils.ToastUtil;
import com.example.taobaounion.utils.Utils;
import com.example.taobaounion.view.IOnSellCallBack;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class OnSellFragment extends BaseFragment implements IOnSellCallBack, OnSellContentAdapter.OnItemClickListen {

    @BindView(R.id.on_sell_content)
    public RecyclerView mOnSellContentList;
    private OnSellContentAdapter mContentAdapter;
    public static final int DEFAULT_SPAN_COUNT = 2;
    private IOnSellPresenter mIOnSellPresenter;

    @BindView(R.id.header_title)
    public TextView headerTitle;

    @BindView(R.id.on_sell_refresh)
    public TwinklingRefreshLayout mRefreshLayout;

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_on_sell;
    }

    @Override
    protected void initView(View view) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT);
        mOnSellContentList.setLayoutManager(gridLayoutManager);
        mContentAdapter = new OnSellContentAdapter();
        mOnSellContentList.setAdapter(mContentAdapter);
        mRefreshLayout.setEnableLoadmore(true);
        mRefreshLayout.setEnableRefresh(false);
        mContentAdapter.setOnItemClickListen(this);
        headerTitle.setText(R.string.text_on_sell_title);
    }

    @Override
    protected void release() {
        if (mIOnSellPresenter != null) {
            mIOnSellPresenter.unRegisterViewCallBack(this);
        }
    }

    @Override
    protected void initListen() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (mIOnSellPresenter != null) {
                    LogUtils.d(OnSellFragment.this, "getMoreContent...");
                    mIOnSellPresenter.getMoreContent();
                }
            }
        });
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_bar_header, container, false);
    }

    @Override
    protected void initPresent() {
        mIOnSellPresenter = PresentManger.getInstance().getOnSellPresenter();
        mIOnSellPresenter.registerViewCallBack(this);
        mIOnSellPresenter.getContent();
    }
//
//    @Override
//    public void onContentLoaded(OnSellContent content) {
////        ????????????
//
//        List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> data = content.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data();
//        Utils.filterList(data);
//
//    }

    @Override
    public void onContentLoaded(List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> list, List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> beanList) {
        stateChange(State.SUCCESS);
        mContentAdapter.setData(list);
    }

    @Override
    public void onMoreLoaded(OnSellContent moreContent) {
        int size = moreContent.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size();
        ToastUtil.showToast("?????????" + size + "?????????");
        mContentAdapter.addMoreData(moreContent);
        mRefreshLayout.finishLoadmore();
    }

    @Override
    public void onMoreLoadedError() {
        ToastUtil.showToast("????????????,????????????");
        mRefreshLayout.finishLoadmore();
    }

    @Override
    public void onMoreLoadedEmpty() {
        ToastUtil.showToast("????????????????????????");
        mRefreshLayout.finishLoadmore();
    }

    @Override
    public void onLoading() {
        stateChange(State.LOADING);
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
    public void onItemClick(IBaseInfo dataBean) {
//        TicketUtil.handle2TaoBao(getContext(),dataBean, couponAmount);
    }

    @Override
    public void onLoseClick(int position, IBaseInfo dataBean) {
        UnInsert unInsert = new UnInsert();
        unInsert.setCoverUrl(dataBean.getPict_url());
        unInsert.setUrl(dataBean.getLink());
        unInsert.setTitle(dataBean.getTitle());
        Utils.addUnInsert(unInsert);
    }
}
