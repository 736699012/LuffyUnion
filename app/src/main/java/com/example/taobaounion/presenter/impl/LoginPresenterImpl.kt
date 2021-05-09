package com.example.taobaounion.presenter.impl

import com.example.taobaounion.model.LoginApi
import com.example.taobaounion.model.bean.LoginData
import com.example.taobaounion.presenter.interfaces.ILoginPresenter
import com.example.taobaounion.utils.RetrofitManger
import com.example.taobaounion.view.ILoginCallBack
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class LoginPresenterImpl : ILoginPresenter {

    private var callback: ILoginCallBack? = null
    override fun unRegisterViewCallBack(callback: ILoginCallBack?) {
        this.callback = null
    }

    override fun checkLogin(username: String?, password: String?) {
        val loginRetrofit = RetrofitManger.getInstance().loginRetrofit
        val api = loginRetrofit.create(LoginApi::class.java)
        val call = api.checkLogin(username, password)
        call.enqueue(object : Callback<LoginData> {
            override fun onFailure(call: Call<LoginData>, t: Throwable) {
                callback?.onLoginError()
            }

            override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                response.code()
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    if (response.body()?.isLogin!!) {
                        callback?.onLoginSuccess()
                    } else {
                        callback?.onLoginError()
                    }
                }
            }

        })
    }

    override fun registerViewCallBack(callback: ILoginCallBack?) {
        this.callback = callback
    }


}