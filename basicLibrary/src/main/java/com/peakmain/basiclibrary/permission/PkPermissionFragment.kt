package com.peakmain.basiclibrary.permission

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.helper.PermissionHelper
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback


/**
 * author ：Peakmain
 * createTime：2022/08/11
 * mail:2726449200@qq.com
 * describe：
 */
internal class PkPermissionFragment : Fragment() {

    private var mOnPermissionCallback: OnPermissionCallback? = null
    private var mOnPermissionCallbackLiveData =
        MutableLiveData<Pair<Array<String>?, OnPermissionCallback?>>()
    private var mSinglePermissionLauncher =
        registerForActivityResult(RequestPermissionContract()) {
            val permission = it.first
            when {
                it.second -> mOnPermissionCallback?.onGranted(arrayOf(permission))
                permission.isNotEmpty() && shouldShowRequestPermissionRationale(permission) ->
                    mOnPermissionCallback?.onDenied(
                        arrayOf(permission),
                        false
                    )
                else -> mOnPermissionCallback?.onDenied(arrayOf(permission), true)
            }
        }
    private val mPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.containsValue(false)) {
                val deniedList = mutableListOf<String>()
                for (entry in it.entries) if (!entry.value) deniedList.add(entry.key)
                val shouldPermissionList = deniedList.filter { permission ->
                    shouldShowRequestPermissionRationale(permission)
                }
                if (shouldPermissionList.isNotEmpty()) {
                    mOnPermissionCallback?.onDenied(shouldPermissionList.toTypedArray(), false)
                } else {
                    mOnPermissionCallback?.onDenied(deniedList.toTypedArray(), true)
                }
            } else {
                mOnPermissionCallback?.onGranted(it.keys.toTypedArray())
            }
        }

    /**
     * 是否授予了某个权限
     */
    fun isGranted(permission: String): Boolean {
        return PermissionHelper.instance.isGranted(permission)
    }

    fun isGranted(permissions: Array<String>): Boolean {
        return PermissionHelper.instance.isGranted(permissions)
    }


    fun isRevoked(permission: String): Boolean {
        val fragmentActivity = activity ?: return false
        return !AndroidVersion.isAndroid6() || fragmentActivity.packageManager.isPermissionRevokedByPolicy(
            permission,
            fragmentActivity.packageName
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mOnPermissionCallbackLiveData.observe(
            this,
            requestPermissionObserver()
        )
    }

    private fun requestPermissionObserver(): Observer<Pair<Array<String>?, OnPermissionCallback?>> =
        Observer {
            val callback = it.second
            val permissions = it.first
            mOnPermissionCallback = callback
            if (callback != null && permissions != null) {
                val deniedPermissions =
                    PermissionHelper.instance.getDeniedPermissions(*permissions)
                if (!AndroidVersion.isAndroid6() || deniedPermissions.isEmpty()) {
                    mOnPermissionCallback?.onGranted(permissions)
                } else if (permissions.size == 1) {
                    mSinglePermissionLauncher.launch(deniedPermissions[0])
                } else {
                    mPermissionLauncher.launch(deniedPermissions.toTypedArray())

                }
            }
        }

    fun requestPermission(permission: String, block: OnPermissionCallback) {
        this.mOnPermissionCallback = block
        mOnPermissionCallbackLiveData.value = arrayOf(permission) to block
    }

    /**
     * 申请权限
     */
    fun requestPermissions(permissions: Array<String>, block: OnPermissionCallback) {
        this.mOnPermissionCallback = block
        mOnPermissionCallbackLiveData.value = permissions to block
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mOnPermissionCallback != null) {
            mOnPermissionCallback = null
        }
        if (mOnPermissionCallbackLiveData.value != null) {
            mOnPermissionCallbackLiveData.value = null
        }
    }

}