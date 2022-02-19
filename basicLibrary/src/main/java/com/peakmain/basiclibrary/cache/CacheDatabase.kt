package com.peakmain.basiclibrary.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.peakmain.basiclibrary.config.BasicLibraryConfig

/**
 * author ：Peakmain
 * createTime：2022/2/19
 * mail:2726449200@qq.com
 * describe：
 */
@Database(entities = [Cache::class], version = 1)
abstract class CacheDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: CacheDatabase? = null
        fun getInstance(): CacheDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            BasicLibraryConfig.getInstance().getApp()
                                .getApplication().applicationContext,
                            CacheDatabase::class.java, "bd_cache"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract val cacheDao: CacheDao
}