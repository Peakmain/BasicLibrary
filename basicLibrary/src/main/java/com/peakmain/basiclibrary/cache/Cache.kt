package com.peakmain.basiclibrary.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * author ：Peakmain
 * createTime：2022/2/19
 * mail:2726449200@qq.com
 * describe：
 */
@Entity(tableName = "cache")
class Cache {
    @PrimaryKey(autoGenerate = false)
    @NotNull
    var key: String = ""
    var data: ByteArray? = null
}