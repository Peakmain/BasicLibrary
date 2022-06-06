package com.peakmain.basiclibrary.bean

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
    val titleText: String? = null,//标题文本
    val titleTextSize: Float = 16f,//标题字体大小
    val isTitleTextBold:Boolean=true,//标题是否加粗
    val titleColor: Int = R.color.ui_color_4A4A4A,//标题的颜色
    val isShowHomeAsUp: Boolean = true,//左上角是否显示返回图标
    val isHideTitleText: Boolean = false,//隐藏标题
    val isShowRightIcon: Boolean = false,//显示右边的图标
    val leftText: String = "",//设置左边的文字
    val isHideLeftText: Boolean = false,//是否隐藏左边的标题
    val isShowBackArrow: Boolean = false,//是否显示返回按钮
    val isShowToolbarTitle: Boolean = false,//是否显示Toolbar的标题
    val rightImageIcon: Int = R.drawable.ic_more,//修改右边图标
    val rightViewClickListener: View.OnClickListener?=null,
    val leftImageIcon: Int = R.drawable.library_ic_left_black_back,
    val customLeftBackIcon: Int = R.drawable.library_ic_left_black_back,
    val leftViewClickListener: View.OnClickListener? = null,
    val toolbarBackgroundColor: Int = android.R.color.white//修改背景颜色
) : Serializable