package com.example.taobaounion.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import cn.bmob.v3.Bmob
import com.example.taobaounion.R
import com.example.taobaounion.model.LoginApi
import com.example.taobaounion.model.bean.LoginData
import com.example.taobaounion.model.bean.PersonDesc
import com.example.taobaounion.presenter.interfaces.ILoginPresenter
import com.example.taobaounion.ui.custom.NewsDialog
import com.example.taobaounion.utils.PresentManger
import com.example.taobaounion.utils.RetrofitManger
import com.example.taobaounion.view.ILoginCallBack
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection


class LoginActivity : AppCompatActivity(), ILoginCallBack {


    var newsDialog: NewsDialog? = null
    var loginPresenter: ILoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Bmob.initialize(this,"d018d65a88bc6dfc85a7b6804e9c758d")
        initView()
    }

    private fun initView() {
        loginPresenter = PresentManger.getInstance().loginPresenter
        loginPresenter?.registerViewCallBack(this)
        login_bt.setOnClickListener {
            if (TextUtils.isEmpty(login_username.text)) {
                if (newsDialog == null) {
                    newsDialog = NewsDialog(this@LoginActivity)
                }
                newsDialog?.setContent("你输入的用户名为空")
                newsDialog?.show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(login_password.text)) {
                if (newsDialog == null) {
                    newsDialog = NewsDialog(this@LoginActivity)
                }
                newsDialog?.setContent("你输入的密码为空")
                newsDialog?.show()
                return@setOnClickListener
            }
            loginPresenter?.checkLogin(login_username.text.toString(), login_password.text.toString())
            it.isClickable = false
            login_bt.isEnabled = false
        }
        login_register_tx.setOnClickListener {
            toRegister()
        }
    }

    private fun toRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        this.startActivity(intent)
    }

    override fun onLoginSuccess() {
        val personDescPresenter = PresentManger.getInstance().personDescPresenter
        var detailDesc = personDescPresenter.detailDesc
        if (detailDesc != null) {

            detailDesc.name = login_username.text.toString()
            detailDesc.password = login_password.text.toString()
        } else {
            detailDesc = PersonDesc()
        }
        personDescPresenter.changeDetailDesc(detailDesc)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginError() {
        if (newsDialog == null) {
            newsDialog = NewsDialog(this@LoginActivity)
        }
        newsDialog?.setContent("登陆失败")
        newsDialog?.show()
        login_bt.isEnabled = true
        login_bt.isClickable = true
    }
}