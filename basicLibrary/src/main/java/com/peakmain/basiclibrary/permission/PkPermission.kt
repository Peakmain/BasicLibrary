package com.peakmain.basiclibrary.permission

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.peakmain.basiclibrary.helper.PermissionHelper
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback

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
        @JvmStatic
        private val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            PkPermission()
        }

        /**
         * 是否授予某权限
         */
        @JvmStatic
        fun isGranted(permissions: String): Boolean {
            return PermissionHelper.instance.isGranted(permissions)
        }

        /**
         * 是否授予某权限
         */
        @JvmStatic
        fun isGranted(permissions: Array<String>): Boolean {
            return PermissionHelper.instance.isGranted(permissions)
        }
        @JvmStatic
        fun request(fragment: Fragment,permissions: String,block: OnPermissionCallback){
            instance.with(fragment).requestPermission(arrayOf(permissions))
                .request(block)
        }
        @JvmStatic
        fun request(fragment: Fragment, permissions: Array<String>, block: OnPermissionCallback) {
            instance.with(fragment).requestPermission(permissions)
                .request(block)
        }
        @JvmStatic
        fun request(
            activity: FragmentActivity,
            permissions: Array<String>,
            block: OnPermissionCallback
        ) {
            instance.with(activity).requestPermission(permissions)
                .request(block)
        }
        @JvmStatic
        fun toAppSetting(context: Context) {
            PermissionSettingFactory.toAppSetting(context)
        }

        @JvmStatic
        fun getAppSettingIntent(context: Context) {
            PermissionSettingFactory.getAppSettingIntent(context)
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
        mPkPermissionFragment?.requestPermissions(mPermission, block)
    }

    private fun requestPermission(permissions: Array<String>): PkPermission {
        this.mPermission = permissions
        return this
    }

}
