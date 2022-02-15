package com.peakmain.basiclibary

import android.view.View
import android.widget.ProgressBar
import com.peakmain.basiclibary.databinding.ActivityMainBinding
import com.peakmain.basiclibary.viewModel.MainViewModel
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.extend.ktxRunOnUiThreadDelay
import com.peakmain.basiclibrary.manager.AnimationManager
import com.peakmain.basiclibrary.utils.GlobalCoroutineExceptionHandler
import com.peakmain.basiclibrary.utils.bus.RxBus
import com.peakmain.ui.utils.LogUtils

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var progressDialog: ProgressBar
    override fun initView() {


        mViewModel.test()
        mViewModel.getProjectTree()
        val rxBus = RxBus.instance.register<Int>("test")
        rxBus.setData(100)
        val value = rxBus.value
        progressDialog = findViewById(R.id.progressBar)
        findViewById<View>(R.id.tv_title).visibility = View.GONE
        ktxRunOnUiThreadDelay(3000) {
            AnimationManager.hideCrossFadeLoadingView(
                findViewById<View>(R.id.tv_title),
                progressDialog
            )
        }
        GlobalCoroutineExceptionHandler().coroutineExceptionCallback = { context, exception ->
            LogUtils.e("$context,异常是:$exception")
        }
    }

}