package com.peakmain.basiclibrary.view

import android.opengl.Visibility
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.peakmain.basiclibrary.extend.click
import com.peakmain.basiclibrary.extend.clickClipListener
import com.peakmain.basiclibrary.extend.clickViewDelay
import com.peakmain.basiclibrary.extend.ktxRunOnUiThread
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

    /**
     * 设置view的visibility
     */
    @BindingAdapter(value = ["app:visibility"])
    @JvmStatic
    fun setViewVisible(view: View, visibility: Int = View.VISIBLE) {
        view.visibility = visibility
    }

    /**
     * 防止多次重复点击
     */
    @BindingAdapter(value = [ "app:clickDelayTime","app:click"],requireAll = false)
    @JvmStatic
    fun setViewClick(view: View, delayTime: Long = 0, block: (View) -> Unit) {
        if (delayTime == 0L) {
            view.click {
                block(it)
            }
        } else {
            view.clickViewDelay(delayTime) {
                block(it)
            }
        }
    }

    /**
     * textView的drawableLeft和drawableRight的点击事件
     */
   @BindingAdapter(value = ["app:drawableLeftClick","app:drawableRightClick"],requireAll = false)
   @JvmStatic
   fun setTextViewDrawableClick(view:TextView,  drawableLeftClick: (View) -> Unit = {},drawableRightClick: (View) -> Unit = {}){
       view.clickClipListener(drawableLeftClick,drawableRightClick)
   }

}