package com.peakmain.basiclibrary.utils.keyboard

/**
 * author ：Peakmain
 * createTime：1/22/22
 * mail:2726449200@qq.com
 * describe：
 */
interface OnKeyboardListener {
    /**
     * 键盘变化
     * @param isPopup        the is popup  是否弹出
     * @param keyboardHeight the keyboard height  软键盘高度
     */
    fun onKeyboardChange(isPopup: Boolean, keyboardHeight: Int)
}