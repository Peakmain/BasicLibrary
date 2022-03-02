package com.peakmain.basiclibrary.webview.callback

import android.graphics.Color
import android.view.View
import com.peakmain.basiclibrary.R
import java.io.Serializable

/**
 * author ：Peakmain
 * createTime：2022/3/2
 * mail:2726449200@qq.com
 * describe：
 */
data class WebViewTitleBean(
    val title: String = "",
    val titleColor: Int = android.R.color.white,
    val leftClick: View.OnClickListener? = null,
    val isHideRightView: Boolean = false,
    val toolbarBackgroundColor: Int = R.color.ui_color_2F73F6
) : Serializable