package com.peakmain.basiclibrary.bean

import java.io.Serializable

/**
 * author ：Peakmain
 * createTime：2022/6/6
 * mail:2726449200@qq.com
 * describe：
 */
data class WebViewConfigBean(
    var url: String? = null,
    var model: Int = 2,
    var statusBarColor: Int? = null,
    var statusBarAlpha: Int = 1,
    var titleBean: WebViewTitleBean? = null
) : Serializable