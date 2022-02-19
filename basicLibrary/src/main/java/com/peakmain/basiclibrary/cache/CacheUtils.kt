package com.peakmain.basiclibrary.cache

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * author ：Peakmain
 * createTime：2022/2/19
 * mail:2726449200@qq.com
 * describe：Room实现key,value缓存
 */
object CacheUtils {
    fun <T> saveCache(key: String, content: T) {
        val cache = Cache()
        cache.key = key
        cache.data = toByteArray(content)
        CacheDatabase.getInstance().cacheDao.saveCache(cache)
    }
    fun <T> getCache(key: String): T? {
        val cache = CacheDatabase.getInstance().cacheDao.getCache(key)
        return (if (cache?.data != null) {
            toObject(cache.data)
        } else null) as? T
    }

    fun deleteCache(key: String) {
        val cache = Cache()
        cache.key = key
        CacheDatabase.getInstance().cacheDao.deleteCache(cache)
    }
    private fun <T> toByteArray(content: T): ByteArray? {
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(content)
            oos.flush()
            return baos.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            baos?.close()
            oos?.close()
        }

        return ByteArray(0)
    }

    private fun toObject(data: ByteArray?): Any? {
        var bais: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null
        try {
            bais = ByteArrayInputStream(data)
            ois = ObjectInputStream(bais)
            return ois.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {

            bais?.close()
            ois?.close()
        }

        return null
    }
}