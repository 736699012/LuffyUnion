package com.example.taobaounion.ui.custom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.LinearLayout
import com.example.taobaounion.R
import com.example.taobaounion.utils.SizeUtils
import kotlinx.android.synthetic.main.news_dialog.*

class NewsDialog constructor(context: Context) : Dialog(context) {

    init {
//        window?.setLayout((SizeUtils.getScreenWidth(context) / 4 * 3), LinearLayout.LayoutParams.WRAP_CONTENT);
        setContentView(R.layout.news_dialog)
        dialog_affirm.setOnClickListener {
            dismiss()
        }
    }

    fun setContent(content: String) {
        dialog_content?.text = content
    }


}