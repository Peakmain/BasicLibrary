package com.peakmain.basiclibary

import android.util.Log
import com.peakmain.basiclibary.databinding.ActivityMainBinding
import com.peakmain.basiclibary.network.DataResponse
import com.peakmain.basiclibary.network.ProjectTree
import com.peakmain.basiclibary.network.WanAndroidApi
import com.peakmain.basiclibary.viewModel.MainViewModel
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.extend.*
import com.peakmain.basiclibrary.network.RetrofitManager
import com.peakmain.basiclibrary.network.status.ApiStatus
import com.peakmain.basiclibrary.utils.bus.RxBus
import java.util.concurrent.TimeUnit

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainViewModel>() {


    override fun initView() {


        Log.e("TAG", "测试${getViewModel(MainViewModel::class.java)}")
        Log.e("TAG", "测试${getViewModel(MainViewModel::class.java)}")
        mViewModel.test()
        mViewModel.getProjectTree()
        val rxBus = RxBus.instance.register<Int>("test")
        rxBus.setData(100)
        val value=rxBus.value
    }

}