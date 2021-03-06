package com.example.taobaounion.presenter.impl;

import androidx.annotation.NonNull;

import com.example.taobaounion.model.Api;
import com.example.taobaounion.model.bean.OnSellContent;
import com.example.taobaounion.model.dao.Collect;
import com.example.taobaounion.model.dao.UnInsert;
import com.example.taobaounion.presenter.interfaces.IOnSellPresenter;
import com.example.taobaounion.utils.CollectManger;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManger;
import com.example.taobaounion.utils.UnInsertManger;
import com.example.taobaounion.view.IOnSellCallBack;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OnSellPresenterImpl implements IOnSellPresenter {

    private static final int DEFAULT_PAGE = 1;
    private int mCurrentPage = DEFAULT_PAGE;
    private IOnSellCallBack mCallback;
    private boolean isLoading = false;

    @Override
    public void getContent() {

        if (mCallback != null) {
            mCallback.onLoading();
        }
        Call<OnSellContent> task = createCall();
        if (task != null) {
            getContentOrMoreContent(task);
        }
    }

    private void getContentOrMoreContent(Call<OnSellContent> task) {
        task.enqueue(new Callback<OnSellContent>() {
            @Override
            public void onResponse(@NonNull Call<OnSellContent> call, @NonNull Response<OnSellContent> response) {
                int code = response.code();
                LogUtils.d(OnSellPresenterImpl.this, "code == " + code);
                isLoading = false;
                if (code == HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    OnSellContent content = response.body();
                    LogUtils.d(OnSellPresenterImpl.this, "content = " + content);
                    onLoadSuccess(content);
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<OnSellContent> call, @NonNull Throwable t) {
                onLoadError();
                isLoading = false;
            }
        });
    }

    private Call<OnSellContent> createCall() {
        if (isLoading) {
            return null;
        }
        isLoading = true;
        Retrofit retrofit = RetrofitManger.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        return api.getOnSellContent(mCurrentPage);
    }

    private void onLoadSuccess(OnSellContent content) {
        if (mCallback != null) {
            if (content == null || content.getData() == null ||
                    content.getData().getTbk_dg_optimus_material_response().getResult_list() == null
                    || content.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size() == 0) {
                LogUtils.d(this, "null");
                if (mCurrentPage == 1) {
                    mCallback.onEmpty();
                } else {
                    mCallback.onMoreLoadedEmpty();
                }
            } else {
                LogUtils.d(this, "onContentLoaded..." + mCurrentPage);
                if (mCurrentPage == 1) {
                    List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> data = content.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data();
                    List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> beans = unInsertData(data);
                    List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> recommend = recommend(beans);
                    mCallback.onContentLoaded(beans, recommend);
                } else {
                    mCallback.onMoreLoaded(content);
                }
            }
        }
    }

    private List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> unInsertData(List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> recommend) {
        List<UnInsert> insertList = UnInsertManger.getInstance().getInsertList();
        List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> newList = new ArrayList<>();
        if (insertList != null && insertList.size() > 0) {
            for (OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean rec : recommend) {
                boolean isUnInsert = false;
                for (UnInsert unInsert : insertList) {
                    if (unInsert.getTitle().equals(rec.getTitle())) {
                        isUnInsert = true;
                        break;
                    }
                }
                if (!isUnInsert) {
                    newList.add(rec);
                }

            }
            return newList;
        } else {
            return recommend;
        }
    }

    private List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> recommend(List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> data) {
        List<Collect> collectList = CollectManger.getInstance().getCollectList();
        List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> newList = new ArrayList<>();
        if (collectList != null && collectList.size() > 0) {
            Collect collect = collectList.get(0);
            String title = collect.getTitle();
            String substring = title.substring(0, 4);
            for (OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean bean : data) {
                if (bean.getTitle().contains(substring)) {
                    newList.add(bean);
                }
            }
            return newList;
        } else {
            return data;
        }
    }

    private void onLoadError() {
        if (mCallback != null) {
            if (mCurrentPage == 1) {
                mCallback.onError();
            } else {
                mCurrentPage--;
                mCallback.onMoreLoadedError();
            }

        }
    }

    @Override
    public void getMoreContent() {
        mCurrentPage++;
        Call<OnSellContent> task = createCall();
        if (task != null) {
            getContentOrMoreContent(task);
        }
    }

    @Override
    public void reload() {
        this.getContent();
    }

    @Override
    public void registerViewCallBack(IOnSellCallBack callback) {
        mCallback = callback;
    }

    @Override
    public void unRegisterViewCallBack(IOnSellCallBack callback) {
        mCallback = null;
    }
}
