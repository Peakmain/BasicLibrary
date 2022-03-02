package com.peakmain.basiclibrary.webview.fragment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebView
import com.peakmain.basiclibrary.R
import com.peakmain.basiclibrary.base.fragment.BaseFragment
import com.peakmain.basiclibrary.databinding.LayoutFragmentWebViewBinding
import com.peakmain.basiclibrary.dialog.ProgressLoading
import com.peakmain.basiclibrary.helper.WebViewHelper
import com.peakmain.basiclibrary.utils.BasicLibraryUtils
import com.peakmain.basiclibrary.viewModel.WebViewModel
import com.peakmain.basiclibrary.webview.callback.WebViewChromeClientCallback
import com.peakmain.basiclibrary.webview.callback.WebViewClientCallback
import com.peakmain.ui.utils.LogUtils
import java.lang.Exception

/**
 * author ：Peakmain
 * createTime：2022/3/1
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewFragment(override val layoutId: Int = R.layout.layout_fragment_web_view) :
    BaseFragment<LayoutFragmentWebViewBinding, WebViewModel>(),
        (String) -> String, WebViewClientCallback, WebViewChromeClientCallback {
    protected var mFileUploadCallbackFirst: ValueCallback<Uri>? = null
    protected var mFileUploadCallbackSecond: ValueCallback<Array<Uri>>? = null
    override fun initView(fragmentView: View) {
        mBinding.libraryWebView.apply {
            //不显示滚动条
            isHorizontalScrollBarEnabled = false
            isVerticalScrollBarEnabled = false
            mLoadUrlListener = this@WebViewFragment
            mViewModel.initWebViewSetting(this)
            mViewModel.initWebClient(this, this@WebViewFragment)
            mViewModel.initWebChromeClient(this, this@WebViewFragment)
        }
        ProgressLoading.instance(requireContext(), mBinding.libraryWebView)?.showLoading()
        loadUrl2WebView(null)
    }

    private fun loadUrl2WebView(oldUrl: String?) {
        var curUrl = oldUrl
        if (TextUtils.isEmpty(curUrl)) {
            curUrl = getWebViewUrl()
        }
        if (!TextUtils.isEmpty(curUrl)) {
            mBinding.libraryWebView.loadUrl(curUrl!!)
        } else {
            LogUtils.e("WebView is empty page not found!")
        }
    }

    private fun getWebViewUrl(): String? {
        return arguments!!.getString(WebViewHelper.LIBRARY_WEB_VIEW_URL)
    }

    override fun invoke(p1: String): String {
        return p1
    }

    fun canGoBack(): Boolean {
        return mBinding.libraryWebView.canGoBack()
    }

    fun webViewPageGoBack() {
        if (canGoBack()) {
            mBinding.libraryWebView.goBack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.libraryWebView.mLoadUrlListener = null
    }

    override fun onPageStarted(view: WebView, url: String) {
        LogUtils.e("onPageStarted:$url")
    }

    override fun onPageFinished(view: WebView, url: String) {
        ProgressLoading.instance(requireContext(), mBinding.libraryWebView)?.hideLoading()
        if (!TextUtils.isEmpty(url) && (url.startsWith("http") || url.startsWith("https"))
            && !BasicLibraryUtils.isNetworkAvailable(activity)
        ) {
            ProgressLoading.instance(requireContext(), mBinding.libraryWebView)?.showNoNetwork()
                ?.setOnRetryClickListener(View.OnClickListener {
                    loadUrl2WebView(url)
                })
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        //处理电话功能
        if (url.startsWith("tel")) {
            try {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                activity!!.startActivity(intent)
                return true
            } catch (ex: ActivityNotFoundException) {
                ex.printStackTrace()
            }
        }
        // 对支付宝和微信的支付页面点击做特殊处理
        if (url.contains("alipays://platformapi") || url.contains("weixin://wap/pay?")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
            return true
        }
        //处理外部链接
        return (url.startsWith("http://") || url.startsWith("https://")) && onWebPageUrlLoading(
            view,
            url
        )
    }

    override fun onReceivedError(view: WebView, err: Int, des: String, url: String) {
        ProgressLoading.instance(requireContext(), mBinding.libraryWebView)
            ?.showError()
    }

    override fun onReceivedTitle(title: String) {

    }

    fun loadUrl(url: String) {
        mBinding.libraryWebView.loadUrl(url)
    }

    override fun openFileInput(
        fileUploadCallbackFirst: ValueCallback<Uri>?,
        fileUploadCallbackSecond: ValueCallback<Array<Uri>>?,
        acceptType: String?
    ) {
        if (mFileUploadCallbackFirst != null) {
            mFileUploadCallbackFirst!!.onReceiveValue(null)
        }
        mFileUploadCallbackFirst = fileUploadCallbackFirst

        if (mFileUploadCallbackSecond != null) {
            mFileUploadCallbackSecond!!.onReceiveValue(null)
        }
        mFileUploadCallbackSecond = fileUploadCallbackSecond
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        var type = acceptType
        if (TextUtils.isEmpty(acceptType)) {
            type = "image/*"
        }
        i.type = type
        if (activity != null) {
            activity!!.startActivityForResult(
                Intent.createChooser(i, "File Chooser!"),
                REQUEST_CODE_FILE_PICKER
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == REQUEST_CODE_FILE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                if (intent != null) {
                    if (mFileUploadCallbackFirst != null) {
                        mFileUploadCallbackFirst!!.onReceiveValue(intent.data)
                        mFileUploadCallbackFirst = null
                    } else if (mFileUploadCallbackSecond != null) {
                        val dataUris: Array<Uri>? = try {
                            arrayOf(Uri.parse(intent.dataString))
                        } catch (e: Exception) {
                            null
                        }
                        mFileUploadCallbackSecond!!.onReceiveValue(dataUris)
                        mFileUploadCallbackSecond = null
                    }
                }
            } else {
                if (mFileUploadCallbackFirst != null) {
                    mFileUploadCallbackFirst!!.onReceiveValue(null)
                    mFileUploadCallbackFirst = null
                } else if (mFileUploadCallbackSecond != null) {
                    mFileUploadCallbackSecond!!.onReceiveValue(null)
                    mFileUploadCallbackSecond = null
                }
            }
        }
    }


    /**
     * 外链处理
     *
     * @param view view
     * @param url  url
     * @return boolean 不再需要被处理则返回true
     */
    protected fun onWebPageUrlLoading(view: WebView?, url: String?): Boolean {
        return false
    }

    fun clearWebView() {
        mBinding.libraryWebView.let {
            mBinding.libraryFlRoot.removeAllViews()
            it.visibility = View.GONE
            it.destroy()
            null
        }
    }

    companion object {
        private const val REQUEST_CODE_FILE_PICKER = 51426

    }
}