package com.peakmain.basiclibary.utils

import android.util.Log
import com.peakmain.basiclibary.fragment.HomeFragment
import com.peakmain.basiclibrary.constants.PermissionConstants
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback
import com.peakmain.basiclibrary.permission.PkPermission
import com.peakmain.ui.utils.PreferencesUtil
import com.peakmain.ui.utils.ToastUtils

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
object PermissionUtils {
    fun requestPermission(
        fragment: HomeFragment,
        @PermissionConstants.PermissionGroup permissions: String
    ) {
        if (PkPermission.isGranted(PermissionConstants.getPermissions(permissions))) {
            Log.e("TAG", "授予了权限:${permissions}")
        } else {
            val permission = PermissionConstants.getPermissions(permissions)
            PkPermission.request(fragment, permission, object : OnPermissionCallback {
                override fun onGranted(permissions: Array<String>) {
                    for (s in permissions) {
                        Log.e("TAG", "授予了权限:$s")
                    }

                }

                override fun onDenied(permissions: Array<String>, never: Boolean) {
                    if (never) {
                        for (s in permissions) {
                            Log.e("TAG", "拒绝了权限:$s")
                        }
                        fragment.context?.let {
                            PkPermission.toAppSetting(it)
                        }
                    } else {
                        for (s in permissions) {
                            Log.e("TAG", "临时授予了权限:$s")
                        }
                    }

                }

            })
        }
    }

    fun requestSinglePermission(fragment: HomeFragment, permission: String) {
        if (PkPermission.isGranted(permission)) {
            Log.e("TAG", "授予了权限:${permission}")
        } else {
            if (PkPermission.shouldShowRequestPermissionRationale(fragment, arrayOf(permission))) {
                //拒绝了权限，但是没有选择"Never ask again"
                requestSinglePkPermission(fragment, permission)
            } else {
                //两种情况：1、从来没有申请过此权限 2、没有申请过权限并且选择"Never ask again"选项
                /* if (isFirstTimeAsking(arrayOf(permission))) {
                     ToastUtils.showLong("第一次申请权限")
                     firstTimeAsking(arrayOf(permission), false)
                     requestSinglePkPermission(fragment, permission)
                 }else{
                     ToastUtils.showLong("权限之前被拒绝，并且用户选择不再提示")
                 }*/
                requestSinglePkPermission(fragment, permission)
            }
        }
    }

    private fun requestSinglePkPermission(
        fragment: HomeFragment,
        permission: String
    ) {
        PkPermission.request(fragment, permission, object : OnPermissionCallback {
            override fun onGranted(permissions: Array<String>) {
                Log.e("TAG", "授予了权限:$permission")
            }

            override fun onDenied(permissions: Array<String>, never: Boolean) {
                Log.e("TAG", "是否永久:$never 拒绝了${permission}权限")
                /* fragment.context?.let {
                             PkPermission.toAppSetting(it)
                         }*/
            }

        })
    }

    fun firstTimeAsking(permissions: Array<String>, isFirstOnce: Boolean) {
        val sb = java.lang.StringBuilder()
        permissions.forEach {
            sb.append(it)
        }
        PreferencesUtil.instance.saveParam(sb.toString(), isFirstOnce)
    }


    fun requestPermission(fragment: HomeFragment, permissions: Array<String>) {
        if (PkPermission.isGranted(permissions = permissions)) {
            Log.e("TAG", "授予了权限:${permissions}")
        } else {
            PkPermission.request(fragment, permissions, object : OnPermissionCallback {
                override fun onGranted(permissions: Array<String>) {
                    for (s in permissions) {
                        Log.e("TAG", "授予了权限:$s")
                    }

                }

                override fun onDenied(permissions: Array<String>, never: Boolean) {
                    if (never) {
                        for (s in permissions) {
                            Log.e("TAG", "拒绝了权限:$s")
                        }
                        fragment.context?.let {
                            PkPermission.toAppSetting(it)
                        }
                    } else {
                        for (s in permissions) {
                            Log.e("TAG", "临时授予了权限:$s")
                        }
                    }

                }

            })
        }
    }
}