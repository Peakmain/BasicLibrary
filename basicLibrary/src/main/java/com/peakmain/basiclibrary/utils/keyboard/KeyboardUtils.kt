package com.peakmain.basiclibrary.utils.keyboard

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.os.IBinder
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import com.peakmain.basiclibrary.R
import com.peakmain.basiclibrary.utils.WindowUtils

/**
 * author ：Peakmain
 * createTime：1/22/22
 * mail:2726449200@qq.com
 * describe：键盘工具类
 */
/**
 * @param activity activity
 * @param isSupportActionBar 是否支持supportActionBar
 * @param isFits 是否解决标题栏与状态栏重叠问题
 */
class KeyboardUtils(
    val activity: Activity,
    val isSupportActionBar: Boolean = false,
    val isFits: Boolean = true
) :
    ViewTreeObserver.OnGlobalLayoutListener {
    private var mDecorView: View = activity.window.decorView
    private var mContentView: View
    private var mChildView: View? = null
    private var mPaddingLeft = 0
    private var mPaddingTop: Int = 0
    private var mPaddingRight: Int = 0
    private var mPaddingBottom: Int = 0
    private var mStatusBarHeight = 0
    private var mActionBarHeight = 0
    private var mPrevKeyboardHeight = 0
    private var mKeyBoardHeight: Int = 0

    private var isStart = false

    init {
        val frameLayout = mDecorView.findViewById<FrameLayout>(R.id.content)
        mChildView = frameLayout[0]
        if (mChildView != null) {
            if (mChildView is DrawerLayout) {
                mChildView = (mChildView as DrawerLayout)[0]
            }
            if (mChildView != null) {
                mPaddingLeft = mChildView!!.paddingLeft
                mPaddingTop = mChildView!!.paddingTop
                mPaddingRight = mChildView!!.paddingRight
                mPaddingBottom = mChildView!!.paddingBottom
            }
        }
        mContentView = if (mChildView != null) mChildView!! else frameLayout
        mStatusBarHeight =
            WindowUtils.getInstance()?.getStatusHeight(activity)?:0
        mActionBarHeight =
            WindowUtils.getInstance()?.getActionBarHeight(
                activity
            )?:0
    }

    companion object {
        /**
         * 显示系统键盘
         */
        fun showSoftInput(context: Context) {
            val im = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            im?.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        /**
         * 多种隐藏软件盘方法的其中一种
         *
         * @param context 上下文
         * @param token   当前view的token
         */
        fun hideSoftInput(context: Context, token: IBinder?) {
            if (token != null) {
                val im =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                im?.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }

        fun isOpenInput(context: Context): Boolean {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return imm.isActive
        }

    }

    fun start(mode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.setSoftInputMode(mode)
            if (!isStart) {
                mDecorView.viewTreeObserver.addOnGlobalLayoutListener(this)
                isStart = true
            }
        }
    }

    fun cancel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isStart) {
            mDecorView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            isStart = false
        }
    }

    override fun onGlobalLayout() {
        var keyboardHeight: Int
        var bottom = 0
        val naviationBarHeight =
            WindowUtils.getInstance()?.getNavigationBarHeight(
                activity
            ) ?: 0
        val rect = Rect()
        var isPopup = false
        mDecorView.getWindowVisibleDisplayFrame(rect)
        keyboardHeight = mContentView.height - rect.bottom
        if (keyboardHeight != mPrevKeyboardHeight) {
            mPrevKeyboardHeight = keyboardHeight
            if (WindowUtils.getInstance() != null && !WindowUtils.getInstance()!!
                    .checkFitsSystemWindow(
                        mDecorView.findViewById(R.id.content)
                    )
            ) {
                if (mChildView != null) {
                    if (isSupportActionBar) {
                        keyboardHeight += mActionBarHeight + mStatusBarHeight
                    }
                    if (isFits) {
                        keyboardHeight += mStatusBarHeight
                    }
                    if (keyboardHeight > naviationBarHeight) {
                        bottom = keyboardHeight + mPaddingBottom
                        isPopup = true
                    }
                    mContentView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, bottom)
                } else {
                    WindowUtils.getInstance()?.apply {
                        bottom = getPaddingBottom()
                        keyboardHeight -= naviationBarHeight
                        if (keyboardHeight > naviationBarHeight) {
                            bottom = keyboardHeight + naviationBarHeight
                            isPopup = true
                        }
                        mContentView.setPadding(
                            getPaddingLeft(),
                            getPaddingTop(),
                            getPaddingRight(),
                            getPaddingBottom()
                        )
                    }

                }
            } else {
                keyboardHeight -= naviationBarHeight
                if (keyboardHeight > naviationBarHeight) {
                    isPopup = true
                }
            }
            if (keyboardHeight < 0) {
                keyboardHeight = 0
            }
            if (onKeyboardListener != null) {
                onKeyboardListener!!.onKeyboardChange(isPopup, keyboardHeight)
            }
            mKeyBoardHeight = keyboardHeight
        }

    }

    private var onKeyboardListener: OnKeyboardListener? = null


    private fun setOnKeyboardListener(onKeyboardListener: OnKeyboardListener) {
        this.onKeyboardListener = onKeyboardListener
    }

    fun getKeyBoardHeight(): Int {
        return mKeyBoardHeight
    }
}