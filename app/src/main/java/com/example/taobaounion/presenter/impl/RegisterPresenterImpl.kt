package com.example.taobaounion.presenter.impl

import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.example.taobaounion.model.dao.User
import com.example.taobaounion.presenter.interfaces.IRegisterPresenter
import com.example.taobaounion.utils.UserManger
import com.example.taobaounion.view.IRegisterCallBack

class RegisterPresenterImpl : IRegisterPresenter {

    private var mCallBack: IRegisterCallBack? = null

    override fun onRegisterUser(username: String, password: String) {
        val user = User()
        user.username = username
        user.password = password
        user.desc = ""
        user.save(object : SaveListener<String>() {
            override fun done(s: String?, e: BmobException?) {
                if (e == null) {
                    mCallBack?.onRegisterSuccess(username, password)
                } else {
                    mCallBack?.onRegisterError(username)
                }
            }
        })
    }

//    override fun onRegisterUser(username: String, password: String) {
//        val loginRetrofit = RetrofitManger.getInstance().loginRetrofit
//        val api = loginRetrofit.create(LoginApi::class.java)
//        val call = api.registerUser(username, password)
//        call.enqueue(object : Callback<RegisterData> {
//            override fun onFailure(call: Call<RegisterData>, t: Throwable) {
//                mCallBack?.onRegisterError(username)
//            }
//
//            override fun onResponse(call: Call<RegisterData>, response: Response<RegisterData>) {
//                if (response.code() == HttpURLConnection.HTTP_OK) {
//                    if (response.body()?.isSuccessRe!!) {
//                        mCallBack?.onRegisterSuccess(username, password)
//                    } else {
//                        mCallBack?.onRegisterError(username)
//                    }
//                } else {
//                    mCallBack?.onRegisterError(username)
//                }
//            }
//
//        })
//    }

    override fun unRegisterViewCallBack(callback: IRegisterCallBack?) {
        mCallBack = null
    }

    override fun registerViewCallBack(callback: IRegisterCallBack?) {
        mCallBack = callback
    }
}