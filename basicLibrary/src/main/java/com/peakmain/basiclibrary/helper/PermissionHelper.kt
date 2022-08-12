package com.peakmain.basiclibrary.helper

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.peakmain.basiclibrary.config.BasicLibraryConfig
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.permission.PkPermission
import com.peakmain.basiclibrary.permission.PkPermissionFragment
import com.peakmain.basiclibrary.utils.BasicLibraryUtils

/**
 * author ：Peakmain
 * createTime：2022/08/11
 * mail:2726449200@qq.com
 * describe：
 */
internal class PermissionHelper private constructor() {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            PermissionHelper()
        }
        private val TAG = PkPermission::class.simpleName
    }


    fun getFragment(fragmentManager: FragmentManager): PkPermissionFragment {
        var pkPermissionFragment = findFragment(fragmentManager)
        if (pkPermissionFragment == null) {
            pkPermissionFragment = PkPermissionFragment()
            fragmentManager.beginTransaction()
                .add(pkPermissionFragment, TAG)
                .commitNow()
        }
        return pkPermissionFragment
    }

    private fun findFragment(fragmentManager: FragmentManager): PkPermissionFragment? {
        return fragmentManager.findFragmentByTag(TAG) as PkPermissionFragment?
    }

    /**
     * 检查是否都赋予权限
     *
     * @param grantResults grantResults
     * @return 所有都同意返回true 否则返回false
     */
    fun isGranted(grantResults: IntArray): Boolean {
        if (grantResults.isEmpty()) return false
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 是否授予了某个权限
     */
    fun isGranted(permission: String): Boolean {
        val application = BasicLibraryUtils.application
        return !AndroidVersion.isAndroid6() || (application != null &&
                PackageManager.PERMISSION_GRANTED ==
                ContextCompat.checkSelfPermission(
                    application,
                    permission
                ))
    }

    fun isGranted(permissions: Array<String>): Boolean {
        if (!AndroidVersion.isAndroid6()) {
            return true
        }
        permissions.forEach {
            if (!isGranted(it)) {
                return false
            }
        }
        return true
    }

    /**
     * 是否拒绝过权限但是没有选择不再提示
     */
    fun shouldShowRequestPermissionRationale(
        fragment: Fragment,
        permissions: Array<String>
    ): Boolean {
        for (permission in permissions) {
            if (fragment.shouldShowRequestPermissionRationale(permission)) {
                return true
            }
        }
        return false
    }

    /**
     * 获取请求权限中需要授权的权限
     */
    fun getDeniedPermissions(vararg permissions: String): List<String> {
        val deniedPermissions = ArrayList<String>()
        if (BasicLibraryUtils.application == null) return deniedPermissions
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    BasicLibraryUtils.application!!,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {
                deniedPermissions.add(permission)
            }
        }
        return deniedPermissions
    }
}