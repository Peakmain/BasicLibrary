package com.peakmain.basiclibrary.permission.setting

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.peakmain.basiclibrary.interfaces.IPermissionSetting


/**
 * author ：Peakmain
 * createTime：2022/08/12
 * mail:2726449200@qq.com
 * describe：
 */
internal class XiaomiPermissionSetting : IPermissionSetting {
    override fun getAppSetting(context: Context): Intent {
        try {
            val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
            intent.putExtra("extra_pkgname", context.packageName)
            val componentName = ComponentName(
                "com.miui.securitycenter",
                "com.miui.permcenter.permissions.PermissionsEditorActivity"
            )
            intent.component = componentName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return DefaultPermissionSetting().getAppSetting(context)
    }
}