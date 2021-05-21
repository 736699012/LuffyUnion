package com.example.taobaounion.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.bmob.v3.Bmob
import com.example.taobaounion.R
import com.example.taobaounion.utils.Constant
import com.example.taobaounion.utils.JsonCacheUtil
import com.example.taobaounion.utils.PresentManger
import com.example.taobaounion.view.ILoginCallBack
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), ILoginCallBack {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = JsonCacheUtil.getInstance().sharedPreferences
        val isFirst = sharedPreferences.getBoolean(Constant.KEY_FISRT_ENTER, true)
        if (isFirst) {
            startActivity(Intent(this, GuideActivity::class.java))
            finish()
            return
        }
        setContentView(R.layout.activity_splash)
        Bmob.initialize(this, "d018d65a88bc6dfc85a7b6804e9c758d")
        splash_tv.postDelayed({
            enterMain()
        }, 2000)
    }

    private fun enterMain() {
        val personDescPresenter = PresentManger.getInstance().personDescPresenter
        val detailDesc = personDescPresenter.detailDesc
        val loginPresenter = PresentManger.getInstance().loginPresenter
        loginPresenter.registerViewCallBack(this)
        loginPresenter.checkLogin(detailDesc?.name, detailDesc?.password)
    }

    override fun onLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginError() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}