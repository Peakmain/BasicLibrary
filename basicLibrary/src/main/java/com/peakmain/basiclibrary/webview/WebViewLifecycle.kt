package com.peakmain.basiclibrary.webview

import android.os.Looper
import android.webkit.WebView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import android.view.ViewGroup
import android.webkit.WebViewClient


/**
 * author ：Peakmain
 * createTime：2022/3/8
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewLifecycle(var webView: WebView? = null) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        webView?.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        webView?.onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (Looper.getMainLooper() != Looper.myLooper()) return
        webView?.apply {
            stopLoading()
            handler?.removeCallbacksAndMessages(null)
            removeAllViews()
            val mViewGroup = parent as ViewGroup?
            mViewGroup?.removeView(this)
            webChromeClient = null
            webViewClient = WebViewClient()
            tag = null
            clearHistory()
            destroy()
        }
        webView = null
    }
}