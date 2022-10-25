package com.peakmain.basiclibrary.utils.mmkv

import android.content.Context

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：
 */
class DefaultSharedPreferencesFactory(context: Context, override val key: String="basic_sp_key") :
    BaseSharedPreferencesFactory(context)