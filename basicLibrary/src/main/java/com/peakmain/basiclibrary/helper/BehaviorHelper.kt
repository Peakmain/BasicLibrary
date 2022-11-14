package com.peakmain.basiclibrary.helper

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.peakmain.basiclibrary.R
import com.peakmain.basiclibrary.interfaces.IBehaviorHelperCallback

/**
 * author ：Peakmain
 * createTime：2022/11/14
 * mail:2726449200@qq.com
 * describe：
 */
class BehaviorHelper
/**
 *  @param target 设置阴影的根布局
 * @param backgroundColor 阴影背景颜色
 * @param peekHeight 折叠的时候 底部显示高度
 * @param isHide false 表示用户将能通过向下滑动完全隐藏的BottomSheet ,默认无此状态
 * @param isCancelable 点击空白是否可以取消，false表示可以取消，true表示不可取消
 */
constructor(
    private val target: ViewGroup? = null,
    content: ViewGroup? = null,
    listener: IBehaviorHelperCallback? = null,
    @ColorInt val backgroundColor: Int = Color.parseColor("#66000000"),
    peekHeight: Int = 0,
    isHide: Boolean = false,
    private val isCancelable: Boolean = false
) {
    private var behavior: BottomSheetBehavior<View>? = null

    init {
        content?.let {
            behavior = BottomSheetBehavior.from(it)
            behavior?.isHideable = isHide
            behavior?.peekHeight = peekHeight
            behavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        removeShadow()
                        listener?.close(bottomSheet, newState)
                    } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        listener?.open(bottomSheet, newState)
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }

            })
        }
    }

    fun toggle() {
        if (behavior?.state == BottomSheetBehavior.STATE_COLLAPSED) {
            behavior?.state = BottomSheetBehavior.STATE_EXPANDED
            showShadow()
        } else {
            behavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun showShadow() {
        if (target == null) return
        if (target.findViewById<FrameLayout>(R.id.basic_library_shadow) != null) return
        val shadow = FrameLayout(target.context)
        shadow.apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(backgroundColor)
            if (!isCancelable) {
                setOnClickListener {
                    target.removeView(this)
                    behavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
            id = R.id.basic_library_shadow
        }
        target.addView(shadow, 0)
    }

    fun removeShadow() {
        val shadowFrameLayout = target?.findViewById<ViewGroup>(R.id.basic_library_shadow) ?: return
        target.removeView(shadowFrameLayout)
    }

    fun getBehavior(): BottomSheetBehavior<View>? {
        return behavior
    }
}