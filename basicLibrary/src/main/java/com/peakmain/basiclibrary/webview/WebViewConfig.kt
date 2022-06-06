package com.peakmain.basiclibrary.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.annotation.IntRange
import com.peakmain.basiclibrary.base.BaseOneSingleton
import com.peakmain.basiclibrary.bean.WebViewConfigBean
import com.peakmain.basiclibrary.bean.WebViewTitleBean
import com.peakmain.basiclibrary.helper.WebViewHelper
import java.lang.ref.WeakReference

/**
 * author ：Peakmain
 * createTime：2022/6/6
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewConfig(val contextRef: WeakReference<Context>) {
    companion object : BaseOneSingleton<Context, WebViewConfig>() {
        const val MODE_LIGHT = 1
        const val MODE_DARK = 2
        override fun createSingleton(params: Context): WebViewConfig =
            WebViewConfig(WeakReference(params))
    }

    private val webViewConfigBean = WebViewConfigBean()


    /**
     * MODE_LIGHT:白底黑字
     * MODE_DARK：黑底白字
     */
    @IntDef(
        MODE_LIGHT, MODE_DARK
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class StatusState


    /**
     * 设置url
     */
    fun url(url: String): WebViewConfig {
        webViewConfigBean.url = url
        return this
    }

    /**
     * 设置状态栏的model，默认是黑底白字
     */
    fun statusMode(@StatusState model: Int = MODE_DARK): WebViewConfig {
        webViewConfigBean.model = model
        return this
    }

    /**
     * 设置状态栏的颜色
     */
    fun statusBarColor(@ColorInt color: Int): WebViewConfig {
        webViewConfigBean.statusBarColor = color
        return this
    }

    /**
     * 设置状态栏的颜色
     */
    fun statusBarAlpha(@IntRange(from = 0, to = 255) statusBarAlpha: Int): WebViewConfig {
        webViewConfigBean.statusBarAlpha = statusBarAlpha
        return this
    }

    /**
     * 设置标题
     */
    fun titleBean(titleBean: WebViewTitleBean? = WebViewTitleBean()): WebViewConfig {
        webViewConfigBean.titleBean = titleBean
        return this
    }

    fun start(flags: Int? = null) {
        val bundle = Bundle()
        val context = contextRef.get()
        val intent = Intent(context, WebViewActivity::class.java)
        bundle.putSerializable(WebViewHelper.LIBRARY_WEB_VIEW, webViewConfigBean)
        intent.putExtras(bundle)
        if (flags != null) {
            intent.flags = flags
        }
        context?.startActivity(intent)
    }
}