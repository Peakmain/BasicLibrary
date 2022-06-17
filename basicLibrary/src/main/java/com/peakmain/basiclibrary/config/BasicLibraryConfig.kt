package com.peakmain.basiclibrary.config

import com.peakmain.basiclibrary.base.BaseEmptySingleton
import com.peakmain.basiclibrary.base.IApp

/**
 * author ：Peakmain
 * createTime：2021/12/27
 * mail:2726449200@qq.com
 * describe：全局的配置
 */
class BasicLibraryConfig private constructor() {
    private var mApp: IApp? = null

    companion object:BaseEmptySingleton<BasicLibraryConfig>(){
        override val createSingleton=::BasicLibraryConfig
    }

    fun setApp(app: IApp) {
        this.mApp = app
    }

    fun getApp(): IApp? {
        return mApp
    }
}