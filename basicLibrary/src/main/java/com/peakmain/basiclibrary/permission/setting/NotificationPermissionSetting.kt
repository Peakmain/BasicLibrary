package com.peakmain.basiclibrary.permission.setting

import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.peakmain.basiclibrary.constants.AndroidVersion
import com.peakmain.basiclibrary.interfaces.IPermissionSetting
import com.peakmain.basiclibrary.permission.PermissionSettingFactory

/**
 * author ：Peakmain
 * createTime：2022/10/25
 * mail:2726449200@qq.com
 * describe：android 13消息通知设置界面
 */
class NotificationPermissionSetting : IPermissionSetting {
    override fun getAppSetting(context: Context): Intent {
        if (AndroidVersion.isAndroid13()) {
            val applicationInfo = context.applicationInfo
            try {
                val intent = Intent()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra("app_package", applicationInfo.packageName)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, applicationInfo.packageName)
                intent.putExtra("app_uid", applicationInfo.uid)
                return intent
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
        return DefaultPermissionSetting().getAppSetting(context)
    }
}