package com.peakmain.basiclibrary.utils.log

import android.util.Log
import com.peakmain.basiclibrary.BuildConfig
import com.peakmain.basiclibrary.config.BasicLibraryConfig
import com.peakmain.basiclibrary.constants.PermissionConstants
import com.peakmain.basiclibrary.extend.isSpace
import com.peakmain.basiclibrary.permission.PkPermission
import java.io.File
import java.io.RandomAccessFile
import java.text.SimpleDateFormat
import java.util.*

/**
 * author ：Peakmain
 * createTime：1/22/22
 * mail:2726449200@qq.com
 * describe:写日志到sd卡
 */
object LogFileUtils {

    /**
     * 秒
     */
    private val SECOND: Long = 1000

    /**
     * 分
     */
    private val MINUTE = SECOND * 60

    /**
     * 小时
     */
    private val HOUR = MINUTE * 60

    /**
     * 天
     */
    private val DAY = HOUR * 24

    /**
     * 周
     */
    private val WEEK = DAY * 7
    private const val YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
    private const val YYYY_MM_DD = "yyyy-MM-dd"
    private const val LOGCAT_BASE_PATH = "Peakmain/Log"
    val application = BasicLibraryConfig.getInstance()?.getApp()?.getApplication()
    private fun getBasePath(children: String) =
       application?.getExternalFilesDir(null)?.absolutePath + "/$children/"

    var basePath: String = getBasePath(LOGCAT_BASE_PATH)
    @JvmStatic
    fun setLogPath(path: String) {
        basePath = path
    }

    @JvmStatic
    fun write(str: String) {
        try {
            write(basePath, str)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun write(filePath: String, str: String) {
        if (PkPermission.isGranted(PermissionConstants.getPermissions(PermissionConstants.STORAGE))) {
            val file = (if (filePath.isSpace()) null else File(filePath)) ?: return
            file.also {
                if (!it.exists()) {
                    it.mkdirs()
                }
                val logFileName =
                    formatTimeByLong(System.currentTimeMillis(), YYYY_MM_DD)
                val logPath = "$filePath$logFileName.log"
                File(logPath).also { log ->
                    if (!log.exists()) {
                        log.createNewFile()
                    }
                    writeLogToFile(
                        log,
                        "${
                            formatTimeByLong(
                                System.currentTimeMillis(),
                                YYYY_MM_DD_HH_MM_SS
                            )
                        }   $str"
                    )
                }
            }
        } else {
            Log.e("TAG","请开启读写权限")
        }
    }

    private fun writeLogToFile(file: File, str: String) {
        val strContent = str + "\r\n"
        try {
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
            }
            val raf = RandomAccessFile(file, "rwd")
            raf.seek(file.length())
            raf.write(strContent.toByteArray())
            raf.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 删除文件
     *
     * @param logPath 路径
     */
    private fun deleteFile(logPath: String): Boolean {
        var flag = false
        val file = File(logPath)
        // 路径为文件且不为空则进行删除
        if (file.isFile && file.exists()) {
            file.delete()
            flag = true
        }
        return flag
    }

    private fun formatTimeByLong(time: Long, pattern: String): String {
        if (time == -1L) {
            return ""
        }
        return try {
            val d = Date(time)
            val sf = SimpleDateFormat(pattern)
            sf.format(d)
        } catch (e: Exception) {
            ""
        }
    }

}