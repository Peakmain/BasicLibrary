package com.peakmain.basiclibrary.utils

import android.text.TextUtils
import java.math.BigDecimal

/**
 * author ：Peakmain
 * createTime：2022/3/1
 * mail:2726449200@qq.com
 * describe：算术工具类
 */
object ArithmeticUtils {
    fun add(d1: Double, d2: Double): Double {
        val str1 = d1.toString()
        val str2 = d2.toString()
        return add(str1, str2)
    }

    /**
     * 相加
     */
    fun add(str1: String?, str2: String?): Double {
        val b1 = BigDecimal(str1)
        val b2 = BigDecimal(str2)
        return b1.add(b2).toDouble()
    }

    /**
     * 相减
     */
    fun sub(d1: Double, d2: Double): Double {
        val str1 = java.lang.Double.toString(d1)
        val str2 = java.lang.Double.toString(d2)
        return sub(str1, str2)
    }

    /**
     * 相减
     */
    fun sub(str1: String?, str2: String?): Double {
        val b1 = BigDecimal(str1)
        val b2 = BigDecimal(str2)
        return b1.subtract(b2).toDouble()
    }

    /**
     * 相乘
     */
    fun mul(d1: Double, d2: Double): Double {
        val str1 = java.lang.Double.toString(d1)
        val str2 = java.lang.Double.toString(d2)
        return mul(str1, str2)
    }

    /**
     * 相乘
     */
    fun mul(str1: String?, str2: String?): Double {
        val b1 = BigDecimal(str1)
        val b2 = BigDecimal(str2)
        return b1.multiply(b2).toDouble()
    }

    /**
     * 相除
     */
    fun div(d1: Double, d2: Double): Double {
        return div(d1, d2, 10)
    }

    fun div(d1: Double, d2: Double, scale: Int): Double {
        require(scale >= 0) { "The scale must be a positive integer or zero" }
        val b1 = BigDecimal(java.lang.Double.toString(d1))
        val b2 = BigDecimal(java.lang.Double.toString(d2))
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * num.compareTo(BigDecimal.ZERO)结果：
     * num小于0  例如：num=-10.00
     * num等于0，例如num=0.00
     * num大于0  例如：num=10.00
     *
     * @param strValue
     * @return 浮点型是否大于0
     */
    fun valueGreaterThanZero(strValue: String?): Boolean {
        // 为空时，认为大于0
        if (TextUtils.isEmpty(strValue)) {
            return false
        }
        val num = BigDecimal(strValue)
        val result = num.compareTo(BigDecimal.ZERO)
        return result == 1
    }

    /**
     * num.compareTo(BigDecimal.ZERO)结果：
     * num小于0  例如：num=-10.00
     * num等于0，例如num=0.00或 0
     * num大于0  例如：num=10.00
     *
     * @param strValue
     * @return 浮点型是否等于0
     */
    fun valueEqualsZero(strValue: String?): Boolean {
        // 为空认为是0
        if (TextUtils.isEmpty(strValue)) {
            return true
        }
        val num = BigDecimal(strValue)
        val result = num.compareTo(BigDecimal.ZERO)
        return result == 0
    }
}