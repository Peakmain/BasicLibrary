package com.peakmain.basiclibrary.view

import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.peakmain.basiclibrary.extend.clickViewDelay

/**
 * author ：Peakmain
 * createTime：2021/12/29
 * mail:2726449200@qq.com
 * describe：
 */
object BindingView {

    /**
     * 设置view的visibility
     */
    @BindingAdapter(value = ["visibilityOrGone"])
    @JvmStatic
    fun setViewVisibleOrGone(view: View, showVisibility: Boolean) {
        view.visibility = if (showVisibility) View.VISIBLE else View.GONE
    }

    @BindingAdapter(value = ["visibilityOrInVisible"])
    @JvmStatic
    fun setViewVisibleOrInvisible(view: View, showVisibility: Boolean) {
        view.visibility = if (showVisibility) View.VISIBLE else View.INVISIBLE
    }

    /**
     * 防止多次重复点击
     */
    @BindingAdapter(value = ["clickDelayTime", "click"], requireAll = false)
    @JvmStatic
    fun setViewClick(view: View, delayTime: Long = 0, block: View.OnClickListener? = null) {
        if (delayTime == 0L) {
            view.setOnClickListener(block)
        } else {
            view.clickViewDelay(delayTime) {v->
                block?.let {
                    block.onClick(v)
                }
            }
        }
    }

    /**
     * textView的drawableLeft和drawableRight的点击事件
     */
    @BindingAdapter(value = ["drawableLeftClick", "drawableRightClick"], requireAll = false)
    @JvmStatic
    fun setTextViewDrawableClick(
        view: TextView,
        drawableLeftClick: View.OnClickListener? = null,
        drawableRightClick: View.OnClickListener? = null
    ) {
        view.run {
            setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            val drawableLeft = compoundDrawables[0]
                            if (drawableLeft != null && event.rawX <= left + drawableLeft.bounds.width()) {
                                drawableLeftClick?.let {
                                    drawableLeftClick.onClick(view)
                                }
                                return true
                            }
                            val drawableRight = compoundDrawables[2]
                            return if (drawableRight != null && event.rawX >= width-paddingRight-drawableRight.intrinsicWidth) { // 增加了宽度，该方向+当前icon宽度
                                drawableRightClick?.let {
                                    drawableRightClick.onClick(view)
                                }
                                true
                            } else {
                                false
                            }
                        }
                        else -> return false
                    }
                }
            })
        }
    }
}