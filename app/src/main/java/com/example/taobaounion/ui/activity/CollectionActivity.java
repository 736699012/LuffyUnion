package com.example.taobaounion.ui.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseActivity;
import com.example.taobaounion.model.bean.CollectionBean;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.presenter.interfaces.ICollectionPresenter;
import com.example.taobaounion.ui.adapter.CollectionAdapter;
import com.example.taobaounion.utils.PresentManger;
import com.example.taobaounion.utils.TicketUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;

import static com.example.taobaounion.base.BaseApplication.getContext;

public class CollectionActivity extends BaseActivity implements CollectionAdapter.OnItemClickListen {

    @BindView(R.id.collect_content)
    public RecyclerView mRecyclerView;
    @BindView(R.id.collect_refresh)
    public TwinklingRefreshLayout mRefreshLayout;
    public static final int DEFAULT_SPAN_COUNT = 2;
    private CollectionAdapter mCollectionAdapter;
    private ICollectionPresenter mCollectionPresenter;

    @Override
    protected void initPresent() {
        mCollectionPresenter = PresentManger.getInstance().getCollectionPresenter();
        List<CollectionBean> lists = mCollectionPresenter.getCollectionLists();
        mCollectionAdapter.setData(lists);
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
                    List<CollectionBean> lists = mCollectionPresenter.getCollectionLists();
                    mCollectionAdapter.setData(lists);
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
    public void cancelCollect(int pos, CollectionBean collectionBean) {
        if (mCollectionAdapter != null) {
            mCollectionAdapter.remove(pos, collectionBean);
        }
    }


    @Override
    protected void recycle() {
        super.recycle();
//        EventBus.getDefault().unregister(this);
    }
}
