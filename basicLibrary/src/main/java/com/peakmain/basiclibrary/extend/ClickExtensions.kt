package com.peakmain.basiclibrary.extend

import android.view.MotionEvent
import android.view.View
import android.widget.TextView

/**
 * author ：Peakmain
 * createTime：2021/12/27
 * mail:2726449200@qq.com
 * describe：点击事件的扩展类
 */
private var <T : View>T.lastClickTime: Long
    get() = if (getTag(1638288000) != null) getTag(1638288000) as Long else -1
    set(value) {
        setTag(1638288000, value)
    }
private var <T : View>T.delayTime: Long
    get() = if (getTag(1638288600) != null) getTag(1638288600) as Long else -1
    set(value) {
        setTag(1638288600, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var isClickEnable = false
    val currentTimeMillis = System.currentTimeMillis()
    if (currentTimeMillis - lastClickTime >= delayTime) {
        isClickEnable = true
    }
    lastClickTime = currentTimeMillis
    return isClickEnable
}


fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {
    if (clickEnable()) {
        block(it as T)
    }
}

private var lastTime = -1L
fun <T : View> T.clickDelay(time: Long = 750, block: (T) -> Unit) = setOnClickListener {
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - lastTime
    if (diff > time) {
        lastTime = currentTime
        block(this)
    }
}

/**
 * @param time:延迟时间，默认750
 * @param block:事件处理函数
 */
fun <T : View> T.clickViewDelay(time: Long = 750, block: (T) -> Unit) {
    delayTime = time
    setOnClickListener {
        if (clickEnable()) {
            block(it as T)
        }
    }
}

/**
 * TextView点击事件拆分
 */
fun <T : TextView> T.clickClipListener(
    leftClick: (View) -> Unit = {},
    rightClick: (View) -> Unit = {}
) {
    this.run {
        setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val drawableLeft = compoundDrawables[0]
                        if (drawableLeft != null && event.rawX <= left + drawableLeft.bounds.width()) {
                            leftClick(this@run)
                            return true
                        }
                        val drawableRight = compoundDrawables[2]
                        return if (drawableRight != null && event.rawX >= width-paddingRight-drawableRight.intrinsicWidth) { // 增加了宽度，该方向+当前icon宽度
                            rightClick(this@run)
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