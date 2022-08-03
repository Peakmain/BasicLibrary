package com.peakmain.basiclibrary.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import com.peakmain.basiclibrary.R
import com.peakmain.basiclibrary.base.BaseEmptySingleton
import com.peakmain.basiclibrary.constants.AndroidVersion

/**
 * author ：Peakmain
 * createTime：1/22/22
 * mail:2726449200@qq.com
 * describe：
 */
class WindowUtils {
    private var mContentView: ViewGroup? = null

    /**
     * ActionBar的高度
     */
    private var mActionBarHeight: Int = 0
    private var mPaddingBottom = 0
    private var mPaddingLeft = 0
    private var mPaddingRight = 0
    private var mPaddingTop = 0

    companion object : BaseEmptySingleton<WindowUtils>() {
        override val createSingleton: () -> WindowUtils
            get() = ::WindowUtils
    }

    /**
     * 检查布局根节点是否使用了android:fitsSystemWindows="true"属性
     */
    fun checkFitsSystemWindow(view: View?): Boolean {
        if (view == null) return false
        if (view.fitsSystemWindows) {
            return true
        }
        if (view is ViewGroup) {
            val childCount = view.childCount
            for (i in 0 until childCount) {
                val childView = view[i]
                if (childView is DrawerLayout) {
                    if (checkFitsSystemWindow(childView)) {
                        return true
                    }
                }
                if (childView.fitsSystemWindows) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 适配刘海屏
     */
    fun fitsNotchScreen(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val layoutParams = activity.window.attributes
            layoutParams.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            activity.window.attributes = layoutParams
        }
    }

    /**
     * android 5.0以上解决状态栏和布局重叠问题
     */
    fun setWindowsAboveLollipop(activity: Activity, isSupportActionBar: Boolean = false) {
        mContentView = activity.window.decorView.findViewById(android.R.id.content)
        mActionBarHeight = getActionBarHeight(activity)
        if (checkFitsSystemWindow(mContentView)) {
            if (isSupportActionBar) {
                setPadding(0, mActionBarHeight, 0, 0)
            }
            return
        }
        var top = getStatusHeight(activity)
        if (isSupportActionBar) {
            top += mActionBarHeight
        }
        setPadding(0, top, 0, 0)
    }

    /**
     * 获取actionBar的高度
     */
    fun getActionBarHeight(activity: Activity): Int {
        var result: Int = 0
        val actionBar = activity.window.findViewById<View>(R.id.action_bar_container)
        if (actionBar != null) {
            result = actionBar.measuredHeight
        }
        if (result == 0) {
            val typedValue = TypedValue()
            activity.theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)
            result = TypedValue.complexToDimensionPixelOffset(
                typedValue.data,
                actionBar.resources.displayMetrics
            )
        }
        return result
    }

    /**
     * 获取状态栏的高度
     */
    fun getStatusHeight(context: Context): Int {
        return getInternalDimensionSize(context, "status_bar_height")
    }

    private fun getInternalDimensionSize(context: Context, key: String): Int {
        val resourceId =
            Resources.getSystem().getIdentifier(key, "dimen", "android")
        try {
            if (resourceId >= 0) {
                val sizeContext = context.resources.getDimensionPixelSize(resourceId)
                val sizeSystem = Resources.getSystem().getDimensionPixelSize(resourceId)
                return if (sizeSystem >= sizeContext) {
                    sizeSystem
                } else {
                    val densityContext = context.resources.displayMetrics.density
                    val densitySystem = Resources.getSystem().displayMetrics.density
                    val f = sizeContext * densitySystem / densityContext
                    if (f >= 0) (f + 0.5f).toInt() else (f - 0.5f).toInt()
                }
            }
        } catch (e: Exception) {
            return 0
        }
        return 0
    }

    fun getNavigationBarHeight(activity: Activity): Int {
        if (hasNavBar(activity)) {
            var key = ""
            key =
                if (activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    "navigation_bar_height"
                } else {
                    "navigation_bar_height_landscape"
                }
            return getInternalDimensionSize(activity, key)
        }
        return 0
    }

    private fun hasNavBar(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //判断小米手机是否开启了全面屏，开启了，直接返回false
            if (Settings.Global.getInt(
                    activity.contentResolver,
                    "force_fsg_nav_bar",
                    0
                ) != 0
            ) {
                return false
            }
            //判断华为手机是否隐藏了导航栏，隐藏了，直接返回false
            if (OSUtils.isEMUI) {
                if (OSUtils.isEMUI3_x || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    if (Settings.System.getInt(
                            activity.contentResolver,
                            "navigationbar_is_min",
                            0
                        ) != 0
                    ) {
                        return false
                    }
                } else {
                    if (Settings.Global.getInt(
                            activity.contentResolver,
                            "navigationbar_is_min",
                            0
                        ) != 0
                    ) {
                        return false
                    }
                }
            }
        }
        val realHeight: Int
        val realWidth: Int
        if (AndroidVersion.isAndroid11()) {
            val metrics = activity.windowManager.currentWindowMetrics
            realHeight = metrics.bounds.height()
            realWidth = metrics.bounds.width()
        } else {
            //其他手机根据屏幕真实高度与显示高度是否相同来判断
            val windowManager = activity.windowManager
            val d = windowManager.defaultDisplay
            val realDisplayMetrics = DisplayMetrics()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                d.getRealMetrics(realDisplayMetrics)
            }
            realHeight = realDisplayMetrics.heightPixels
            realWidth = realDisplayMetrics.widthPixels
        }
        val displayHeight = Resources.getSystem().displayMetrics.heightPixels
        val displayWidth = Resources.getSystem().displayMetrics.widthPixels
        return realWidth - displayWidth > 0 || realHeight - displayHeight > 0
    }

    private fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        if (mContentView != null) {
            mContentView!!.setPadding(left, top, right, bottom)
        }
        mPaddingBottom = bottom
        mPaddingTop = top
        mPaddingLeft = left
        mPaddingRight = right
    }

    fun getPaddingBottom(): Int {
        return mPaddingBottom
    }

    fun getPaddingTop(): Int {
        return mPaddingTop
    }

    fun getPaddingLeft(): Int {
        return mPaddingLeft
    }

    fun getPaddingRight(): Int {
        return mPaddingRight
    }
}