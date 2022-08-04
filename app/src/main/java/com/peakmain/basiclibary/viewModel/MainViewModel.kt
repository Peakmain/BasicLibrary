package com.peakmain.basiclibary.viewModel

import android.util.Log
import android.view.View
import com.peakmain.basiclibary.network.DataResponse
import com.peakmain.basiclibary.network.ProjectTree
import com.peakmain.basiclibary.network.WanAndroidApi
import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel
import com.peakmain.basiclibrary.network.RetrofitManager
import com.peakmain.basiclibrary.network.entity.BaseEntity
import com.peakmain.basiclibary.network.status.ApiBaseStatus
import com.peakmain.basiclibrary.network.status.ApiStatus
import com.peakmain.basiclibary.network.status.BaseEntityDataRetrofitData
import com.peakmain.ui.utils.ToastUtils

/**
 * author ：Peakmain
 * createTime：2021/12/27
 * mail:2726449200@qq.com
 * describe：
 */
class MainViewModel : BaseViewModel() {
    private lateinit var api: WanAndroidApi
    val REQUEST_BASE_URL = "https://wanandroid.com/"
    val delayTime = 1000L
    override fun initModel() {
        api = RetrofitManager.createService(WanAndroidApi::class.java, REQUEST_BASE_URL)
    }

     val isShow = false
    fun getProjectTree() {

        RetrofitManager.createData(api.projectTree,
            object : ApiStatus<DataResponse<ProjectTree>>() {

                override fun success(t: DataResponse<ProjectTree>) {
                    Log.e("TAG", t.toString())
                }

                override fun error(exception: Exception) {
                }


            })
        RetrofitManager.createData(api.projectTree1, BaseEntityDataRetrofitData(object :
            ApiBaseStatus<BaseEntity<ProjectTree>>(){
            override fun baseData(entity: BaseEntity<ProjectTree>) {
                TODO("Not yet implemented")
            }

            override fun before() {
                TODO("Not yet implemented")
            }

            override fun success(t: BaseEntity<ProjectTree>) {
                TODO("Not yet implemented")
            }

            override fun isEmpty() {
                TODO("Not yet implemented")
            }

            override fun loadMore(t: BaseEntity<ProjectTree>, isRefresh: Boolean) {
                TODO("Not yet implemented")
            }

            override fun error(exception: java.lang.Exception) {
                TODO("Not yet implemented")
            }

        }) )
        RetrofitManager.createData(api.projectTree,null, {
            //todo before()
        }, {
            //todo success(
        }, {
            //todo error()
        })
    }


    fun clickListener(view: View) {
        ToastUtils.showLong("测试")
    }

}