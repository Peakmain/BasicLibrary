package com.peakmain.basiclibrary.interfaces

import android.content.Context
import android.content.Intent

/**
 * author ：Peakmain
 * createTime：2022/08/12
 * mail:2726449200@qq.com
 * describe：
 */
internal interface IPermissionSetting {
    fun getAppSetting(context:Context): Intent
}