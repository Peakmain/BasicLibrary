package com.peakmain.basiclibrary.permission

import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.helper.PermissionHelper
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback
import com.peakmain.ui.utils.PermissionUtils

/**
 * author ：Peakmain
 * createTime：2022/08/11
 * mail:2726449200@qq.com
 * describe：
 */
class PkPermission private constructor() {
    private lateinit var mPermission: Array<String>
    private var mPkPermissionFragment: PkPermissionFragment? = null
    companion object {
        val TAG = PkPermission::class.simpleName
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            PkPermission()
        }
        /**
         * 是否授予某权限
         */
        fun isGranted(permissions: String): Boolean {
            return PermissionHelper.instance.isGranted(permissions)
        }

        /**
         * 是否授予某权限
         */
        fun isGranted(permissions: Array<String>): Boolean {
            return PermissionHelper.instance.isGranted(permissions)
        }
        fun request(fragment: Fragment, permissions: Array<String>, block: OnPermissionCallback) {
            instance.with(fragment).requestPermission(permissions)
                .request(block)
        }

        fun request(
            activity: FragmentActivity,
            permissions: Array<String>,
            block: OnPermissionCallback
        ) {
            instance.with(activity).requestPermission(permissions)
                .request(block)
        }
        fun onRequestPermissionsResult(permissions: Array<String>, grantResults: IntArray) {
           instance.onRequestPermissionsResult(permissions,grantResults)
        }
    }





    private fun with(fragment: Fragment): PkPermission {
        mPkPermissionFragment = PermissionHelper.instance.getFragment(fragment.childFragmentManager)
        return this
    }

    private fun with(activity: FragmentActivity): PkPermission {
        mPkPermissionFragment =
            PermissionHelper.instance.getFragment(activity.supportFragmentManager)
        return this
    }

    private fun request(block: OnPermissionCallback) {
        mPkPermissionFragment?.requestPermissions(mPermission,block)
    }

    private fun requestPermission(permissions: Array<String>): PkPermission {
        this.mPermission = permissions
        return this
    }



    private fun onRequestPermissionsResult(permissions: Array<String>, grantResults: IntArray) {
        mPkPermissionFragment
            ?.onRequestPermissionsResult(permissions, grantResults)
    }

}
