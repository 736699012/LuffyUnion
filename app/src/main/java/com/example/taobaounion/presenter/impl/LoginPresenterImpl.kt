package com.example.taobaounion.presenter.impl

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.example.taobaounion.model.dao.User
import com.example.taobaounion.presenter.interfaces.ILoginPresenter
import com.example.taobaounion.utils.CollectionUtils
import com.example.taobaounion.utils.UserManger
import com.example.taobaounion.view.ILoginCallBack

class LoginPresenterImpl : ILoginPresenter {

    private var callback: ILoginCallBack? = null
    override fun unRegisterViewCallBack(callback: ILoginCallBack?) {
        this.callback = null
    }


    override fun checkLogin(username: String?, password: String?) {
        val user = User()
        user.username = username
        user.password = password
        val bmobQuery = BmobQuery<User>()
        bmobQuery.findObjects(object : FindListener<User>() {
            override fun done(result: MutableList<User>?, error: BmobException?) {
                if (error != null) {
                    callback?.onLoginError()
                } else {
                    var isSuccess = false
                    result?.forEach {
                        if (it == user) {
                            UserManger.getInstance().user = it
                            callback?.onLoginSuccess()
                            isSuccess = true
                        }
                    }
                    if (!isSuccess) {
                        callback?.onLoginError()
                    }
                }
            }
        })
    }

//    override fun checkLogin(username: String?, password: String?) {
//        val loginRetrofit = RetrofitManger.getInstance().loginRetrofit
//        val api = loginRetrofit.create(LoginApi::class.java)
//        val call = api.checkLogin(username, password)
//        call.enqueue(object : Callback<LoginData> {
//            override fun onFailure(call: Call<LoginData>, t: Throwable) {
//                callback?.onLoginError()
//            }
//
//            override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
//                response.code()
//                if (response.code() == HttpURLConnection.HTTP_OK) {
//                    if (response.body()?.isLogin!!) {
//                        callback?.onLoginSuccess()
//                    } else {
//                        callback?.onLoginError()
//                    }
//                }
//            }
//
//        })
//    }

    override fun registerViewCallBack(callback: ILoginCallBack?) {
        this.callback = callback
    }


}