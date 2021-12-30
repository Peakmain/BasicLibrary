package com.peakmain.basiclibary

import android.util.Log
import com.peakmain.basiclibary.databinding.ActivityMainBinding
import com.peakmain.basiclibary.network.DataResponse
import com.peakmain.basiclibary.network.ProjectTree
import com.peakmain.basiclibary.network.WanAndroidApi
import com.peakmain.basiclibary.viewModel.MainViewModel
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.network.RetrofitManager
import com.peakmain.basiclibrary.network.status.ApiStatus

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainViewModel>() {
    private lateinit var api: WanAndroidApi
    val REQUEST_BASE_URL = "https://wanandroid.com/"
    override fun initView() {
        api = RetrofitManager.createService(WanAndroidApi::class.java, REQUEST_BASE_URL)
        RetrofitManager.createData(api.projectTree,
            object : ApiStatus<DataResponse<ProjectTree>>() {

                override fun success(t: DataResponse<ProjectTree>) {
                    Log.e("TAG", t.toString())
                }

                override fun error(exception: Exception) {
                }


            })
        Log.e("TAG","测试${getViewModel(MainViewModel::class.java)}")
        Log.e("TAG","测试${getViewModel(MainViewModel::class.java)}")
        Log.e("TAG","测试${getViewModel(MainViewModel::class.java)}")
    }

}