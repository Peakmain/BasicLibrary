package com.peakmain.basiclibrary.permission

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.peakmain.basiclibrary.helper.PermissionHelper
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback
import java.lang.ref.WeakReference

/**
 * author ：Peakmain
 * createTime：2022/08/11
 * mail:2726449200@qq.com
 * describe：
 */
class PkPermission private constructor() {
    private lateinit var mPermission: Array<String>
    private var mPkPermissionFragment: WeakReference<PkPermissionFragment>? = null

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
        fun shouldShowRequestPermissionRationale(
            fragment: Fragment?,
            permissions: Array<String>?
        ): Boolean {
            return PermissionHelper.instance.shouldShowRequestPermissionRationale(
                fragment,
                permissions
            )
        }

        @JvmStatic
        fun shouldShowRequestPermissionRationale(
            activity: Activity?,
            permissions: Array<String>?
        ): Boolean {
            return PermissionHelper.instance.shouldShowRequestPermissionRationale(
                activity,
                permissions
            )
        }

        @JvmStatic
        fun request(fragment: Fragment, permission: String, block: OnPermissionCallback) {
            instance.with(fragment).requestPermission(arrayOf(permission))
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
            permission: String,
            block: OnPermissionCallback
        ) {
            instance.with(activity).requestPermission(permission)
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
        fun toNotificationSetting(context: Context?) {
            PermissionSettingFactory.toAppSetting(context, true)
        }

    }


    private fun with(fragment: Fragment): PkPermission {
        mPkPermissionFragment =
            WeakReference(PermissionHelper.instance.getFragment(fragment.childFragmentManager))
        return this
    }

    private fun with(activity: FragmentActivity): PkPermission {
        mPkPermissionFragment =
            WeakReference(PermissionHelper.instance.getFragment(activity.supportFragmentManager))
        return this
    }

    private fun request(block: OnPermissionCallback) {
        val fragment = mPkPermissionFragment?.get() ?: return
        fragment.requestPermissions(mPermission, block)
    }

    private fun requestPermission(permission: String): PkPermission {
        this.mPermission = arrayOf(permission)
        return this
    }

    private fun requestPermission(permissions: Array<String>): PkPermission {
        this.mPermission = permissions
        return this
    }

}
