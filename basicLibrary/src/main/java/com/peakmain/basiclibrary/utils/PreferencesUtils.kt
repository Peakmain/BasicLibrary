package com.peakmain.basiclibrary.utils

import android.content.Context

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：
 */

class PreferencesUtils private constructor(val context: Context) {
    companion object {
        private var instance: PreferencesUtils? = null
        fun getInstance(context: Context): PreferencesUtils {
            if (instance == null) {
                synchronized(context) {
                    if (instance == null) {
                        instance = PreferencesUtils(context)
                    }
                }
            }
            return instance!!
        }

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
        mSharedPreferences = DefaultSharedPreferencesFactory(context)
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

