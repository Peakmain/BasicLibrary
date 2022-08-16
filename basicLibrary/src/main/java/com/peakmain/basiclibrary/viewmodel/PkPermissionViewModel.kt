package com.peakmain.basiclibrary.viewmodel

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.extend.launchMulti
import com.peakmain.basiclibrary.helper.PermissionHelper
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback

/**
 * author ：Peakmain
 * createTime：2022/08/15
 * mail:2726449200@qq.com
 * describe：
 */
internal class PkPermissionViewModel : BaseViewModel() {
    var sBackgroundLocationPermission: Boolean = false
    var mOnPermissionCallbackLiveData =
        MutableLiveData<Pair<Array<String>?, OnPermissionCallback?>>()
    var mOnPermissionCallback: OnPermissionCallback? = null

    override fun initModel() {

    }

    fun clearData() {
        if (mOnPermissionCallback != null) {
            mOnPermissionCallback = null
        }
        if (mOnPermissionCallbackLiveData.value != null) {
            mOnPermissionCallbackLiveData.value = null
        }
        sBackgroundLocationPermission = false
    }

    fun registerSingleForActivityResult(
        it: Pair<String, Boolean>,
        isShouldShowRequestPermissionRationale: Boolean
    ) {
        val permission = it.first
        when {
            it.second -> mOnPermissionCallback?.onGranted(arrayOf(permission))
            permission.isNotEmpty() && isShouldShowRequestPermissionRationale ->
                mOnPermissionCallback?.onDenied(
                    arrayOf(permission),
                    false
                )
            else -> mOnPermissionCallback?.onDenied(arrayOf(permission), true)
        }
    }

    fun requestPermissionObserver(
        singlePermissionLauncher: ActivityResultLauncher<String>,
        multiPermissionLauncher: ActivityResultLauncher<Array<String>>,
        block: (() -> Unit)? = null
    ): Observer<Pair<Array<String>?, OnPermissionCallback?>> =
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
                    singlePermissionLauncher.launch(deniedPermissions[0])
                } else {
                    multiPermissionLauncher.launchMulti(deniedPermissions.toTypedArray(), block)

                }
            }
        }

    fun registerMultiForActivityResult(
        it: MutableMap<String, Boolean>,
        block: ((String) -> Boolean),
        requestPermission: (() -> Unit)?
    ) {
        if (it.containsValue(false)) {
            val deniedList = mutableListOf<String>()
            for (entry in it.entries) if (!entry.value) deniedList.add(entry.key)
            val shouldPermissionList = deniedList.filter { permission ->
                block.invoke(permission)
            }
            if (shouldPermissionList.isNotEmpty()) {
                mOnPermissionCallback?.onDenied(
                    shouldPermissionList.toTypedArray(),
                    false
                )
            } else {
                mOnPermissionCallback?.onDenied(deniedList.toTypedArray(), true)
            }
        } else {
            val permissionSet = it.keys
            if (sBackgroundLocationPermission &&
                AndroidVersion.isAndroid10() &&
                (permissionSet.contains(
                    ACCESS_FINE_LOCATION
                ) || permissionSet.contains(
                    ACCESS_COARSE_LOCATION
                ))
            ) {
                requestPermission?.invoke()
            } else {
                if (sBackgroundLocationPermission) sBackgroundLocationPermission = false
                mOnPermissionCallback?.onGranted(permissionSet.toTypedArray())
            }

        }
    }
}