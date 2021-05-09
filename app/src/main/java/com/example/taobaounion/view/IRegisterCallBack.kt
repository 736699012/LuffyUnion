package com.example.taobaounion.view

interface IRegisterCallBack {

    fun onRegisterSuccess(username: String, password: String)

    fun onRegisterError(username: String)

}