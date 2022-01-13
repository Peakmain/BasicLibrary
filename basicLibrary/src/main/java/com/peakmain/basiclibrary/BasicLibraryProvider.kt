package com.peakmain.basiclibrary

import android.app.Application
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelStore
import com.peakmain.basiclibrary.base.IApp
import com.peakmain.basiclibrary.config.BasicLibraryConfig
import com.peakmain.basiclibrary.task.MMKVTask
import com.peakmain.ui.utils.launcher.dispatcher.TaskDispatcher


/**
 * author ：Peakmain
 * createTime：2021/12/27
 * mail:2726449200@qq.com
 * describe：
 */
class BasicLibraryProvider : FileProvider(), IApp {
    private lateinit var viewModelStore: ViewModelStore
    protected lateinit var mDispatcher: TaskDispatcher
    override fun onCreate(): Boolean {
        BasicLibraryConfig.getInstance().setApp(this)
        viewModelStore = ViewModelStore()
        TaskDispatcher.init(getApplication())
        mDispatcher = TaskDispatcher.createInstance()
        mDispatcher.addTask(MMKVTask())
        return true
    }

    override fun getApplication(): Application {
        return context!!.applicationContext as Application
    }

    override fun getViewModelStore(): ViewModelStore {
        return viewModelStore
    }

    override fun getDispatcher(): TaskDispatcher {
        return mDispatcher
    }


}