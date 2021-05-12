package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.bean.CollectionBean;
import com.example.taobaounion.model.dao.Collect;
import com.example.taobaounion.model.event.CollectEvent;
import com.example.taobaounion.presenter.interfaces.ICollectionPresenter;
import com.example.taobaounion.utils.CollectionUtils;
import com.example.taobaounion.utils.JsonCacheUtil;
import com.example.taobaounion.utils.ToastUtil;
import com.example.taobaounion.utils.UserManger;
import com.example.taobaounion.view.ICollectCallBack;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CollectionPresenterImpl implements ICollectionPresenter {

    public static final String KEY_COLLECTION = "key_collection_1";
    private JsonCacheUtil mInstance;
    private ICollectCallBack mCollectCallBack;

    public CollectionPresenterImpl() {
        mInstance = JsonCacheUtil.getInstance();
    }

    @Override
    public void addCollect(Collect collectionBean) {
//        CollectionBean bean = getBean();
//        List<CollectionBean> lists = getCollectionLists();
//        if (lists.contains(collectionBean)) {
//            return;
//        }
//        lists.add(0, collectionBean);
//        bean.setCollectionBeanList(lists);
//        mInstance.saveCache(KEY_COLLECTION, bean);
//        postEvent(new CollectEvent(lists));
        Collect collect = new Collect(collectionBean);
        collect.setUserId(UserManger.getInstance().getUser().getObjectId());
        collect.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtil.showToast("收藏成功");
                    CollectionUtils.changeCollect();
                } else {
                    ToastUtil.showToast("收藏失败");
                }
            }
        });
    }

    @Override
    public void deleteCollect(Collect collectionBean) {
        collectionBean.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    CollectionUtils.changeCollect();
                    ToastUtil.showToast("取消收藏成功");
                } else {
                    ToastUtil.showToast("取消收藏失败");
                }
            }
        });
//        CollectionBean bean = getBean();
//        List<CollectionBean> lists = getCollectionLists();
//        lists.remove(collectionBean);
//        bean.setCollectionBeanList(lists);
//        mInstance.saveCache(KEY_COLLECTION, bean);
//        postEvent(new CollectEvent(lists));
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

    @Override
    public void getListByIntent() {
        if (mCollectCallBack != null) {
            mCollectCallBack.onLoading();
        }
        BmobQuery<Collect> query = new BmobQuery<>();
        query.findObjects(new FindListener<Collect>() {
            @Override
            public void done(List<Collect> list, BmobException e) {
                if (e == null) {
                    List<Collect> filter = new ArrayList<>();
                    if (list == null || list.size() == 0) {
                        if (mCollectCallBack != null) {
                            mCollectCallBack.onEmpty();
                        }
                        return;
                    }
                    for (Collect temp : list) {
                        if (temp.getUserId().equals(UserManger.getInstance().getUser().getObjectId())) {
                            filter.add(temp);
                        }
                    }
                    if (filter.size() == 0) {
                        if (mCollectCallBack != null) {
                            mCollectCallBack.onEmpty();
                        }
                    } else {
                        if (mCollectCallBack != null) {
                            mCollectCallBack.onSuccess(filter);
                        }
                    }

                } else {
                    if (mCollectCallBack != null) {
                        mCollectCallBack.onError();
                    }
                }
            }
        });
    }

    @Override
    public void registerViewCallBack(ICollectCallBack callback) {
        mCollectCallBack = callback;
    }

    @Override
    public void unRegisterViewCallBack(ICollectCallBack callback) {
        mCollectCallBack = null;
    }
}
