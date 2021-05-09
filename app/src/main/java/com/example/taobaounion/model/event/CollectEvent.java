package com.example.taobaounion.model.event;

import com.example.taobaounion.model.bean.CollectionBean;

import java.util.List;

public class CollectEvent {

    public List<CollectionBean> mList ;

    public CollectEvent(List<CollectionBean> list) {
        mList = list;
    }
}
