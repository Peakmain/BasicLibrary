package com.peakmain.basiclibrary.manager

import android.content.Context
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import com.peakmain.basiclibrary.config.BasicLibraryConfig
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.utils.ThreadUtils
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

    private var mStateCallbacks: MutableList<OnPhoneStateCallback> = CopyOnWriteArrayList()
    private val phoneStateListener: PhoneStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, incomingNumber: String) {
            super.onCallStateChanged(state, incomingNumber)
            for (callback in mStateCallbacks) {
                callback.onPhoneStateCallback(state, incomingNumber)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private class PhoneStateCallBack(stateCallbacks: MutableList<OnPhoneStateCallback>) :
        TelephonyCallback(), TelephonyCallback.CallStateListener {
        private var mStateCallbacks: MutableList<OnPhoneStateCallback> = stateCallbacks
        override fun onCallStateChanged(state: Int) {
            for (callback in mStateCallbacks) {
                callback.onPhoneStateCallback(state, "")
            }
        }

    }

    interface OnPhoneStateCallback {
        fun onPhoneStateCallback(state: Int, incomingPhoneNumber: String)
    }

    private var telephonyManager: TelephonyManager? = null


    init {
        val application = BasicLibraryConfig.getInstance()?.getApp()?.getApplication()
        application?.let {
            telephonyManager =
                it.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
            val mainExecutor = ThreadUtils.getMainExecutor() ?: return@let
            if (AndroidVersion.isAndroid12()) {
                telephonyManager?.registerTelephonyCallback(
                    mainExecutor,
                    PhoneStateCallBack(mStateCallbacks)
                )
            } else {
                telephonyManager?.listen(
                    phoneStateListener,
                    PhoneStateListener.LISTEN_CALL_STATE
                )
            }
        }

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
        telephonyManager?.let {
            if (AndroidVersion.isAndroid12()) {
                it.unregisterTelephonyCallback(
                    PhoneStateCallBack(mStateCallbacks)
                )
            } else {
                it.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE)
            }
        }
    }


}