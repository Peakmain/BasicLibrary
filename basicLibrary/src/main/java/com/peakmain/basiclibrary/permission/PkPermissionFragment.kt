package com.peakmain.basiclibrary.permission

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.helper.PermissionHelper
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback
import com.peakmain.basiclibrary.viewmodel.PkPermissionViewModel


/**
 * author ：Peakmain
 * createTime：2022/08/11
 * mail:2726449200@qq.com
 * describe：
 */
internal class PkPermissionFragment : Fragment() {

    private val mViewModel = PkPermissionViewModel()
    private var mSinglePermissionLauncher =
        registerForActivityResult(RequestPermissionContract()) {
            mViewModel.registerSingleForActivityResult(
                it,
                shouldShowRequestPermissionRationale(it.first)
            )
        }
    private val mMultiPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            mViewModel.registerMultiForActivityResult(it, { permission ->
                shouldShowRequestPermissionRationale(permission)
            }) {
                requestPermission(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                    mViewModel.mOnPermissionCallback
                )
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
        mViewModel.mOnPermissionCallbackLiveData.observe(this,
            mViewModel.requestPermissionObserver(
                mSinglePermissionLauncher,
                mMultiPermissionLauncher
            ) {
                mViewModel.sBackgroundLocationPermission = true
            }
        )
    }

    fun requestPermission(permission: String, block: OnPermissionCallback?) {
        mViewModel.mOnPermissionCallback = block
        mViewModel.mOnPermissionCallbackLiveData.value = arrayOf(permission) to block
    }

    /**
     * 申请权限
     */
    fun requestPermissions(permissions: Array<String>, block: OnPermissionCallback) {
        mViewModel.mOnPermissionCallback = block
        mViewModel.mOnPermissionCallbackLiveData.value = permissions to block
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.clearData()
    }

}