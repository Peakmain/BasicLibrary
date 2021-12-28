package com.peakmain.basiclibrary.dialog

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.peakmain.basiclibrary.R

/**
 * author ：Peakmain
 * createTime：2021/12/27
 * mail:2726449200@qq.com
 * describe：
 */

class SubmitLoading {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { SubmitLoading() }
    }

    lateinit var mDialog: CustomCenterDialog
    lateinit var mTvLoading: TextView
    lateinit var mProgressBar: ProgressBar
    lateinit var mIvDone: ImageView


    fun show(context: Activity) {
        show(context, false)
    }

    fun show(context: Activity, msg: String) {
        show(context, msg, false)
    }

    fun show(context: Activity, cancelable: Boolean) {
        show(context, "loading", cancelable)
    }

    fun show(context: Activity, msg: String, cancelable: Boolean) {
        val view = context.layoutInflater.inflate(R.layout.layout_submit_loading, null)
        mTvLoading = view.findViewById(R.id.tv_loading)
        mProgressBar = view.findViewById(R.id.progressBar)
        mIvDone = view.findViewById(R.id.iv_done)
        mTvLoading.text = msg
        mDialog = CustomCenterDialog(context, view, R.style.CustomDialogThemes).also {
            it.setCancelable(cancelable)
            it.show()
        }
    }

    fun success(end: () -> Unit = {}) {
        success("successful!", end)
    }

    fun success(msg: String, end: () -> Unit = {}) {
        hideTips(msg, 400, true, end)
    }

    fun success(msg: String, time: Long, end: () -> Unit = {}) {
        hideTips(msg, time, true, end)
    }

    fun error(end: () -> Unit = {}) {
        error("error!", end)
    }

    fun error(msg: String, end: () -> Unit = {}) {
        error(msg, 400, end)
    }

    fun error(msg: String, time: Long, end: () -> Unit = {}) {
        hideTips(msg, time, false, end)
    }

    fun hideTips(msg: String, showTime: Long, status: Boolean, end: () -> Unit = {}) {
        if (this::mProgressBar.isInitialized && this::mIvDone.isInitialized) {
            changeText("")
            animationHide(mProgressBar)
            animationShow(mIvDone, status)
            Handler(Looper.getMainLooper()).postDelayed({
                changeText(msg)
            }, 250)
            Handler(Looper.getMainLooper()).postDelayed({
                end()
                hide()
            }, showTime + 200)
        } else {
            hide()
        }
    }

    fun animationHide(view: View) {
        val animation = AlphaAnimation(1f, 0f)
        animation.duration = 150
        animation.isFillEnabled = true
        animation.fillBefore = false
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                view.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        view.startAnimation(animation)
    }

    fun animationShow(view: ImageView, status: Boolean) {
        if (status) {
            view.setImageResource(R.drawable.library_done_black_24dp)
        } else {
            view.setImageResource(R.drawable.library_clear_black_24dp)
        }
        val animation = AlphaAnimation(0f, 1f)
        animation.duration = 200
        animation.isFillEnabled = true
        animation.fillBefore = false
        animation.fillAfter = true
        view.startAnimation(animation)
        view.visibility = View.VISIBLE
    }

    fun changeText(str: String) {
        if (this::mTvLoading.isInitialized) {
            mTvLoading.text = str
        }
    }

    fun hide() {
        if (this::mDialog.isInitialized) {
            mDialog.dismiss()
        }
    }

}