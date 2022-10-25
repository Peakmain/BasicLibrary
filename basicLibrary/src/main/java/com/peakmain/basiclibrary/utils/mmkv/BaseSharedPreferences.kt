package com.peakmain.basiclibrary.utils.mmkv

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.tencent.mmkv.MMKV
import java.io.*

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：MMKV的工具类
 */
abstract class BaseSharedPreferencesFactory @JvmOverloads constructor(
    context: Context,
    private val mMode: Int = Context.MODE_PRIVATE
) {
    companion object {
        private const val OLD_DATA_KEY = "old_data_key"
    }
    private val mContext: Context = context.applicationContext
    protected abstract val key: String
    private var mSharedPreferences: SharedPreferences? = null
    private val sharedPreferences: SharedPreferences
        get() {
            if (mSharedPreferences == null) {
                val mmkv = MMKV.mmkvWithID(key, MMKV.MULTI_PROCESS_MODE)
                 if(!mmkv.decodeBool(OLD_DATA_KEY)){
                     //迁移旧的数据
                     val oldSharedPreferences = mContext.getSharedPreferences(key, mMode)
                     mmkv.importFromSharedPreferences(oldSharedPreferences)
                     oldSharedPreferences.edit().clear().apply()
                     mmkv.encode(OLD_DATA_KEY, true)
                 }
                mSharedPreferences=mmkv
            }
            return mSharedPreferences!!
        }


    fun remove(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun remove(vararg key: String) {
        for (s in key) {
            remove(s)
        }
    }

    fun saveParams(key: String, objects: Any) {
        val editor = sharedPreferences.edit()
        when (objects::class.java.simpleName) {
            "String" -> {
                editor.putString(key, objects as String)
            }
            "Integer" -> {
                editor.putInt(key, objects as Int)
            }
            "Boolean" -> {
                editor.putBoolean(key, objects as Boolean)
            }
            "Float" -> {
                editor.putFloat(key, objects as Float)
            }
            "Long" -> {
                editor.putLong(key, objects as Long)
            }
            else -> {
                if (objects !is Serializable) {
                    throw IllegalArgumentException(
                        objects::class.java.name.toString() + " 必须实现Serializable接口!"
                    )
                }
                val baos = ByteArrayOutputStream()
                val oos = ObjectOutputStream(baos)
                try {
                    oos.writeObject(objects)
                    val productBase64: String = Base64.encodeToString(
                        baos.toByteArray(), Base64.DEFAULT
                    )
                    editor.putString(key, productBase64)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    baos.close()
                    oos.close()
                }
                editor.apply()
            }
        }
    }

    fun getParam(key: String, defaultObject: Any?): Any? {
        if (defaultObject == null) {
            return getObject(key)
        }
        val preferences = sharedPreferences
        when (defaultObject.javaClass.simpleName) {
            "String" -> {
                return preferences.getString(key, defaultObject as String?)
            }
            "Integer" -> {
                return preferences.getInt(key, defaultObject as Int)
            }
            "Boolean" -> {
                return preferences.getBoolean(key, defaultObject as Boolean)
            }
            "Float" -> {
                return preferences.getFloat(key, defaultObject as Float)
            }
            "Long" -> {
                return preferences.getLong(key, defaultObject as Long)
            }
            else -> return getObject(key)
        }
    }

    fun getObject(key: String?): Any? {
        val wordBase64 = sharedPreferences.getString(key, "")
        val base64 =
            Base64.decode(wordBase64?.toByteArray(), Base64.DEFAULT)
        val bais = ByteArrayInputStream(base64)
        try {
            val bis = ObjectInputStream(bais)
            return bis.readObject()
        } catch (e: java.lang.Exception) {
        }
        return null
    }

    fun clearData() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}