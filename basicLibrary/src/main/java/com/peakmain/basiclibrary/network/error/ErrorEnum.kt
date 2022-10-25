package com.peakmain.basiclibrary.network.error

import androidx.annotation.IntDef

/**
 * author ：Peakmain
 * createTime：2021/12/23
 * mail:2726449200@qq.com
 * describe：
 */
class ErrorEnum{
    companion object{
         const val SUCCESS=0
         const val TOKEN_ERROR=401
    }
    @IntDef(value=[SUCCESS,TOKEN_ERROR])
    @Retention(AnnotationRetention.SOURCE)
    annotation class Error

}