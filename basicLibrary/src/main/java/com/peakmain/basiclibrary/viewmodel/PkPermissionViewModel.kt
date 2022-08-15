package com.peakmain.basiclibrary.viewmodel

import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.helper.PermissionHelper
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback

/**
 * author ：Peakmain
 * createTime：2022/08/15
 * mail:2726449200@qq.com
 * describe：
 */
internal class PkPermissionViewModel : BaseViewModel() {
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
        multiPermissionLauncher: ActivityResultLauncher<Array<String>>
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
                    multiPermissionLauncher.launch(deniedPermissions.toTypedArray())

                }
            }
        }
}