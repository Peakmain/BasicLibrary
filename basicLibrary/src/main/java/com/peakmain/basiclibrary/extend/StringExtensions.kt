package com.peakmain.basiclibrary.extend

import java.text.DecimalFormat

/**
 * author ：Peakmain
 * createTime：1/22/22
 * mail:2726449200@qq.com
 * describe：
 */
fun <T : String?> T.isSpace(): Boolean {
    if (this == null) return true
    var i = 0
    val len = length
    while (i < len) {
        if (!Character.isWhitespace(get(i))) {
            return false
        }
        i++
    }
    return true
}

fun Number.formatToMoney(): String? {
    return DecimalFormat("#,###.00").format(this)
}

fun String?.replaceZero(): String? {
    if (this == null) return this
    var s = this
    s.replace("0+?$".toRegex(), "").also { s = it }
    s?.replace("[.]$".toRegex(), "").also { s = it }
    return s
}
/**
 * 判断两个字符是否改变
 */
fun CharSequence?.haveContentsChanged(str:CharSequence?):Boolean{
    if (this == null != (str == null)) {
        return true
    } else if (this == null) {
        return false
    }
    val length: Int = this.length
    if (length != str?.length) {
        return true
    }
    for (i in 0 until length) {
        if (this[i] != str[i]) {
            return true
        }
    }
    return false
}