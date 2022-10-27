package com.peakmain.basiclibrary.utils

import com.peakmain.basiclibrary.constants.AndroidVersion
import java.text.SimpleDateFormat
import java.util.*

/**
 * author ：Peakmain
 * createTime：2021/12/24
 * mail:2726449200@qq.com
 * describe：
 */
object TimeUtils {
    const val yyyy_MM_dd = "yyyy-MM-dd"
    private const val yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss"

    fun getCurrentTime(pattern: String=yyyy_MM_dd_HH_mm_ss): String {
        val currentTime = Date()
        val formatter = if (AndroidVersion.isAndroid7()) {
            SimpleDateFormat(pattern,Locale.getDefault(Locale.Category.FORMAT))
        } else {
            SimpleDateFormat(pattern,Locale.CHINA)
        }
        return formatter.format(currentTime)
    }
    /**
     * 返回几就是周几
     */
    fun getDayOfWeek(date: Date): Int {
        val c = Calendar.getInstance().also {
            it.time = date
        }
        val weekDay = c.get(Calendar.DAY_OF_WEEK)
        val day = (weekDay - 1)
        return if (day == 0) 7 else day
    }
    /**
     * 将指定的毫秒数转换为 以pattern参数自定义的格式返回
     */
    fun ms2Date(_ms: Long, pattern: String): String {
        val date = Date(_ms)
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        return format.format(date)
    }
    /**
     * 将指定以pattern参数自定义的格式的时间转换为毫秒值
     */
    fun date2Ms(_data: String, pattern: String): Long {
        val format = if(AndroidVersion.isAndroid7()){
            SimpleDateFormat(pattern,Locale.getDefault(Locale.Category.FORMAT))
        }else{
            SimpleDateFormat(pattern,Locale.CHINA)
        }
        return try {
            val date = format.parse(_data)
            date?.time ?: 0
        } catch (e: Exception) {
            0
        }
    }
    /**
     * 获取当前时间段
     * [h] 小时数，24小时制
     */
    fun getCurrentTimeRange(h: String): String {
        var hour: Int
        try {
            hour = h.toInt()
        } catch (e: Exception) {
            hour = 0
            e.printStackTrace()
        }
        return when (hour) {
            23, 0, 1 -> "半夜"
            in 2..5 -> "凌晨"
            in 6..9 -> "早上"
            in 9 until 12 -> "上午"
            in 12..13 -> "中午"
            in 14..18 -> "下午"
            in 19..22 -> "晚上"
            else -> ""
        }
    }

    /**
     * 获取[date2]比[date1]多的天数
     */
    fun dateDifference(date1: Date, date2: Date): Int {
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2
        val day1 = cal1[Calendar.DAY_OF_YEAR]
        val day2 = cal2[Calendar.DAY_OF_YEAR]
        val year1 = cal1[Calendar.YEAR]
        val year2 = cal2[Calendar.YEAR]
        return if (year1 != year2) {
            var timeDistance = 0
            for (i in year1 until year2) {
                timeDistance += if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    //闰年
                    366
                } else {//不是闰年
                    365
                }
            }
            timeDistance + (day2 - day1)
        } else {
            day2 - day1
        }
    }

}