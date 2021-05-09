package com.example.taobaounion.presenter.interfaces;

import com.example.taobaounion.model.bean.CollectionBean;

import java.util.List;

public interface ICollectionPresenter {


    // 添加收藏
    void addCollect(CollectionBean collectionBean);

    // 取消收藏
    void deleteCollect(CollectionBean collectionBean);

    //收藏列表
    List<CollectionBean> getCollectionLists();


}
