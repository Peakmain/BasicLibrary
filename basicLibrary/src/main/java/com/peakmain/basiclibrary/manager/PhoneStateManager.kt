package com.peakmain.basiclibrary.manager

import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import com.peakmain.basiclibrary.config.BasicLibraryConfig
import java.util.concurrent.CopyOnWriteArrayList

/**
 * author ：Peakmain
 * createTime：1/25/22
 * mail:2726449200@qq.com
 * describe：电话状态工具类
 */
class PhoneStateManager private constructor() {
    companion object {
        val instance: PhoneStateManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            PhoneStateManager()
        }
    }

    private val phoneStateListener: PhoneStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, incomingNumber: String) {
            super.onCallStateChanged(state, incomingNumber)
            for (callback in mStateCallbacks) {
                callback.onPhoneStateCallback(state, incomingNumber)
            }
        }
    }

    interface OnPhoneStateCallback {
        fun onPhoneStateCallback(state: Int, incomingPhoneNumber: String)
    }

    private var telephonyManager: TelephonyManager? = null
    private lateinit var mStateCallbacks: MutableList<OnPhoneStateCallback>

    init {
        val application = BasicLibraryConfig.getInstance().getApp().getApplication()
        telephonyManager =
            application.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (telephonyManager != null) {
            telephonyManager!!.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        }
        mStateCallbacks = CopyOnWriteArrayList()
    }

    fun addStateCallback(phoneStateCallback: OnPhoneStateCallback) {
        if (!mStateCallbacks.contains(phoneStateCallback)) {
            mStateCallbacks.add(phoneStateCallback)
        }
    }

    fun removeStateCallback(phoneStateCallback: OnPhoneStateCallback) {
        if (mStateCallbacks.contains(phoneStateCallback)) {
            mStateCallbacks.remove(phoneStateCallback)
        }
    }


    @Throws(Throwable::class)
    protected fun finalize() {
        if (telephonyManager != null) {
            telephonyManager!!.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE)
        }
    }


}