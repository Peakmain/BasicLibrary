package com.peakmain.basiclibrary.task

import com.peakmain.basiclibrary.config.BasicLibraryConfig
import com.peakmain.ui.utils.launcher.task.MainTask
import com.peakmain.ui.utils.launcher.task.Task
import com.tencent.mmkv.MMKV

/**
 * author ：Peakmain
 * createTime：2021/12/30
 * mail:2726449200@qq.com
 * describe：
 */
class MMKVTask : MainTask() {
    override fun run() {
        BasicLibraryConfig.getInstance()?.getApp()?.getApplication()?.apply {
            MMKV.initialize(this)
        }
       
    }
}