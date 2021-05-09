package com.example.taobaounion.presenter.interfaces

import com.example.taobaounion.base.IBasePresenter
import com.example.taobaounion.view.IRegisterCallBack

interface IRegisterPresenter : IBasePresenter<IRegisterCallBack> {

    fun onRegisterUser(username: String, password: String)
}