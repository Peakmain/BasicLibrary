package com.peakmain.basiclibrary.utils.toast

import android.app.Activity
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.peakmain.basiclibrary.R

/**
 * author ：Peakmain
 * createTime：2023/11/10
 * mail:2726449200@qq.com
 * describe：
 */
class TopToastController private constructor(private val context: Activity, params: Params?) {
    private val topToastLinearLayoutView: TopToastLinearLayout?

    init {
        if (params == null) {
            dismiss()
        }

        topToastLinearLayoutView = TopToastLinearLayout(context)
        topToastLinearLayoutView.setParams(params!!)
    }

    private fun show() {
        if (topToastLinearLayoutView != null) {
            val decorView = context.window.decorView as ViewGroup
            if (topToastLinearLayoutView.parent == null) {
                addCookie(decorView, topToastLinearLayoutView)
            }
        }
    }

    fun dismiss() {
        val decorView = context.window.decorView as ViewGroup
        removeFromParent(decorView)
    }

    private fun removeFromParent(parent: ViewGroup) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            if (child is TopToastLinearLayout) {
                child.dismiss()
                return
            }
        }
    }

    private fun addCookie(parent: ViewGroup, topToastLinearLayout: TopToastLinearLayout) {
        if (topToastLinearLayout.parent != null) {
            return
        }

        // if exists, remove existing cookie
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            if (child is TopToastLinearLayout) {
                child.dismiss { parent.addView(topToastLinearLayout) }
                return
            }
        }
        parent.addView(topToastLinearLayout)
    }

    class Builder internal constructor(private val context: Activity) {
        private val params = Params()

        /**
         * 设置标题
         */
        fun setTitle(title: String?): Builder {
            params.title = title
            return this
        }
        /**
         * 设置标题
         */
        fun setTitle(@StringRes resId: Int): Builder {
            params.title = context.getString(resId)
            return this
        }
        /**
         * 设置标题字体颜色
         */
        fun setTitleColor(@ColorRes titleColor: Int): Builder {
            params.titleColor = titleColor
            return this
        }

        /**
         * 设置标题字体大小
         */
        fun setTitleSize(size: Int): Builder {
            params.titleSize = size.toFloat()
            return this
        }

        /**
         * 设置消息内容
         */
        fun setMessage(message: String?): Builder {
            params.message = message
            return this
        }
        /**
         * 设置消息内容
         */
        fun setMessage(@StringRes resId: Int): Builder {
            params.message = context.getString(resId)
            return this
        }
        /**
         * 设置消息内容字体大小
         */
        fun setMessageSize(size: Int): Builder {
            params.messageSize = size.toFloat()
            return this
        }
        /**
         * 设置消息内容字体颜色
         */
        fun setMessageColor(@ColorRes messageColor: Int): Builder {
            params.messageColor = messageColor
            return this
        }

        /**
         * 设置背景颜色
         */
        fun setBackgroundColor(@ColorRes background: Int): Builder {
            params.backgroundColor = background
            return this
        }

        private fun create(): TopToastController {
            return TopToastController(context, params)
        }

        fun show(): TopToastController {
            val cookie = create()
            cookie.show()
            return cookie
        }
    }

    internal class Params {
        var title: String? = null
        var titleSize: Float = 14f
        var message: String? = null
        var backgroundColor: Int = 0
        var titleColor: Int = 0
        var messageColor: Int = 0
        var messageSize: Float = 11f
        var animationInTop: Int = R.anim.slide_in_from_top
        var animationOutTop: Int = R.anim.slide_out_to_top
    }


    companion object {

        @JvmStatic
        fun build(activity: Activity): Builder {
            return Builder(activity)
        }
    }
}