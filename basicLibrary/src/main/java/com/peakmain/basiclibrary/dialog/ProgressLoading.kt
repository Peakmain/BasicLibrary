package com.peakmain.basiclibrary.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peakmain.basiclibrary.R
import com.peakmain.basiclibrary.base.BaseTwoSingleton

/**
 * author ：Peakmain
 * createTime：2022/3/1
 * mail:2726449200@qq.com
 * describe：
 */
class ProgressLoading private constructor(context: Context, val webView: View?) {
    //重试的点击事件
    private var mOnRetryClickListener: View.OnClickListener? = null


    companion object : BaseTwoSingleton<Context, View, ProgressLoading>() {
        private var mStatusView = ArrayList<View?>()
        override fun createSingleton(params1: Context, params2: View): ProgressLoading? {
            mStatusView = ArrayList()
            return ProgressLoading(params1, params2)
        }

        private val DEFAULT_LAYOUT_PARAMS = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )


    }

    private var mEmptyView: View? = null
    private var mErrorView: View? = null
    private var mLoadingView: View? = null
    private var mNoNetworkView: View? = null

    //布局的id
    private var mEmptyViewResId = R.layout.layout_empty_view
    private var mErrorViewResId = R.layout.layout_error_view
    private var mLoadingViewResId = R.layout.layout_loading_view
    private var mNoNetworkViewResId = R.layout.layout_network_view

    private val mOtherIds = ArrayList<Int>()

    private var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    fun showEmptyView(): ProgressLoading {

        return if (mEmptyView != null) {
            showEmpty(mEmptyView!!, DEFAULT_LAYOUT_PARAMS)
        } else {
            showEmpty(mEmptyViewResId, DEFAULT_LAYOUT_PARAMS)
        }

    }

    fun showEmpty(layoutId: Int, layoutParams: ViewGroup.LayoutParams): ProgressLoading {
        return showEmpty(inflateView(layoutId), layoutParams)
    }

    private fun inflateView(layoutId: Int): View {
        return mInflater!!.inflate(layoutId, null)
    }

    private fun showEmpty(view: View, layoutParams: ViewGroup.LayoutParams): ProgressLoading {
        if (mEmptyView == null) {
            mEmptyView = view
            val emptyRetryView = mEmptyView!!.findViewById<View>(R.id.empty_retry_view)
            if (null != mOnRetryClickListener && null != emptyRetryView) {
                emptyRetryView.setOnClickListener(mOnRetryClickListener)
            }
            mOtherIds.add(mEmptyView!!.id)
            val parent = webView?.parent as ViewGroup
            parent.addView(view, 0, layoutParams)
        }
        mStatusView.add(mEmptyView)
        showViewById(mEmptyView!!.id)
        return this
    }

    private fun showViewById(viewId: Int) {
        webView?.visibility = View.INVISIBLE
        for (view in mStatusView) {
            if (view!!.id == viewId) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.INVISIBLE
            }
        }
    }

    fun showLoading(): ProgressLoading {
        return if (mLoadingView != null) {
            showLoading(mLoadingView, DEFAULT_LAYOUT_PARAMS)
        } else {
            showLoading(mLoadingViewResId, DEFAULT_LAYOUT_PARAMS)
        }
    }

    fun showLoading(layoutId: Int, layoutParams: ViewGroup.LayoutParams?): ProgressLoading {
        return showLoading(inflateView(layoutId), layoutParams)
    }

    fun showLoading(view: View?, layoutParams: ViewGroup.LayoutParams?): ProgressLoading {
        if (null == mLoadingView) {
            mLoadingView = view
            mOtherIds.add(mLoadingView!!.id)
            val parent = webView?.parent as ViewGroup?
            parent?.addView(view, 0, layoutParams)
        }
        mStatusView.add(mLoadingView)
        showViewById(mLoadingView!!.id)
        return this
    }

    fun hideLoading() {
        if (mLoadingView != null && mLoadingView!!.visibility == View.VISIBLE) showContentView()
    }

    fun showNoNetwork(view: View?, layoutParams: ViewGroup.LayoutParams?): ProgressLoading {
        if (null == mNoNetworkView) {
            mNoNetworkView = view
            val noNetworkRetryView = mNoNetworkView!!.findViewById<View>(R.id.no_network_retry_view)
            if (null != mOnRetryClickListener && null != noNetworkRetryView) {
                noNetworkRetryView.setOnClickListener(mOnRetryClickListener)
            }
            mOtherIds.add(mNoNetworkView!!.id)
            val parent = webView?.parent as ViewGroup?
            parent?.addView(view, 0, layoutParams)
        }
        mStatusView.add(mNoNetworkView)
        showViewById(mNoNetworkView!!.id)
        return this
    }

    fun showNoNetwork(): ProgressLoading {
        if (mNoNetworkView != null) {
            showNoNetwork(mNoNetworkView, DEFAULT_LAYOUT_PARAMS)
        } else {
            showNoNetwork(mNoNetworkViewResId, DEFAULT_LAYOUT_PARAMS)
        }
        return this
    }

    fun showNoNetwork(layoutId: Int, layoutParams: ViewGroup.LayoutParams?) {
        showNoNetwork(inflateView(layoutId), layoutParams)
    }

    fun showContentView() {
        val parent = webView?.parent as ViewGroup? ?: return
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            view.visibility = if (mOtherIds.contains(view.id)) View.INVISIBLE else View.VISIBLE
        }
    }

    fun showError() {
        if (mErrorView != null) {
            showError(mErrorView, DEFAULT_LAYOUT_PARAMS)
        } else {
            showError(mErrorViewResId, DEFAULT_LAYOUT_PARAMS)
        }
    }

    fun showError(layoutId: Int, layoutParams: ViewGroup.LayoutParams?) {
        showError(inflateView(layoutId), layoutParams)
    }

    fun showError(view: View?, layoutParams: ViewGroup.LayoutParams?) {
        if (null == mErrorView) {
            mErrorView = view
            val errorRetryView = mErrorView!!.findViewById<View>(R.id.error_retry_view)
            if (null != mOnRetryClickListener && null != errorRetryView) {
                errorRetryView.setOnClickListener(mOnRetryClickListener)
            }
            mOtherIds.add(mErrorView!!.id)
            val parent = webView?.parent as ViewGroup?
            parent?.addView(view, 0, layoutParams)
        }
        mStatusView.add(mErrorView)
        showViewById(mErrorView!!.id)
    }

    fun setOnRetryClickListener(onRetryClickListener: View.OnClickListener?) {
        mOnRetryClickListener = onRetryClickListener
    }

    fun addLoadingView(loadingView: View?): ProgressLoading {
        mLoadingView = loadingView
        mLoadingView!!.visibility = View.VISIBLE
        return this
    }

    /**
     * 设置自定的空view
     *
     * @param emptyView 布局view
     */
    fun setEmptyView(emptyView: View?): ProgressLoading {
        mEmptyView = emptyView
        return this
    }

    /**
     * 设置自定的空view
     *
     * @param emptyViewResId R.layout.xx
     */
    fun setEmptyView(emptyViewResId: Int): ProgressLoading {
        mEmptyViewResId = emptyViewResId
        return this
    }

    /**
     * 设置自定义view的错误view
     *
     * @param errorView 布局view
     */
    fun setErrorView(errorView: View?): ProgressLoading {
        mErrorView = errorView
        return this
    }

    /**
     * 设置自定义view的错误view
     *
     * @param errorViewResId 布局view的id
     */
    fun setErrorView(errorViewResId: Int): ProgressLoading {
        mErrorViewResId = errorViewResId
        return this
    }

    /**
     * 设置自定义view的正在加载的view
     *
     * @param loadingView 布局view
     */
    fun setLoadingView(loadingView: View?): ProgressLoading {
        mLoadingView = loadingView
        return this
    }

    /**
     * 设置自定义view的正在加载的view
     *
     * @param loadingViewResId 布局view的id
     */
    fun setLoadingView(loadingViewResId: Int): ProgressLoading {
        mLoadingViewResId = loadingViewResId
        return this
    }

    /**
     * 设置自定义view的没有网络的view
     *
     * @param noNetworkView 布局view
     */
    fun setNoNetworkView(noNetworkView: View?): ProgressLoading {
        mNoNetworkView = noNetworkView
        return this
    }

    /**
     * 设置自定义view的没有网络的view
     *
     * @param noNetworkViewResId 布局view的id
     */
    fun setNoNetworkView(noNetworkViewResId: Int): ProgressLoading {
        mNoNetworkViewResId = noNetworkViewResId
        return this
    }
}