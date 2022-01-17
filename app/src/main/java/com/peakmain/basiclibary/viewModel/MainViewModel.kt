package com.peakmain.basiclibary.viewModel

import android.util.Log
import com.peakmain.basiclibary.network.DataResponse
import com.peakmain.basiclibary.network.ProjectTree
import com.peakmain.basiclibary.network.WanAndroidApi
import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel
import com.peakmain.basiclibrary.network.RetrofitManager
import com.peakmain.basiclibrary.network.status.ApiStatus
import com.peakmain.ui.utils.LogUtils

/**
 * author ：Peakmain
 * createTime：2021/12/27
 * mail:2726449200@qq.com
 * describe：
 */
class MainViewModel : BaseViewModel() {
    private lateinit var api: WanAndroidApi
    val REQUEST_BASE_URL = "https://wanandroid.com/"
    override fun initModel() {
        api = RetrofitManager.createService(WanAndroidApi::class.java, REQUEST_BASE_URL)
    }

    fun getProjectTree() {
        RetrofitManager.createData(api.projectTree,
            object : ApiStatus<DataResponse<ProjectTree>>() {

                override fun success(t: DataResponse<ProjectTree>) {
                    Log.e("TAG", t.toString())
                }

                override fun error(exception: Exception) {
                }


            })
        RetrofitManager.createData(api.projectTree, {
            //todo before()
        }, {
            //todo success(
        }, {
            //todo error()
        })
    }

    fun test() {
        LogUtils.e("测试")
    }
}