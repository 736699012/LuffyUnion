package com.example.taobaounion.view;

import com.example.taobaounion.base.IBaseCallback;
import com.example.taobaounion.model.dao.Collect;

import java.util.List;

public interface ICollectCallBack extends IBaseCallback {

    void onSuccess(List<Collect> list);

    void onError();
}
