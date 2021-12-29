package com.peakmain.basiclibrary.view

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.peakmain.ui.imageLoader.ImageLoader
import com.peakmain.ui.utils.TextUtils

/**
 * author ：Peakmain
 * createTime：2021/12/29
 * mail:2726449200@qq.com
 * describe：
 */
object BindingView {
    @BindingAdapter("app:loadUrl")
    @JvmStatic
    fun loadImage(img: ImageView, url: String) {
        ImageLoader.instance?.displayImage(img.context, url, img)
    }

    /**
     * 文本添加*前缀
     */
    @BindingAdapter(value = ["app:asteriskPrevText", "app:asteriskColor"], requireAll = false)
    @JvmStatic
    fun setAsteriskPrefixText(textView: TextView, text: String?, color: Int) {
        var textContent: String? = null
        if (android.text.TextUtils.isEmpty(text)) {
            textContent = textView.text.toString()
        }
        val spannableString = TextUtils.clipTextColor("*$textContent", color, 0, 1)
        textView.text = spannableString
    }


}