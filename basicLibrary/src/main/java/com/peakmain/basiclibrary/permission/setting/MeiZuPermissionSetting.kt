package com.peakmain.basiclibrary.permission.setting

import android.content.Context
import android.content.Intent
import com.peakmain.basiclibrary.interfaces.IPermissionSetting

/**
 * author ：Peakmain
 * createTime：2022/08/12
 * mail:2726449200@qq.com
 * describe：魅族系统设置界面
 */
internal class MeiZuPermissionSetting : IPermissionSetting {
    override fun getAppSetting(context: Context): Intent {
        try {
            val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.putExtra("packageName", context.packageName)
            return intent
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return DefaultPermissionSetting().getAppSetting(context)
    }
}