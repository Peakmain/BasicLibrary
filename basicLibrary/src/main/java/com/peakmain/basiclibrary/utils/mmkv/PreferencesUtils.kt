package com.peakmain.basiclibrary.utils.mmkv

import android.content.Context
import java.lang.ref.WeakReference

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：
 */

class PreferencesUtils private constructor(private val contextRef: WeakReference<Context>) {
    companion object {
        @Volatile
        private var instance: PreferencesUtils? = null

        @JvmStatic
        fun getInstance(context: Context): PreferencesUtils? {
            instance ?: synchronized(this) {
                instance ?: PreferencesUtils(WeakReference(context)).also {
                    instance = it
                }
            }
            return instance
        }
    }

    private lateinit var mSharedPreferences: DefaultSharedPreferencesFactory
    fun getSharedPreferences(): DefaultSharedPreferencesFactory? {
         if (this::mSharedPreferences.isInitialized) {
             return mSharedPreferences
        } else {
            val context = contextRef.get()
            if (context != null) {
                return init(context)
            }
        }
        return null
    }

    private fun init(context: Context): DefaultSharedPreferencesFactory {
        mSharedPreferences =
            DefaultSharedPreferencesFactory(
                context
            )
        return mSharedPreferences
    }

    fun saveParams(key: String, objects: Any) {
        getSharedPreferences()?.saveParams(key, objects)
    }

    fun getParam(key: String, defaultObject: Any?): Any? {
        return getSharedPreferences()?.getParam(key, defaultObject)
    }

    fun clearData() {
        getSharedPreferences()?.clearData()
    }
}

