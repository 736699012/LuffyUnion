package com.example.taobaounion.utils;

import com.example.taobaounion.model.dao.Collect;

import java.util.List;

public class CollectManger {

    private List<Collect> mCollectList;

    private static CollectManger mCollectManger = new CollectManger();

    private CollectManger() {

    }

    public static CollectManger getInstance() {
        return mCollectManger;
    }

    public List<Collect> getCollectList() {
        return mCollectList;
    }

    public void setCollectList(List<Collect> collectList) {
        mCollectList = collectList;
    }
}
