package com.example.taobaounion.presenter.impl

import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.example.taobaounion.model.dao.UnInsert
import com.example.taobaounion.presenter.interfaces.IUnInsertPresenter
import com.example.taobaounion.utils.ToastUtil

class UnInsertPresenterImpl : IUnInsertPresenter {


    override fun addUnInsert(unInsert: UnInsert?) {
        unInsert?.save(object : SaveListener<String>() {
            override fun done(p0: String?, e: BmobException?) {
                if (e == null) {
                    ToastUtil.showToast("下次不会在推荐它拉")
                }
            }

        })
    }
}