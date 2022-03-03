package com.peakmain.basiclibrary.webview.callback

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebView

/**
 * author ：Peakmain
 * createTime：2022/3/1
 * mail:2726449200@qq.com
 * describe：
 */
interface WebViewChromeClientCallback {
    fun onReceivedTitle(title:String)
    fun openFileInput(
        fileUploadCallbackFirst: ValueCallback<Uri>?,
        fileUploadCallbackSecond: ValueCallback<Array<Uri>>?,
        acceptType: String?
    )
    fun onProgressChanged(view: WebView?, newProgress: Int)
}