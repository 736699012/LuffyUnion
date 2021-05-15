package com.example.taobaounion.ui.fragment;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.bean.ChoicenessCategories;
import com.example.taobaounion.model.bean.ChoicenessContent;
import com.example.taobaounion.model.bean.IBaseInfo;
import com.example.taobaounion.model.dao.UnInsert;
import com.example.taobaounion.presenter.interfaces.IChoicenessPresenter;
import com.example.taobaounion.ui.adapter.ChoicenessCategoriesListAdapter;
import com.example.taobaounion.ui.adapter.ChoicenessContentAdapter;
import com.example.taobaounion.ui.custom.LoadingView;
import com.example.taobaounion.utils.Constant;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.PresentManger;
import com.example.taobaounion.utils.SizeUtils;
import com.example.taobaounion.utils.TicketUtil;
import com.example.taobaounion.utils.Utils;
import com.example.taobaounion.view.IChoicenessCallBack;

import java.util.List;

import butterknife.BindView;

public class ChoicenessFragment extends BaseFragment implements IChoicenessCallBack, ChoicenessCategoriesListAdapter.OnLeftItemClickListen, ChoicenessContentAdapter.OnItemClickListen {

    private IChoicenessPresenter mChoicenessPresenter;
    @BindView(R.id.left_category_list)
    public RecyclerView leftCategoryList;
    @BindView(R.id.right_content_list)
    public RecyclerView rightContentList;
    @BindView(R.id.choice_loading)
    public LoadingView mLoadingView;
    private ChoicenessCategoriesListAdapter mCategoriesListAdapter;
    private ChoicenessContentAdapter mContentAdapter;
    @BindView(R.id.header_title)
    public TextView headerTitle;

    @Override
    protected int getRootViewId() {
        return R.layout.fragment_choiceness;
    }

    @Override
    protected void initView(View view) {
        stateChange(State.SUCCESS);
        headerTitle.setText(R.string.text_choice_title);
        mCategoriesListAdapter = new ChoicenessCategoriesListAdapter();
        //            设置布局管理
        leftCategoryList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //            设置适配器
        leftCategoryList.setAdapter(mCategoriesListAdapter);
        leftCategoryList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int topOrBottom = SizeUtils.dip2px(leftCategoryList.getContext(), 10);
                int leftOrRight = SizeUtils.dip2px(leftCategoryList.getContext(), 6);
                outRect.top = topOrBottom;
                outRect.bottom = topOrBottom;
                outRect.right = leftOrRight;
                outRect.left = leftOrRight;
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(rightContentList.getContext(), 2);
        rightContentList.setLayoutManager(gridLayoutManager);
        mContentAdapter = new ChoicenessContentAdapter();
        rightContentList.setAdapter(mContentAdapter);
        rightContentList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int topOrBottom = SizeUtils.dip2px(rightContentList.getContext(), 8);
                int leftOrRight = SizeUtils.dip2px(rightContentList.getContext(), 6);
                outRect.top = topOrBottom;
                outRect.bottom = topOrBottom;
                outRect.right = leftOrRight;
                outRect.left = leftOrRight;
            }
        });
    }

    @Override
    protected void initPresent() {
        mChoicenessPresenter = PresentManger.getInstance().getChoicenessPresenter();
        mChoicenessPresenter.registerViewCallBack(this);
        mChoicenessPresenter.getCategories();
    }

    @Override
    protected void initListen() {
        mCategoriesListAdapter.setOnLeftItemClickListen(this);
        mContentAdapter.setOnItemClickListen(this);
    }

    @Override
    protected void release() {
        mChoicenessPresenter.unRegisterViewCallBack(this);
    }

    @Override
    public void onCategoriesLoad(ChoicenessCategories categories) {
        stateChange(State.SUCCESS);
        LogUtils.d(this, "category  ==" + categories.toString());
//        mChoicenessPresenter.getContentByCategory(categories.getData().get(0));
        if (leftCategoryList != null) {
            mCategoriesListAdapter.setData(categories.getData());
        }
    }

    @Override
    protected void onRetryClick() {
        if (mChoicenessPresenter != null) {
            mChoicenessPresenter.reloadContent();
        }
    }

    @Override
    public void onContentLoad(ChoicenessContent content) {
        mLoadingView.setVisibility(View.GONE);
        rightContentList.setVisibility(View.VISIBLE);
        if (content != null) {
            if (content.getCode() == Constant.SUCCESS_CODE) {
                if (content.getData() != null && content.getData().getTbk_dg_optimus_material_response() != null) {
                    List<ChoicenessContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean> uatm_tbk_item = content.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data();
                    Utils.filterList(uatm_tbk_item);
                    mContentAdapter.setData(uatm_tbk_item);
                    rightContentList.scrollToPosition(0);
                }
            }
        }
    }

    @Override
    public void onContentLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        rightContentList.setVisibility(View.GONE);
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_bar_header, container, false);
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

    }

    @Override
    public void onLeftItemClick(ChoicenessCategories.DataBean dataBean) {
        LogUtils.d(this, "title  " + dataBean.getFavorites_title());
        if (mChoicenessPresenter != null) {
            mChoicenessPresenter.getContentByCategory(dataBean);
        }
    }

    @Override
    public void onItemClick(IBaseInfo itemBean) {
        long couponAmount = 0;
        if (itemBean instanceof ChoicenessContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean) {
            String coupon_info = ((ChoicenessContent.DataBean.TbkUatmFavoritesItemGetResponseBean.ResultsBean.UatmTbkItemBean) itemBean).getCoupon_info();
            if (coupon_info.split("减").length >= 2) {
                String temp = coupon_info.split("减")[1];
                String couponStr = temp.replace("元", "");
                couponAmount = Long.parseLong(couponStr);
            }
        }
        TicketUtil.handle2TaoBao(getContext(), itemBean, couponAmount);
    }

    @Override
    public void onLoseClick(int position, IBaseInfo dataBean) {
        mContentAdapter.removeData(dataBean, position);
        UnInsert unInsert = new UnInsert();
        unInsert.setCoverUrl(dataBean.getPict_url());
        unInsert.setUrl(dataBean.getLink());
        unInsert.setTitle(dataBean.getTitle());
        Utils.addUnInsert(unInsert);
    }
}
