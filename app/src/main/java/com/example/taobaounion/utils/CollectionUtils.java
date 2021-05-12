package com.example.taobaounion.utils;

import android.widget.ImageView;

import com.example.taobaounion.R;
import com.example.taobaounion.model.dao.Collect;
import com.example.taobaounion.presenter.interfaces.ICollectionPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CollectionUtils {

    private static ICollectionPresenter mCollectionPresenter;


    public static void changeCollectIcon(ImageView imageView, Collect collectionBean) {
        if (imageView == null) {
            return;
        }
        collectionBean.setUserId(UserManger.getInstance().getUser().getObjectId());
        Collect collect = isCollected(collectionBean);
        final boolean[] isCollectI = {collect != collectionBean};
        if (isCollectI[0]) {
            imageView.setImageResource(R.mipmap.collected);
        }else{
            imageView.setImageResource(R.mipmap.collect);
        }
        imageView.setOnClickListener(v -> {
            if (isCollectI[0]) {
                mCollectionPresenter.deleteCollect(collect);
                imageView.setImageResource(R.mipmap.collect);
                isCollectI[0] = false;
            } else {
                mCollectionPresenter.addCollect(collect);
                imageView.setImageResource(R.mipmap.collected);
                isCollectI[0] = true;
            }
        });
    }

    public static Collect isCollected(Collect collect) {

        mCollectionPresenter = PresentManger.getInstance().getCollectionPresenter();
        List<Collect> collectList = CollectManger.getInstance().getCollectList();
        for (Collect data : collectList) {
            if (data.equals(collect)) {
                return data;
            }
        }
        return collect;
//        List<CollectionBean> lists = mCollectionPresenter.getCollectionLists();

//        return lists.contains(collectionBean);
    }

    public static void changeCollect() {
        BmobQuery<Collect> query = new BmobQuery<>();
        query.findObjects(new FindListener<Collect>() {
            @Override
            public void done(List<Collect> list, BmobException e) {
                if (e == null) {
                    List<Collect> filter = new ArrayList<>();
                    if(list != null){
                        for (Collect temp : list) {
                            if (temp.getUserId().equals(UserManger.getInstance().getUser().getObjectId())) {
                                filter.add(temp);
                            }
                        }
                    }
                    CollectManger.getInstance().setCollectList(filter);
                }
            }
        });
    }
}
