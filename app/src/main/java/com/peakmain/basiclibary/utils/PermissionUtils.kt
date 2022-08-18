package com.peakmain.basiclibary.utils

import android.util.Log
import com.peakmain.basiclibary.MainActivity
import com.peakmain.basiclibrary.constants.PermissionConstants
import com.peakmain.basiclibrary.interfaces.OnPermissionCallback
import com.peakmain.basiclibrary.permission.PkPermission

/**
 * author ：Peakmain
 * createTime：2022/08/18
 * mail:2726449200@qq.com
 * describe：
 */
object PermissionUtils {
    fun requestPermission(activity:MainActivity,@PermissionConstants.PermissionGroup permissions: String) {
        if (PkPermission.isGranted(PermissionConstants.getPermissions(permissions))) {
            Log.e("TAG", "授予了权限:${permissions}")
        } else {
            val permission = PermissionConstants.getPermissions(permissions)
            PkPermission.request(activity, permission, object : OnPermissionCallback {
                override fun onGranted(permissions: Array<String>) {
                    for (s in permissions) {
                        Log.e("TAG", "授予了权限:$s")
                    }

                }

                override fun onDenied(permissions: Array<String>, never: Boolean) {
                    if(never){
                        for (s in permissions) {
                            Log.e("TAG", "拒绝了权限:$s")
                        }
                        PkPermission.toAppSetting(activity)
                    }else{
                        for (s in permissions) {
                            Log.e("TAG", "临时授予了权限:$s")
                        }
                    }

                }

            })
        }
    }
     fun requestSinglePermission(activity: MainActivity,permission: String) {
        if (PkPermission.isGranted(permission)) {
            Log.e("TAG", "授予了权限:${permission}")
        } else {
            PkPermission.request(activity, permission, object : OnPermissionCallback {
                override fun onGranted(permissions: Array<String>) {
                    Log.e("TAG", "授予了权限:$permission")
                }

                override fun onDenied(permissions: Array<String>, never: Boolean) {
                    Log.e("TAG", "是否永久:$never 拒绝了${permission}权限")
                    PkPermission.toAppSetting(activity)
                }

            })
        }
    }
}