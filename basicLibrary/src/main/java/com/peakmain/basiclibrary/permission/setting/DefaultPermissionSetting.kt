package com.peakmain.basiclibrary.permission.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.peakmain.basiclibrary.interfaces.IPermissionSetting


/**
 * author ：Peakmain
 * createTime：2022/08/12
 * mail:2726449200@qq.com
 * describe：
 */
internal class DefaultPermissionSetting : IPermissionSetting {
    override fun getAppSetting(context: Context): Intent {
        try {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.data = Uri.fromParts("package", context.packageName, null)
            return intent
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //系统设置权限
        return Intent(Settings.ACTION_SETTINGS)
    }
}