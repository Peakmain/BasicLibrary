package com.peakmain.basiclibrary.permission

import android.content.Context
import android.content.Intent
import com.peakmain.basiclibrary.permission.setting.DefaultPermissionSetting

/**
 * author ：Peakmain
 * createTime：2022/08/12
 * mail:2726449200@qq.com
 * describe：
 */
internal object PermissionSettingFactory {
    /**
     * Build.MANUFACTURER
     */
    private const val MANUFACTURER_HUAWEI = "HUAWEI" //华为

    private const val MANUFACTURER_MEIZU = "Meizu" //魅族

    private const val MANUFACTURER_XIAOMI = "Xiaomi" //小米

    private const val MANUFACTURER_SONY = "Sony" //索尼

    private const val MANUFACTURER_OPPO = "OPPO" //oppo

    private const val MANUFACTURER_LG = "LG"

    private const val MANUFACTURER_VIVO = "vivo" //vivo

    fun toAppSetting(context: Context) {
        context.startActivity(getAppSettingIntent(context))
    }

    fun getAppSettingIntent(context: Context): Intent {
       /* return when (Build.MANUFACTURER) {
            MANUFACTURER_HUAWEI -> HuaWeiPermissionSetting().getAppSetting(context)
            MANUFACTURER_MEIZU -> MeiZuPermissionSetting().getAppSetting(context)
            MANUFACTURER_XIAOMI -> XiaomiPermissionSetting().getAppSetting(context)
            MANUFACTURER_SONY -> SonyPermissionSetting().getAppSetting(context)
            MANUFACTURER_OPPO -> OPPOPermissionSetting().getAppSetting(context)
            MANUFACTURER_VIVO -> VIVOPermissionSetting().getAppSetting(context)
            MANUFACTURER_LG -> LGPermissionSetting().getAppSetting(context)
            else -> DefaultPermissionSetting().getAppSetting(context)
        }*/
        return DefaultPermissionSetting().getAppSetting(context)
    }
}