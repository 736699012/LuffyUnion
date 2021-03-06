package com.example.taobaounion.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.model.bean.OnSellContent;
import com.example.taobaounion.model.bean.PersonDesc;
import com.example.taobaounion.model.dao.UnInsert;
import com.example.taobaounion.model.dao.User;
import com.example.taobaounion.presenter.interfaces.IOnSellPresenter;
import com.example.taobaounion.presenter.interfaces.IPersonDescPresenter;
import com.example.taobaounion.ui.activity.CollectionActivity;
import com.example.taobaounion.ui.activity.EditPersonMsgActivity;
import com.example.taobaounion.ui.activity.FlashSaleActivity;
import com.example.taobaounion.ui.activity.FootPrintActivity;
import com.example.taobaounion.ui.activity.LoginActivity;
import com.example.taobaounion.ui.adapter.OnSellContentAdapter;
import com.example.taobaounion.ui.custom.GlideCircleTransform;
import com.example.taobaounion.ui.custom.MeCustomTabViewKt;
import com.example.taobaounion.utils.ImageUtils;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.PresentManger;
import com.example.taobaounion.utils.TicketUtil;
import com.example.taobaounion.utils.ToastUtil;
import com.example.taobaounion.utils.UserManger;
import com.example.taobaounion.utils.Utils;
import com.example.taobaounion.view.IOnSellCallBack;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.utils.TbNestedScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

import static com.example.taobaounion.utils.Constant.DEFAULT_USER_NAME;

public class MeFragment extends BaseFragment implements IOnSellCallBack, OnSellContentAdapter.OnItemClickListen, MeCustomTabViewKt.OnItemClickListener {


    @BindView(R.id.me_image)
    public ImageView mAvatar;
    @BindView(R.id.me_name)
    public TextView mName;
    @BindView(R.id.home_pager_content_title)
    public TextView mTitle;
    @BindView(R.id.person_recommend)
    public RecyclerView mRecommend;
    private IPersonDescPresenter mPresenter;
    @BindView(R.id.header_title)
    public TextView headerTitle;
    @BindView(R.id.me_custom_tab)
    public MeCustomTabViewKt mMeCustomTabView;
    @BindView(R.id.recommend_refresh)
    public TwinklingRefreshLayout mRefreshLayout;
    @BindView(R.id.me_head)
    public LinearLayout mHeadLayout;
    @BindView(R.id.me_parent)
    public LinearLayout mMeParent;
    @BindView(R.id.me_tb_nested_scroll_view)
    public TbNestedScrollView mScrollView;


    private OnSellContentAdapter mAdapter;
    private IOnSellPresenter mIOnSellPresenter;

    public final int[] resourceIds = {R.mipmap.collect, R.mipmap.foot_print, R.mipmap.flash_sale};
    public final String[] titleList = {"????????????", "??????", "????????????"};

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView(View view) {
        stateChange(State.SUCCESS);
        EventBus.getDefault().register(this);
//        mPresenter = PresentManger.getInstance().getPersonDescPresenter();
//        PersonDesc desc = mPresenter.getDetailDesc();
        headerTitle.setText("????????????");
        mTitle.setText("???????????????");
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecommend.setLayoutManager(layoutManager);
        mAdapter = new OnSellContentAdapter();
        mRecommend.setAdapter(mAdapter);
        mRefreshLayout.setEnableLoadmore(true);
        mRefreshLayout.setEnableRefresh(false);
        mAdapter.setOnItemClickListen(this);
        User user = UserManger.getInstance().getUser();
        bindView(user);
    }

    @Override
    protected void initListen() {
        mAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditPersonMsgActivity.class);
            startActivity(intent);
        });

        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (mIOnSellPresenter != null) {
                    mIOnSellPresenter.getMoreContent();
                }
            }
        });

        mMeParent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int measuredHeight = 0;
                if (mMeParent != null) {
                    measuredHeight = mMeParent.getMeasuredHeight();
                }
                int headerMeasuredHeight = 0;
                if (mHeadLayout != null) {
                    headerMeasuredHeight = mHeadLayout.getMeasuredHeight();
                }
                if (mScrollView != null) {
                    mScrollView.setHeaderHeight(headerMeasuredHeight);
                }
                if (mRecommend != null) {
                    ViewGroup.LayoutParams layoutParams = mRecommend.getLayoutParams();
                    layoutParams.height = measuredHeight;
                    mRecommend.setLayoutParams(layoutParams);
                }

//                LogUtils.d(HomePagerFragment.this, "measuredHeight " + measuredHeight);

                if (measuredHeight != 0) {
                    mMeParent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(User user) {
        bindView(user);
    }

    @Override
    protected void onRetryClick() {
        super.onRetryClick();
        if (mIOnSellPresenter != null) {
            mIOnSellPresenter.getContent();
        }
    }

    private void bindView(User user) {

        if (user == null) {
            mName.setText(DEFAULT_USER_NAME);
            mAvatar.setImageResource(R.mipmap.blank_face);
            return;
        }
        if (mName != null) {
            mName.setText(user.getUsername());
        }
        if (mAvatar != null) {
            if (TextUtils.isEmpty(user.getCoverUrl())) {
                mAvatar.setImageResource(R.mipmap.blank_face);
            } else {
                Uri uri = ImageUtils.getImageContentUri(mAvatar.getContext(), user.getCoverUrl());
                LogUtils.d(this, "uri = " + uri);
                Glide.with(mAvatar).load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transform(new GlideCircleTransform(getContext()))
                        .into(mAvatar);
            }
        }
        mMeCustomTabView.setOnItemListener(this);
    }

    @Override
    protected void initPresent() {
        mIOnSellPresenter = PresentManger.getInstance().getOnSellPresenter();
        mIOnSellPresenter.registerViewCallBack(this);
        mIOnSellPresenter.getContent();
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_bar_header, container, false);
    }

    @Override
    protected void release() {
        EventBus.getDefault().unregister(this);
        if (mIOnSellPresenter != null) {
            mIOnSellPresenter.unRegisterViewCallBack(this);
        }
    }

//    @Override
//    public void onItemClick(View view) {
//        if (view instanceof MeItemView) {
//
//            int sourceId = ((MeItemView) view).getSourceId();
//            Intent intent = new Intent();
//            switch (sourceId) {
//                case R.mipmap.collect:
//                    intent.setClass(view.getContext(), CollectionActivity.class);
//                    break;
//                case R.mipmap.foot_print:
//                    intent.setClass(view.getContext(), FootPrintActivity.class);
//                    break;
//                case R.mipmap.flash_sale:
//                    break;
//            }
//            startActivity(intent);
//        }
//    }

    @Override
    public void onContentLoaded(List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> list,
                                List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> list2) {
//        ????????????
        stateChange(State.SUCCESS);

        mAdapter.setData(list);
    }

    @Override
    public void onMoreLoaded(OnSellContent moreContent) {
        int size = moreContent.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size();
        ToastUtil.showToast("?????????" + size + "?????????");
        mAdapter.addMoreData(moreContent);
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
        long couponAmount = 0;
        if (dataBean instanceof OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean) {
            couponAmount = ((OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean) dataBean).getCoupon_amount();
        }
        TicketUtil.handle2TaoBao(getContext(), dataBean, couponAmount);
    }

    @Override
    public void onLoseClick(int position, IBaseInfo dataBean) {
        mAdapter.removeData(dataBean, position);
        UnInsert unInsert = new UnInsert();
        unInsert.setCoverUrl(dataBean.getPict_url());
        unInsert.setUrl(dataBean.getLink());
        unInsert.setTitle(dataBean.getTitle());
        Utils.addUnInsert(unInsert);
    }

    @Override
    public void onCollectClick() {
        Intent intent = new Intent();
        intent.setClass(mMeCustomTabView.getContext(), CollectionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFootPrintClick() {
        Intent intent = new Intent();
        intent.setClass(mMeCustomTabView.getContext(), FootPrintActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFlashSaleClick() {
        Intent intent = new Intent();
        intent.setClass(mMeCustomTabView.getContext(), FlashSaleActivity.class);
        startActivity(intent);

    }

    @Override
    public void onExitLoginClick() {
        Intent intent = new Intent();
        intent.setClass(mMeCustomTabView.getContext(), LoginActivity.class);
        IPersonDescPresenter descPresenter = PresentManger.getInstance().getPersonDescPresenter();
        PersonDesc personDesc = descPresenter.getDetailDesc();
        personDesc.setName("");
        personDesc.setPassword("");
        descPresenter.changeDetailDesc(personDesc);
        UserManger.getInstance().setUser(new User());
        startActivity(intent);
    }
}
