package com.peakmain.basiclibrary.utils.mmkv

import android.content.Context
import com.peakmain.basiclibrary.base.BaseOneSingleton

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：
 */

class PreferencesUtils private constructor(val context: Context) {
    companion object : BaseOneSingleton<Context, PreferencesUtils>() {
        override fun createSingleton(params: Context): PreferencesUtils = PreferencesUtils(params)
    }


    private lateinit var mSharedPreferences: DefaultSharedPreferencesFactory
    fun getSharedPreferences(): DefaultSharedPreferencesFactory {
        return if (this::mSharedPreferences.isInitialized) {
            mSharedPreferences
        } else {
            init(context)
            mSharedPreferences
        }
    }

    private fun init(context: Context): DefaultSharedPreferencesFactory {
        mSharedPreferences =
            DefaultSharedPreferencesFactory(
                context
            )
        return mSharedPreferences
    }

    fun saveParams(key: String, objects: Any) {
        getSharedPreferences().saveParams(key, objects)
    }

    fun getParam(key: String, defaultObject: Any?): Any? {
        return getSharedPreferences().getParam(key, defaultObject)
    }

    fun clearData() {
        getSharedPreferences().clearData()
    }
}

