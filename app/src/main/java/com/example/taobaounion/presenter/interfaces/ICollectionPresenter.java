package com.example.taobaounion.presenter.interfaces;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.model.bean.CollectionBean;
import com.example.taobaounion.model.dao.Collect;
import com.example.taobaounion.view.ICollectCallBack;

import java.util.List;

public interface ICollectionPresenter extends IBasePresenter<ICollectCallBack> {


    // 添加收藏
    void addCollect(Collect collectionBean);

    // 取消收藏
    void deleteCollect(Collect collectionBean);

    //收藏列表
    List<CollectionBean> getCollectionLists();

    void getListByIntent();


}
