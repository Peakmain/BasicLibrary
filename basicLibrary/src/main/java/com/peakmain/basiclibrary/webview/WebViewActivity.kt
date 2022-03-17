package com.peakmain.basiclibrary.webview

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.peakmain.basiclibrary.R
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.databinding.LayoutActivityWebViewBinding
import com.peakmain.basiclibrary.helper.WebViewHelper
import com.peakmain.basiclibrary.utils.StatusBarUtils
import com.peakmain.basiclibrary.viewModel.WebViewModel
import com.peakmain.basiclibrary.webview.bean.WebViewTitleBean
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
    private val mStatusColor by lazy {
        intent.extras?.getInt(WebViewHelper.LIBRARY_WEB_VIEW_STATUS_COLOR)
    }
    var mWebViewFragment: WebViewFragment? = null
    private val webViewTitleBean by lazy {
        intent.extras?.getSerializable(WebViewHelper.LIBRARY_WEB_VIEW_TITLE_BEAN) as WebViewTitleBean?
    }
    private var mDefaultNavigationBar: DefaultNavigationBar? = null

    companion object {
        /**
         * @param context context
         * @param url 访问网络的url
         * @param statusColor 状态栏的颜色
         * @param bean 显示标题的实体类，如果为null则不显示标题
         */
        fun start(
            context: Context,
            url: String,
            bean: WebViewTitleBean? = null,
            @ColorInt statusColor: Int = 0
        ) {
            val intent = Intent(context, WebViewActivity::class.java)
            val bundle = Bundle()
            bundle.putString(WebViewHelper.LIBRARY_WEB_VIEW_URL, url)
            bundle.putInt(WebViewHelper.LIBRARY_WEB_VIEW_STATUS_COLOR, statusColor)
            bundle.putSerializable(WebViewHelper.LIBRARY_WEB_VIEW_TITLE_BEAN, bean)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        initTitle()
        StatusBarUtils.setLightMode(this)
        StatusBarUtils.setColor(
            this,
            if (mStatusColor == null || mStatusColor == 0) ContextCompat.getColor(
                this,
                webViewTitleBean?.toolbarBackgroundColor ?: android.R.color.white
            )
            else ContextCompat.getColor(this, mStatusColor!!),
            1
        )

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
        webViewTitleBean?.apply {
            val builder = DefaultNavigationBar.Builder(
                this@WebViewActivity,
                findViewById<View>(android.R.id.content) as ViewGroup
            )
            if (!isShowRightArrow) {
                builder.hideRightView()
            } else {
                builder.setRightResId(rightImageIcon)
            }
            if (isHideLeftText) {
                builder.hideLeftText()
            } else if (!TextUtils.isEmpty(leftText)) {
                builder.setLeftText(leftText)
            }
            if (isHideTitleText) {
                builder.hideTitleText()
            } else {
                builder.setTitleText(
                    titleText,
                    if (isTitleTextBold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
                ).setTitleTextColor(titleColor)
            }
            builder.setToolbarBackgroundColor(toolbarBackgroundColor)
            builder.setHomeAsUpIndicator(customLeftBackIcon)
                .setLeftClickListener(View.OnClickListener {
                    leftViewClickListener
                }).setRightViewClickListener(View.OnClickListener {
                    rightViewClickListener
                })
            builder.setDisplayHomeAsUpEnabled(isShowHomeAsUp)
                .setNavigationOnClickListener(View.OnClickListener { finish() })
            builder.setDisplayShowTitleEnabled(isShowToolbarTitle)
            mDefaultNavigationBar = builder.create()
            mDefaultNavigationBar?.findViewById<TextView>(R.id.tv_title)
                ?.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val appBarLayout =
                    mDefaultNavigationBar?.findViewById<AppBarLayout>(R.id.navigation_header_container)
                appBarLayout?.apply {
                    stateListAnimator = null
                    elevation = 2f
                }
            }
        }

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