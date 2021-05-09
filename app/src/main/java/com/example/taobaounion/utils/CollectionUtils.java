package com.example.taobaounion.utils;

import android.widget.ImageView;

import com.example.taobaounion.R;
import com.example.taobaounion.model.bean.CollectionBean;
import com.example.taobaounion.presenter.interfaces.ICollectionPresenter;

import java.util.List;

public class CollectionUtils {

    private static ICollectionPresenter mCollectionPresenter;


    public static void changeCollectIcon(ImageView imageView, CollectionBean collectionBean) {
        if (imageView == null) {
            return;
        }
        if (isCollected(collectionBean)) {
            imageView.setImageResource(R.mipmap.collected);
        }
        imageView.setOnClickListener(v -> {
            if (isCollected(collectionBean)) {
                mCollectionPresenter.deleteCollect(collectionBean);
                imageView.setImageResource(R.mipmap.collect);

            } else {
                mCollectionPresenter.addCollect(collectionBean);
                imageView.setImageResource(R.mipmap.collected);
            }
        });
    }

    private static boolean isCollected(CollectionBean collectionBean) {

        mCollectionPresenter = PresentManger.getInstance().getCollectionPresenter();
        List<CollectionBean> lists = mCollectionPresenter.getCollectionLists();

        return lists.contains(collectionBean);
    }
}
