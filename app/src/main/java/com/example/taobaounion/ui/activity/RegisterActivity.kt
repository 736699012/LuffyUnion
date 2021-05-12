package com.example.taobaounion.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.example.taobaounion.R
import com.example.taobaounion.model.dao.User
import com.example.taobaounion.ui.custom.NewsDialog
import com.example.taobaounion.utils.PresentManger
import com.example.taobaounion.utils.UserManger
import com.example.taobaounion.view.IRegisterCallBack
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), IRegisterCallBack {

    var newsDialog: NewsDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initListen()
    }

    private fun initListen() {
        val registerPresenter = PresentManger.getInstance().registerPresenter
        registerPresenter.registerViewCallBack(this)
        register_bt.setOnClickListener {
            if (TextUtils.isEmpty(register_username.text)) {
                if (newsDialog == null) {
                    newsDialog = NewsDialog(this@RegisterActivity)
                }
                newsDialog?.setContent("你输入的用户名为空")
                newsDialog?.show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(register_password.text)) {
                if (newsDialog == null) {
                    newsDialog = NewsDialog(this@RegisterActivity)
                }
                newsDialog?.setContent("你输入的密码为空")
                newsDialog?.show()
                return@setOnClickListener
            }
            registerPresenter.onRegisterUser(register_username.text.toString(), register_password.text.toString())
        }
    }

    override fun onRegisterSuccess(username: String, password: String) {
        val bmobQuery = BmobQuery<User>()
        val user = User()
        user.username = username
        user.password = password
        bmobQuery.findObjects(object : FindListener<User>() {
            override fun done(result: MutableList<User>?, error: BmobException?) {
                if (error == null) {
                    result?.forEach {
                        if (it == user) {
                            UserManger.getInstance().user = it
                        }
                    }
                }
            }
        })
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onRegisterError(username: String) {
        if (newsDialog == null) {
            newsDialog = NewsDialog(this@RegisterActivity)
        }
        newsDialog?.setContent("注册失败")
        newsDialog?.show()
    }
}