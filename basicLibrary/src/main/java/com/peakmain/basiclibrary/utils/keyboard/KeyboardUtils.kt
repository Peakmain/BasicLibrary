package com.peakmain.basiclibrary.utils.keyboard

import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
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
            WindowUtils.getStatusHeight(activity)
        mActionBarHeight =
            WindowUtils.getActionBarHeight(
                activity
            )
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
        var keyboardHeight = 0
        var bottom = 0
        val naviationBarHeight =
            WindowUtils.getNaviationBarHeight(
                activity
            )
        val rect = Rect()
        var isPopup = false
        mDecorView.getWindowVisibleDisplayFrame(rect)
        keyboardHeight = mContentView.height - rect.bottom
        if (keyboardHeight != mPrevKeyboardHeight) {
            mPrevKeyboardHeight = keyboardHeight
            if (!WindowUtils.checkFitsSystemWindow(
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
                    bottom =
                        WindowUtils.getPaddingBottom()
                    keyboardHeight -= naviationBarHeight
                    if (keyboardHeight > naviationBarHeight) {
                        bottom = keyboardHeight + naviationBarHeight
                        isPopup = true
                    }
                    mContentView.setPadding(
                        WindowUtils.getPaddingLeft(),
                        WindowUtils.getPaddingTop(),
                        WindowUtils.getPaddingRight(),
                        WindowUtils.getPaddingBottom()
                    )
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