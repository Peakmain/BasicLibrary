package com.peakmain.basiclibrary.permission.setting

import android.content.Context
import android.content.Intent
import android.os.Build
import com.peakmain.basiclibrary.interfaces.IPermissionSetting

/**
 * author ：Peakmain
 * createTime：2022/08/12
 * mail:2726449200@qq.com
 * describe：
 */
internal class VIVOPermissionSetting : IPermissionSetting {
    override fun getAppSetting(context: Context): Intent {
        val intent: Intent
        if (Build.MODEL.contains("Y85") && !Build.MODEL.contains("Y85A") || Build.MODEL.contains("vivo Y53L")) {
            intent = Intent()
            intent.setClassName(
                "com.vivo.permissionmanager",
                "com.vivo.permissionmanager.activity.PurviewTabActivity"
            )
            intent.putExtra("packagename", context.packageName)
            intent.putExtra("tabId", "1")
            return intent
        } else {
            intent = Intent()
            intent.setClassName(
                "com.vivo.permissionmanager",
                "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"
            )
            intent.action = "secure.intent.action.softPermissionDetail"
            intent.putExtra("packagename", context.packageName)
            return intent
        }
    }
}