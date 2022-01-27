package com.peakmain.basiclibary

import android.app.ProgressDialog
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.peakmain.basiclibary.databinding.ActivityMainBinding
import com.peakmain.basiclibary.network.DataResponse
import com.peakmain.basiclibary.network.ProjectTree
import com.peakmain.basiclibary.network.WanAndroidApi
import com.peakmain.basiclibary.viewModel.MainViewModel
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.extend.*
import com.peakmain.basiclibrary.manager.AnimationManager
import com.peakmain.basiclibrary.network.RetrofitManager
import com.peakmain.basiclibrary.network.status.ApiStatus
import com.peakmain.basiclibrary.utils.bus.RxBus
import java.util.concurrent.TimeUnit

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var progressDialog: ProgressBar
    override fun initView() {


        mViewModel.test()
        mViewModel.getProjectTree()
        val rxBus = RxBus.instance.register<Int>("test")
        rxBus.setData(100)
        val value = rxBus.value
        progressDialog=findViewById(R.id.progressBar)
        findViewById<View>(R.id.tv_title).visibility=View.GONE
        ktxRunOnUiThreadDelay(3000){
            AnimationManager.crossfade(findViewById<View>(R.id.tv_title),progressDialog)
        }
    }

}