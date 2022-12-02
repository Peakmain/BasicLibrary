package com.peakmain.basiclibrary.helper

import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
internal class PermissionHelper private constructor() : Handler.Callback {
    private val pendingRequestManagerFragments: HashMap<FragmentManager, PkPermissionFragment> =
        HashMap()
    private val handler = Handler(Looper.getMainLooper(), this)

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            PermissionHelper()
        }
        private const val REMOVE_FRAGMENT_MANAGER_ID = 1
        private val TAG = PkPermission::class.simpleName
    }


    fun getFragment(fragmentManager: FragmentManager): PkPermissionFragment {
        var pkPermissionFragment = findFragment(fragmentManager)
        if (pkPermissionFragment == null) {
            pkPermissionFragment = pendingRequestManagerFragments[fragmentManager]
            if (pkPermissionFragment == null) {
                Log.e(TAG, "创建了Fragment")
                pkPermissionFragment = PkPermissionFragment()
                pendingRequestManagerFragments[fragmentManager] = pkPermissionFragment
                fragmentManager.beginTransaction()
                    .add(pkPermissionFragment, TAG)
                    .commitAllowingStateLoss()
                handler.obtainMessage(REMOVE_FRAGMENT_MANAGER_ID, fragmentManager).sendToTarget()
            }

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

    override fun handleMessage(msg: Message): Boolean {
        var handler = true
        var removed: Any? = null
        var key: Any? = null
        if (msg.what == REMOVE_FRAGMENT_MANAGER_ID) {
            val fragmentManager = msg.obj as FragmentManager
            key = fragmentManager
            removed = pendingRequestManagerFragments.remove(fragmentManager)
        } else {
            handler = false
        }
        if (handler && removed == null && Log.isLoggable(TAG, Log.WARN)) {
            Log.w(
                TAG,
                "Failed to remove expected request manager fragment, manager: $key"
            )
        }
        return handler
    }
}