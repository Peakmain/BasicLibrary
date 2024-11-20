package com.peakmain.basiclibrary.manager

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.peakmain.basiclibrary.interfaces.IPermissionPopupListener

/**
 * author ：Peakmain
 * createTime：2024/11/19
 * mail:2726449200@qq.com
 * describe：
 */
class PermissionHandlerManager private constructor() {
    companion object {
        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            PermissionHandlerManager()
        }
    }

    // 使用弱引用的静态内部 Handler 类，防止内存泄漏
    private val mHandler = object : Handler(Looper.getMainLooper()) {}
    private val listeners = mutableListOf<IPermissionPopupListener>()
    fun registerListener(listener: IPermissionPopupListener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: IPermissionPopupListener) {
        listeners.remove(listener)
    }

    /**
     * 通知所有监听器
     */
    private fun notifyShowListeners() {
        if (listeners.isEmpty()) return
        listeners[0].onShowPermissionPopup()
    }

    private fun notifyHideListeners() {
        if (listeners.isEmpty()) return
        listeners[0].onHidePermissionPopup()
    }

    /**
     * 发送延时消息
     */
    fun sendMessage() {
        mHandler.sendMessageDelayed(createMessage(::showPermissionPopup), 100)
    }

    /**
     * 创建一个消息，封装 Runnable 动作
     */
    private fun createMessage(action: () -> Unit): Message {
        return Message.obtain(mHandler) {
            action()
        }
    }

    /**
     * 展示权限弹窗说明
     */
    private fun showPermissionPopup() {
        notifyShowListeners()
    }

    /**
     * 移除所有消息和回调，防止任务继续执行
     */
    fun removeAllMessages() {
        mHandler.removeCallbacksAndMessages(null)
        notifyHideListeners()
    }
}
