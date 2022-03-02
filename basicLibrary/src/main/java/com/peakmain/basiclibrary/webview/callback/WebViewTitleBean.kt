package com.peakmain.basiclibrary.webview.callback

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
    val titleColor: Int =R.color.ui_color_4A4A4A,//标题的颜色
    val isHideTitleText: Boolean = false,//隐藏标题
    val isShowRightArrow: Boolean = false,//显示右边的箭头
    val leftTitleText: String = "",//设置左边的文字
    val isHideLeftText: Boolean = false,
    val isShowBackArrow: Boolean = false,
    val isShowToolbarTitle: Boolean = false,
    val alertRightImageIcon: Int = R.drawable.ic_more,
    val alertLeftBackIcon: Int = R.drawable.library_ic_left_black_back,
    val leftClick: View.OnClickListener? = null,
    val isHideRightView: Boolean = false,
    val toolbarBackgroundColor: Int = android.R.color.white//修改背景颜色
) : Serializable