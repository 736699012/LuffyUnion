package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.bean.CollectionBean;
import com.example.taobaounion.model.event.CollectEvent;
import com.example.taobaounion.presenter.interfaces.ICollectionPresenter;
import com.example.taobaounion.utils.JsonCacheUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CollectionPresenterImpl implements ICollectionPresenter {

    public static final String KEY_COLLECTION = "key_collection_1";
    private JsonCacheUtil mInstance;

    public CollectionPresenterImpl() {
        mInstance = JsonCacheUtil.getInstance();
    }

    @Override
    public void addCollect(CollectionBean collectionBean) {
        CollectionBean bean = getBean();
        List<CollectionBean> lists = getCollectionLists();
        if (lists.contains(collectionBean)) {
            return;
        }
        lists.add(0, collectionBean);
        bean.setCollectionBeanList(lists);
        mInstance.saveCache(KEY_COLLECTION, bean);
        postEvent(new CollectEvent(lists));
    }

    @Override
    public void deleteCollect(CollectionBean collectionBean) {
        CollectionBean bean = getBean();
        List<CollectionBean> lists = getCollectionLists();
        lists.remove(collectionBean);
        bean.setCollectionBeanList(lists);
        mInstance.saveCache(KEY_COLLECTION, bean);
        postEvent(new CollectEvent(lists));
    }

    private void postEvent(CollectEvent event) {
        EventBus.getDefault().post(event);
    }

    private CollectionBean getBean() {
        CollectionBean bean = mInstance.getValue(KEY_COLLECTION, CollectionBean.class);
        if (bean == null) {
            bean = new CollectionBean();
        }
        return bean;
    }

    @Override
    public List<CollectionBean> getCollectionLists() {
        return getBean().getCollectionBeanList();
    }

}
