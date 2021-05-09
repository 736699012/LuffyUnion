package com.example.taobaounion.presenter.impl

import com.example.taobaounion.model.LoginApi
import com.example.taobaounion.model.bean.RegisterData
import com.example.taobaounion.presenter.interfaces.IRegisterPresenter
import com.example.taobaounion.utils.RetrofitManger
import com.example.taobaounion.view.IRegisterCallBack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

class RegisterPresenterImpl : IRegisterPresenter {

    private var mCallBack: IRegisterCallBack? = null

    override fun onRegisterUser(username: String, password: String) {
        val loginRetrofit = RetrofitManger.getInstance().loginRetrofit
        val api = loginRetrofit.create(LoginApi::class.java)
        val call = api.registerUser(username, password)
        call.enqueue(object : Callback<RegisterData> {
            override fun onFailure(call: Call<RegisterData>, t: Throwable) {
                mCallBack?.onRegisterError(username)
            }

            override fun onResponse(call: Call<RegisterData>, response: Response<RegisterData>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    if (response.body()?.isSuccessRe!!) {
                        mCallBack?.onRegisterSuccess(username, password)
                    } else {
                        mCallBack?.onRegisterError(username)
                    }
                } else {
                    mCallBack?.onRegisterError(username)
                }
            }

        })
    }

    override fun unRegisterViewCallBack(callback: IRegisterCallBack?) {
        mCallBack = null
    }

    override fun registerViewCallBack(callback: IRegisterCallBack?) {
        mCallBack = callback
    }
}