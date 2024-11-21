package com.peakmain.basiclibrary.manager

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.peakmain.basiclibrary.constants.PermissionMapConstants
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
    private val listeners = mutableMapOf<String, MutableList<IPermissionPopupListener>>()
    private var permission: Array<String>? = null

    // 权限与标签的映射表
    fun registerListener(
        @PermissionMapConstants.PermissionTag tag: String,
        listener: IPermissionPopupListener
    ) {
        val tagListeners = listeners[tag] ?: mutableListOf()
        if (!tagListeners.contains(listener)) {
            tagListeners.add(listener)
            listeners[tag] = tagListeners
        }
    }

    // 注销监听器
    fun unregisterListener(
        @PermissionMapConstants.PermissionTag tag: String,
        listener: IPermissionPopupListener
    ) {
        listeners[tag]?.remove(listener)
        if (listeners[tag]?.isEmpty() == true) {
            listeners.remove(tag)
        }
    }

    // 通知显示监听器（遍历所有监听器）
    private fun notifyShowListeners(permission: Array<String>) {
        // 检查 permission 数组是否为空
        if (permission.isEmpty() || listeners.isEmpty()) return


        // 从映射表获取 tag
        val tag = PermissionMapConstants.dangerousPermissionTagMap[permission[0]] ?: return
        this.permission = permission
        // 安全获取 listeners[tag] 列表
        val listenerList = listeners[tag]
        if (listenerList.isNullOrEmpty()) return

        // 通知最后一个注册的监听器
        listenerList.last().onShowPermissionPopup()
    }

    private fun notifyHideListeners() {
        if (permission?.isEmpty() == true || listeners.isEmpty()) return
        this.permission?.let {
            if (it.isEmpty()) return
            val tag = PermissionMapConstants.dangerousPermissionTagMap[it[0]] ?: return
            val listenerList = listeners[tag]
            if (listenerList.isNullOrEmpty()) return

            // 通知最后一个注册的监听器
            listenerList.last().onHidePermissionPopup()
        }
    }

    /**
     * 发送延时消息
     */
    fun sendMessage(permission: Array<String>) {
        mHandler.sendMessageDelayed(createMessage {
            showPermissionPopup(permission)
        }, 100)
    }

    /**
     * 创建一个消息，封装 Runnable 动作
     */
    private fun createMessage(action: () -> Unit): Message {
        return Message.obtain(this.mHandler) {
            action()
        }
    }

    /**
     * 展示权限弹窗说明
     */
    private fun showPermissionPopup(permission: Array<String>) {
        notifyShowListeners(permission)
    }

    /**
     * 移除所有消息和回调，防止任务继续执行
     */
    fun removeAllMessages() {
        mHandler.removeCallbacksAndMessages(null)
        notifyHideListeners()
    }
}
