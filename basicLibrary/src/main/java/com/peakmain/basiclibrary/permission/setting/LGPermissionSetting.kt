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
internal class LGPermissionSetting: IPermissionSetting {
    override fun getAppSetting(context: Context): Intent {
        try {
            val intent = Intent("android.intent.action.MAIN")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("packageName", context.packageName)
            val comp = ComponentName(
                "com.android.settings",
                "com.android.settings.Settings\$AccessLockSummaryActivity"
            )
            intent.component = comp
            return intent
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return DefaultPermissionSetting().getAppSetting(context)
    }
}