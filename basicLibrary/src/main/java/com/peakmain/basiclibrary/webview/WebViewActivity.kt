package com.peakmain.basiclibrary.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import com.peakmain.basiclibrary.R
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.databinding.LayoutActivityWebViewBinding
import com.peakmain.basiclibrary.helper.WebViewHelper
import com.peakmain.basiclibrary.viewModel.WebViewModel
import com.peakmain.basiclibrary.webview.callback.WebViewTitleBean
import com.peakmain.basiclibrary.webview.fragment.WebViewFragment
import com.peakmain.ui.navigationbar.DefaultNavigationBar


/**
 * author ：Peakmain
 * createTime：2022/3/1
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewActivity(override val layoutId: Int = R.layout.layout_activity_web_view) :
    BaseActivity<LayoutActivityWebViewBinding, WebViewModel>() {
    private val mUrl by lazy {
        intent.extras?.getString(WebViewHelper.LIBRARY_WEB_VIEW_URL)
    }

    var mWebViewFragment: WebViewFragment? = null
    private val webViewTitleBean by lazy {
        intent.extras?.getSerializable(WebViewHelper.LIBRARY_WEB_VIEW_TITLE_BEAN) as WebViewTitleBean?
    }

    companion object {
        fun start(context: Context, url: String, bean: WebViewTitleBean? = null) {
            val intent = Intent(context, WebViewActivity::class.java)
            val bundle=Bundle()
            bundle.putString(WebViewHelper.LIBRARY_WEB_VIEW_URL, url)
            bundle.putSerializable(WebViewHelper.LIBRARY_WEB_VIEW_TITLE_BEAN, bean)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        initTitle()
        val bundle = Bundle()
        if (!TextUtils.isEmpty(mUrl)) {
            bundle.putString(WebViewHelper.LIBRARY_WEB_VIEW_URL, mUrl)
        }
        mWebViewFragment = WebViewFragment()
        mWebViewFragment!!.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.root, mWebViewFragment!!)
            .commitAllowingStateLoss()
    }

    private fun initTitle() {
        DefaultNavigationBar.Builder(
            this,
            findViewById<View>(android.R.id.content) as ViewGroup
        )
            .setDisplayHomeAsUpEnabled(true) //Set left click event
            .setLeftClickListener(View.OnClickListener { v: View? ->
                webViewTitleBean?.leftClick ?: finish()
            })
            .setTitleText(webViewTitleBean?.title) //set toolbar background color
            .create()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val url = intent.getStringExtra(WebViewHelper.LIBRARY_WEB_VIEW_URL)
        if (mWebViewFragment != null && !TextUtils.isEmpty(url)) {
            mWebViewFragment!!.loadUrl(intent.getStringExtra(WebViewHelper.LIBRARY_WEB_VIEW_URL)!!)
        }
    }

    private fun canGoBack(): Boolean {
        return mWebViewFragment != null && mWebViewFragment!!.canGoBack()
    }

    private fun webViewGoBack() {
        mWebViewFragment?.webViewPageGoBack()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canGoBack()) {
                webViewGoBack()
                true
            } else {
                finish()
                true
            }
        } else super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mWebViewFragment?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        mWebViewFragment?.clearWebView();
    }
}