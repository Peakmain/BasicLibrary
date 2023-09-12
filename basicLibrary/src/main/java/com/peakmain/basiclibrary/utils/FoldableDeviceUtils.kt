package com.peakmain.basiclibrary.utils

/**
 * author ：Peakmain
 * createTime：2023/9/12
 * mail:2726449200@qq.com
 * describe：
 */
object FoldableDeviceUtils {
    /**
     * true表示是折叠状态
     * false表示展开状态
     */
    fun isFold(): Boolean {
        val screenWidth: Int? =
            BasicLibraryUtils.application?.resources?.displayMetrics?.widthPixels

        if (SystemUtils.isSamsungFold()) {
            return screenWidth != 1768
        }
        if (SystemUtils.isHuaWeiFoldDevice()) {
            return screenWidth != 2200
        }
        if (SystemUtils.isGoogleFoldDevice()) {
            return screenWidth != 2200
        }
        if (SystemUtils.isVivoFoldDevice()) {
            return screenWidth != 1916
        }
        return true
    }


}