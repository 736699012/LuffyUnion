package com.example.taobaounion.ui.activity;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseActivity;
import com.example.taobaounion.model.bean.CollectionBean;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.model.dao.Collect;
import com.example.taobaounion.presenter.interfaces.ICollectionPresenter;
import com.example.taobaounion.ui.adapter.CollectionAdapter;
import com.example.taobaounion.ui.custom.LoadingView;
import com.example.taobaounion.utils.CollectionUtils;
import com.example.taobaounion.utils.PresentManger;
import com.example.taobaounion.utils.TicketUtil;
import com.example.taobaounion.view.ICollectCallBack;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;

import static com.example.taobaounion.base.BaseApplication.getContext;

public class CollectionActivity extends BaseActivity implements CollectionAdapter.OnItemClickListen, ICollectCallBack {

    @BindView(R.id.collect_content)
    public RecyclerView mRecyclerView;
    @BindView(R.id.collect_refresh)
    public TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.collect_loading)
    public LoadingView mLoadingView;
    @BindView(R.id.collect_empty)
    public TextView mEmpty;
    public static final int DEFAULT_SPAN_COUNT = 2;
    private CollectionAdapter mCollectionAdapter;
    private ICollectionPresenter mCollectionPresenter;

    @Override
    protected void initPresent() {
        mCollectionPresenter = PresentManger.getInstance().getCollectionPresenter();
        mCollectionPresenter.registerViewCallBack(this);
        mCollectionPresenter.getListByIntent();
//        List<CollectionBean> lists = mCollectionPresenter.getCollectionLists();
//        mCollectionAdapter.setData(lists);
    }

    @Override
    protected void initView() {
//        EventBus.getDefault().register(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mCollectionAdapter = new CollectionAdapter();
        mRecyclerView.setAdapter(mCollectionAdapter);
        mCollectionAdapter.setOnItemClickListen(this);
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                if (mCollectionPresenter != null) {
                    mCollectionPresenter.getListByIntent();
//                    List<CollectionBean> lists = mCollectionPresenter.getCollectionLists();
//                    mCollectionAdapter.setData(lists);
                }
                refreshLayout.finishRefreshing();
            }
        });
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(CollectEvent event) {
//        if (event.mList != null) {
//            mCollectionAdapter.setData(event.mList);
//        }
//    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    public void onItemClick(IBaseInfo dataBean) {
        long couponAmount = 0;
        if (dataBean instanceof CollectionBean) {
            couponAmount = ((CollectionBean) dataBean).getCoupons();
        }
        TicketUtil.handle2TaoBao(getBaseContext(), dataBean, couponAmount);
    }

    @Override
    public void cancelCollect(int pos, Collect collectionBean) {
        if (mCollectionAdapter != null) {
            ICollectionPresenter collectionPresenter = PresentManger.getInstance().getCollectionPresenter();
            Collect collected = CollectionUtils.isCollected(collectionBean);
            collectionPresenter.deleteCollect(collectionBean);
            mCollectionAdapter.remove(pos, collectionBean);
        }
    }


    @Override
    protected void recycle() {
        super.recycle();
        mCollectionPresenter.unRegisterViewCallBack(this);
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSuccess(List<Collect> list) {
        mCollectionAdapter.setData(list);
        mLoadingView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void onEmpty() {
        mLoadingView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);
    }
}
