package com.peakmain.basiclibrary.utils.toast

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.peakmain.basiclibrary.R
import com.peakmain.basiclibrary.ainimator.SimpleAnimationListener
import com.peakmain.ui.utils.SizeUtils
import com.peakmain.ui.widget.ShapeLinearLayout

/**
 * author ：Peakmain
 * createTime：2024/11/15
 * mail:2726449200@qq.com
 * describe：
 */
internal class TopToastLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var slideOutAnimation: Animation? = null
    private var layoutCookie: ShapeLinearLayout? = null
    private var tvTitle: TextView? = null
    private var tvMessage: TextView? = null
    private var animationInTop = 0
    private var animationOutTop = 0


    private fun initViews() {
        inflate(context, R.layout.library_dialog_top_toast, this)

        layoutCookie = findViewById(R.id.cookie)
        tvTitle = findViewById(R.id.tv_title)
        tvMessage = findViewById(R.id.tv_message)

        val layoutParams = layoutCookie?.layoutParams as MarginLayoutParams?
        layoutParams?.setMargins(
            layoutParams.leftMargin,
            layoutParams.topMargin + SizeUtils.getStatusBarHeight(),
            layoutParams.rightMargin,
            layoutParams.bottomMargin
        )
    }


    fun setParams(params: TopToastController.Params) {
        initViews()
        animationInTop = params.animationInTop
        animationOutTop = params.animationOutTop


        //标题
        if (!TextUtils.isEmpty(params.title)) {
            tvTitle?.let {
                visibility = VISIBLE
                it.text = params.title
                it.textSize=params.titleSize
            }
            if (params.titleColor != 0) {
                tvTitle?.setTextColor(ContextCompat.getColor(context, params.titleColor))
            }
        }

        //消息体
        if (!TextUtils.isEmpty(params.message)) {
            tvMessage?.let {
                visibility = VISIBLE
                it.text = params.message
                it.textSize=params.messageSize
            }
            if (params.messageColor != 0) {
                tvMessage?.setTextColor(ContextCompat.getColor(context, params.messageColor))
            }
        }
        //背景颜色
        if (params.backgroundColor != 0) {
            layoutCookie?.setNormalBackgroundColor(params.backgroundColor)
        }
        createInAnim()
        createOutAnim()
    }



    private fun createInAnim() {
        val animationResId =
            animationInTop
        val slideInAnimation = AnimationUtils.loadAnimation(context, animationResId)

        animation = slideInAnimation
    }

    private fun createOutAnim() {
        val animationResId = animationOutTop
        slideOutAnimation = AnimationUtils.loadAnimation(context, animationResId)
    }

    @JvmOverloads
    fun dismiss(listener: CookieBarDismissListener? = null) {
        slideOutAnimation!!.setAnimationListener(object : SimpleAnimationListener() {
            override fun onAnimationEnd(animation: Animation?) {
                listener?.onDismiss()
                visibility = GONE
                removeFromParent()
            }
        })

        startAnimation(slideOutAnimation)
    }

    private fun removeFromParent() {
        postDelayed({
            val parent = parent
            if (parent != null) {
                this@TopToastLinearLayout.clearAnimation()
                (parent as ViewGroup).removeView(this@TopToastLinearLayout)
            }
        }, 200)
    }

    fun interface CookieBarDismissListener {
        fun onDismiss()
    }
}