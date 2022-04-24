package com.peakmain.basiclibrary.config

import com.peakmain.basiclibrary.base.BaseEmptySingleton
import com.peakmain.basiclibrary.base.BaseOneSingleton
import com.peakmain.basiclibrary.base.IApp
import java.lang.NullPointerException

/**
 * author ：Peakmain
 * createTime：2021/12/27
 * mail:2726449200@qq.com
 * describe：全局的配置
 */
class BasicLibraryConfig private constructor() {
    private var mApp: IApp? = null

    companion object:BaseEmptySingleton<BasicLibraryConfig>(){

        override fun createSingleton(): BasicLibraryConfig = BasicLibraryConfig()
    }

    fun setApp(app: IApp) {
        this.mApp = app
    }

    fun getApp(): IApp {
        if (mApp == null) {
            throw NullPointerException("IApp must not be empty!")
        }
        return mApp!!
    }
}