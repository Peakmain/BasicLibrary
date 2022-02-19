package com.peakmain.basiclibrary.cache

import androidx.room.*

/**
 * author ：Peakmain
 * createTime：2022/2/19
 * mail:2726449200@qq.com
 * describe：
 */
@Dao
interface CacheDao {
    @Insert(entity = Cache::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCache(cache: Cache): Long

    @Query("select * from cache where `key`=:key")
    fun getCache(key: String): Cache?

    @Delete(entity = Cache::class)
    fun deleteCache(cache: Cache)
}