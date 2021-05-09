package com.example.taobaounion.presenter.interfaces;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.view.ILoginCallBack;

public interface ILoginPresenter extends IBasePresenter<ILoginCallBack> {

    void checkLogin(String username, String password);
}
