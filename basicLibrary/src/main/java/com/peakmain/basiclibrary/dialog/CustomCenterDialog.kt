package com.peakmain.basiclibrary.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import com.peakmain.basiclibrary.R

/**
 * author ：Peakmain
 * createTime：2021/12/27
 * mail:2726449200@qq.com
 * describe：
 */
class CustomCenterDialog(context: Context, layout: View, style: Int=R.style.CustomDialogThemes) : Dialog(context, style) {

    init {

        setContentView(layout)

        val window = window

        val params = window!!.attributes

        params.gravity = Gravity.CENTER

        window.attributes = params
    }
}